package relationExtracion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class attriExtration {
	List<String> attrlist=new ArrayList<String>();
	StringBuffer entity=new StringBuffer();
	
	public void Process(){	
		try {
			Class.forName("com.mysql.jdbc.Driver");
	        Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/freelancerresult","root","root");
	        Statement stmt=conn.createStatement();
	        String sql="select id,projectId,head,tail from relation_2 ";
	        ResultSet rs=stmt.executeQuery(sql);		       
	        int id;
	        int projectId;
	        String head;	
	        String tail;
	        while(rs.next()){
	        	id=rs.getInt(1);
	        	projectId=rs.getInt(2);
	        	head=rs.getString(3);
	        	tail=rs.getString(4);
	        	System.out.println(id+" "+head);
	        	if(getAttr(head)){
	        		for(String attr:attrlist){
	        			insertAttr(projectId, entity.toString(), attr);
	        		}
	        		updateEntity(id, "head");
	        		entity=new StringBuffer();
	        		attrlist=new ArrayList<String>();
	        	}
	        	
	        	if(getAttr(tail)){
	        		for(String attr:attrlist){
	        			insertAttr(projectId, entity.toString(), attr);
	        		}
	        		updateEntity(id, "tail");
	        		entity=new StringBuffer();
	        		attrlist=new ArrayList<String>();
	        	}
	        }
	        conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}					
	}
	
	public Boolean getAttr(String str){
		System.out.println(str);
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		Annotation document = new Annotation(str);
		pipeline.annotate(document);
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		List<String> wordList = new ArrayList<String>();
		List<String> posList = new ArrayList<String>();
		
		for (CoreMap sentence : sentences) {
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				String word = token.get(TextAnnotation.class);
				wordList.add(word);
				String pos = token.get(PartOfSpeechAnnotation.class);
				posList.add(pos);
				
			}
		}
		boolean flag=false;
		
		int i=0;
		while(i<posList.size()-1&&posList.get(i).startsWith("J")) {
			attrlist.add(wordList.get(i));
			i++;
			flag=true;
		}
		if(flag){
			for(;i<posList.size();i++){
				entity.append(wordList.get(i));
				if(i!=posList.size()-1)
					entity.append(" ");
			}
		}
		
		return flag;
		
	}
	
	public void insertAttr(int projectId,String entity,String attr){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/freelancerresult","root","root");	
			PreparedStatement ps=null; 				
			String sql="insert into attribute (projectId,entity,attr) values(?,?,?) ";
			ps =conn.prepareStatement(sql);
			ps.setInt(1,projectId);
			ps.setString(2,entity);
			ps.setString(3,attr);
		    ps.executeUpdate(); 		    
		    ps.close();  		
			conn.close();			
		}catch(Exception e){
			e.printStackTrace();			
		}	
	}
	
	public void updateEntity(int id,String headortail){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/freelancerresult","root","root");	
			Statement stmt=conn.createStatement(); 
			String sql="update relation_2 set "+headortail+" ='"+entity+"' where id ="+id;
			System.out.println(sql);
			stmt.executeUpdate(sql); 
			stmt.close();
            conn.close();		
		}catch(Exception e){
			e.printStackTrace();			
		}		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		attriExtration att=new attriExtration();
		att.Process();

	}

}
