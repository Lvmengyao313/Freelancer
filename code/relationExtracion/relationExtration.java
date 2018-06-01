package relationExtracion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;


public class relationExtration {
	List<String> headList;
	List<String> relationList;
	List<String> tailList;
	
	public  relationExtration(){
		headList=new ArrayList<String>();
		relationList=new ArrayList<String>();
		tailList=new ArrayList<String>();
	}

	public List<String> getHeadList() {
		return headList;
	}

	public List<String> getRelationList() {
		return relationList;
	}

	public List<String> getTailList() {
		return tailList;
	}
	
	
	public void readfile(String filePath){		
		FileReader fr =null;
        BufferedReader br =null;
        try { 
        	 fr = new FileReader(filePath);
	         br = new BufferedReader(fr);
	         String des = null;
	         int id=0;
	         while((des = br.readLine()) != null) {
	        	 id++;
	        	 System.out.println(id+"---------");
	        	 System.out.println(des);
	        	 extraction(des);
	        	 insertDB(id);	        		        	 
	         }
	         fr.close();
    		 br.close();	
  		   		 
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }	
	}
		
	
	public void extraction(String des){	
		Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,depparse,natlog,openie");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);        
        Annotation document = new Annotation(des);
        pipeline.annotate(document);      
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        String head = "";
        String relation = "";
        String tail = "";
        for (CoreMap sentence : sentences) {
        	 Collection<RelationTriple> triples = sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
	         for(RelationTriple triple : triples) {
	        	 head = triple.subjectLemmaGloss().toLowerCase();
	        	 relation = triple.relationLemmaGloss().toLowerCase();
	        	 tail = triple.objectLemmaGloss().toLowerCase();
	      //  	 System.out.println(head+"=="+relation+"=="+tail);
	        	 headList.add(head);
	        	 relationList.add(relation);
	        	 tailList.add(tail);	        	 
	         }
        }		
	}
	
	public void insertDB(int id){
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/freelancerresult","root","root");	
			PreparedStatement ps=null; 			
			for(int i=0;i<headList.size();i++){
			//	System.out.println(headList.get(i)+"=="+relationList.get(i)+"=="+tailList.get(i));
				String sql="insert into relation(projectId,head,relation,tail) values(?,?,?,?) ";	
				ps =conn.prepareStatement(sql);
				ps.setInt(1,id);
				ps.setString(2,headList.get(i));
				ps.setString(3,relationList.get(i));
				ps.setString(4,tailList.get(i));
                ps.executeUpdate(); 			    
			    ps.close();			
	   	    }			
			conn.close();			
		}catch(Exception e){
			e.printStackTrace();			
		}
				
		headList.removeAll(headList);
		relationList.removeAll(relationList);
		tailList.removeAll(tailList);
 	
	}
		
	
	public static void main(String args[]){
		relationExtration r=new relationExtration();
		r.readfile("./result/text/10000-.txt");
		System.out.println("finish~");
		
	}
	 

}
