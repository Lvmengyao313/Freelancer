package similarity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import edu.stanford.nlp.util.StringUtils;
import edu.sussex.nlp.jws.JWS;
import edu.sussex.nlp.jws.Lin;
import similarity.DisSimilarity;

public class wordSimilarity {
	
//	static String dir = "C:/soft/WordNet";
//	static JWS	ws = new JWS(dir, "2.1");
//	static Lin lin = ws.getLin();
		
	static Map<String, HashMap<String, Double>> simMap =new HashMap<String,HashMap<String,Double>>();

	static{
		List<String> wordlist=new ArrayList<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
	        Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/freelancerresult","root","root");
	        Statement stmt=conn.createStatement();
	        String sql="select word1, word2 from sim_words ";
	        ResultSet rs=stmt.executeQuery(sql);		
			while (rs.next()){
				if(!wordlist.contains(rs.getString(1)))
					wordlist.add(rs.getString(1));
				if(!wordlist.contains(rs.getString(2)))
					wordlist.add(rs.getString(2));
			 }  
	        conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		for(String w:wordlist){
			simMap.put(w,getSimlist(w));
		}				
	}
	
	public static HashMap<String, Double> getSimlist(String w){
		HashMap<String, Double> simlist=new HashMap<String,Double>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
	        Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/freelancerresult","root","root");
	        Statement stmt=conn.createStatement();
	        String sql="select word1,word2, sim from sim_words where word1='"+w+"' or word2='"+w+"'";
	        ResultSet rs=stmt.executeQuery(sql);	      
			while (rs.next()){
				if(rs.getString(1).equals(w))
					simlist.put(rs.getString(2), rs.getDouble(3));
				else
					simlist.put(rs.getString(1), rs.getDouble(3));
			 }  
	        conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}		
		return simlist;
	}
	
		
	public static double wordsim(String w1,String w2){	
		if(w1.equals(w2))
			return 1.0;
		if(StringUtils.isNumeric(w1)&&StringUtils.isNumeric(w2))
			return 0.9;
		if(simMap.get(w1)==null)
			return 0;
		else
			if(simMap.get(w1).get(w2)==null)
				return 0;
			else
				return simMap.get(w1).get(w2);
	}
	
/*		
	public static double wordsim(String w1,String w2){
		if(w1.equals(w2))
			return 1.0;
		double i=0;
		double max=0;
		TreeMap<String,Double> score=lin.lin(w1,w2,"n");		
		for(String s : score.keySet()){	
			if(score.get(s)>max)
				max=score.get(s);
			if(score.get(s)>0.5)
				i++;
		}	
		if(score.size()==0||i==0)
			return 0;
		else {
			double sim=Math.pow(max,5.0)*(Math.pow(i/score.size(), 1.0/10));
			return sim;				
		}			
			
	}
*/	
	public static double phasesim(String p1,String p2){	
		double sim=0;
		p1=p1.replace("-"," ");
		p1=p1.replace("-"," ");
		if(p1.equals(p2))
			return 1.0;
		String s1[]=p1.split(" ");
		String s2[]=p2.split(" ");
		if(s1.length==1&&s2.length==1){
			sim=wordsim(s1[0],s2[0]);
			return sim;
		}
							
		if(s1.length-s2.length>=3||s1.length-s2.length<=-3)
			return 0;
		sim=DisSimilarity.similarity(p1, p2);
		if(sim>0.88)
			return sim;
		if(sim>0.85&&(s1.length>=3||s2.length>=3))//0.85
			return sim;
		
		double s[][]=new double[s1.length][s2.length];
		double sim1=0,sim2=0;
		double max=0,k=0;
		for(int i=0;i<s1.length;i++){
			for(int j=0;j<s2.length;j++){
				s[i][j]=wordsim(s1[i],s2[j]);
			}
		}
					
		for(int i=0;i<s1.length;i++){
			max=0;
			for(int j=0;j<s2.length;j++){
				k=s[i][j];
				if(k>max)
					max=k;		
			}
			sim1+=max;
		}		
		for(int i=0;i<s2.length;i++){
			max=0;
			for(int j=0;j<s1.length;j++){
				k=s[j][i];
				if(k>max)
					max=k;		
			}
			sim2+=max;
		}		
		sim=(sim1/s1.length+sim2/s2.length)/2;
		return sim;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub  my university project
//		System.out.println("sim="+phasesim("person someone","someone"));
		System.out.println(wordSimilarity.wordsim("pic","picture"));

	}

}
