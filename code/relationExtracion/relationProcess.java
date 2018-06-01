package relationExtracion;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

public class relationProcess {
	List<String> titleList;
	List<Integer> relationIdList;
	List<String> headList;
	List<String> relationList;
	List<String> tailList;
	
	public  relationProcess(){
		titleList=new ArrayList<String>();
		relationIdList=new ArrayList<Integer>();
		headList=new ArrayList<String>();
		relationList=new ArrayList<String>();
		tailList=new ArrayList<String>();
	}
	
	
	
	//==================================head处理========================================
	//i/we/it -->title 
    //you-->person , title--need--person
	//someone-->person
	public void headProcess(){
		selectTitle();		
		try {
			Class.forName("com.mysql.jdbc.Driver");
	        Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/freelancerresult","root","root");
	        Statement stmt=conn.createStatement();
	        String sql="select id,projectId, head from relation_2 ";
	        ResultSet rs=stmt.executeQuery(sql);		       
	        int id;
	        int projectId;
	        String head;
	        while(rs.next()){
	        	id=rs.getInt(1);
	        	projectId=rs.getInt(2);
	        	head=rs.getString(3);

	        	if(head.equals("i")||head.equals("we")||head.equals("it")||head.equals("this")||head.equals("that")){
	        		 changeHead(id,titleList.get(projectId-1));
	        	}	        	
	        	else if(head.equals("you")){
	        		changeHead(id,"someone");
	        		addRelation(projectId,titleList.get(projectId-1),"look for","someone");	        		
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
	
	//提取标题
	public void selectTitle(){
		try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/freelancer","root","root");
            Statement stmt=conn.createStatement();
            String sql="select title from project   ";
            ResultSet rs=stmt.executeQuery(sql);
            String title;
            while(rs.next()){	            	
            	title=rs.getString(1).toLowerCase().replace("\\'", "'");
            	title=title.replace("/", " ").replace("(", " ").replace(")", " ").replace("&", " ").replace(",", " ");
            	String arr[]=title.split("\\s+");
            	StringBuffer sb=new StringBuffer();
            	for(String s:arr){
            		if(s.endsWith(".com")||s.startsWith("www.")||s.startsWith("http")){
            			s="www.com";
            		}
            		sb.append(" "+s);
            	}
            	titleList.add(sb.substring(1));           		                       	
            }
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }		
	}
	
	
	//更新 title
	public void changeHead(int id,String newtitle){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/freelancerresult","root","root");	
			Statement stmt=conn.createStatement(); 
			String sql="update relation_2 set head ='"+newtitle.replace("'", "\\'")+"' where id ="+id;
			System.out.println(sql);
			stmt.executeUpdate(sql); 
			stmt.close();
            conn.close();		
		}catch(Exception e){
			e.printStackTrace();			
		}		
	}
	
	public void changeTail(int id,String newtitle){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/freelancerresult","root","root");	
			Statement stmt=conn.createStatement(); 
			String sql="update relation_2 set tail ='"+newtitle+"' where id ="+id;
			System.out.println(sql);
			stmt.executeUpdate(sql); 
			stmt.close();
            conn.close();		
		}catch(Exception e){
			e.printStackTrace();			
		}		
	}
	
	//插入关系到Relation表
	public void addRelation(int projectId,String head, String relation,String tail){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/freelancerresult","root","root");	
			PreparedStatement ps=null; 				
			String sql="insert into relation_2 (projectId, head,relation,tail) values(?,?,?,?) ";
			ps =conn.prepareStatement(sql);
			ps.setInt(1,projectId);
			ps.setString(2,head);
			ps.setString(3,relation);
			ps.setString(4,tail);
		    ps.executeUpdate(); 		    
		    ps.close();  		
			conn.close();			
		}catch(Exception e){
			e.printStackTrace();			
		}		
		
	}
	
	
	
	
	//=================================tail处理===================================	
	public void tailProcess(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
	        Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/freelancerresult","root","root");
	        Statement stmt=conn.createStatement();
	        String sql="select id,projectId, head,relation,tail from relation ";
	        ResultSet rs=stmt.executeQuery(sql);
	        int id;
	        int projectId;
	        String head;
	        String relation;
	        String tail;
	        String str[];
	        while(rs.next()){
	        	id=rs.getInt(1);
	        	projectId=rs.getInt(2);
	        	head=rs.getString(3);
	        	relation=rs.getString(4);		
	        	tail=rs.getString(5);
	        
	        	str=tail.split(" ");	        		        	
	        	tail="";
	        	int k=0;
	        	while(k<str.length&&(str[k].equals("by")||str[k].equals("for")||str[k].equals("with")||str[k].equals("in")||str[k].equals("as")||str[k].equals("of")||str[k].equals("on")||str[k].equals("to")||str[k].equals("like"))){			
	    			k++;		
	    		}
	        	for(int i=k;i<str.length;i++){
	    			if(str[i].equals("with")&&tail.length()>0){
	    				System.out.println(head+"--"+relation+"--"+tail.substring(1));
	    				insertNewRelation( projectId, head,  relation,  tail.substring(1));
	    				head=tail.substring(1);
	    				relation="with";
	    				tail="";
	    			}
	    			else if(str[i].equals("for")&&tail.length()>0){
	    				System.out.println(head+"--"+relation+"--"+tail.substring(1));
	    				insertNewRelation( projectId, head,  relation,  tail.substring(1));
	    				head=tail.substring(1);
	    				relation="for";
	    				tail="";
	    			}
	    			else if(str[i].equals("in")&&tail.length()>0){
	    				System.out.println(head+"--"+relation+"--"+tail.substring(1));
	    				insertNewRelation( projectId, head,  relation,  tail.substring(1));
	    				head=tail.substring(1);
	    				relation="in";
	    				tail="";
	    			}
	    			else if(str[i].equals("on")&&tail.length()>0){
	    				System.out.println(head+"--"+relation+"--"+tail.substring(1));
	    				insertNewRelation( projectId, head,  relation,  tail.substring(1));
	    				head=tail.substring(1);
	    				relation="on";
	    				tail="";
	    			}
	    			else if(str[i].equals("as")&&tail.length()>0){
	    				System.out.println(head+"--"+relation+"--"+tail.substring(1));
	    				insertNewRelation( projectId, head,  relation,  tail.substring(1));
	    				head=tail.substring(1);
	    				relation="as";
	    				tail="";
	    			}
	    			else if(str[i].equals("of")&&tail.length()>0){
	    				System.out.println(head+"--"+relation+"--"+tail.substring(1));
	    				insertNewRelation( projectId, head,  relation,  tail.substring(1));
	    				head=tail.substring(1);
	    				relation="of";
	    				tail="";
	    			}		
	    			else if(str[i].equals("to")&&tail.length()>0){
	    				System.out.println(head+"--"+relation+"--"+tail.substring(1));
	    				insertNewRelation( projectId, head,  relation,  tail.substring(1));
	    				head=tail.substring(1);
	    				relation="to";
	    				tail="";
	    			}	
	    			else if(str[i].equals("like")&&tail.length()>0){
	    				System.out.println(head+"--"+relation+"--"+tail.substring(1));
	    				insertNewRelation( projectId, head,  relation,  tail.substring(1));
	    				head=tail.substring(1);
	    				relation="like";
	    				tail="";
	    			}
	    			else if(str[i].equals("by")&&tail.length()>0){
	    				System.out.println(head+"--"+relation+"--"+tail.substring(1));
	    				insertNewRelation( projectId, head,  relation,  tail.substring(1));
	    				head=tail.substring(1);
	    				relation="by";
	    				tail="";
	    			}	
	    			else{
	    				tail+=" "+str[i];	    				
	    			}
	    		}
	        	if(tail.length()>0){
	        		System.out.println(head+"--"+relation+"--"+tail.substring(1));
	        		insertNewRelation( projectId, head,  relation,  tail.substring(1));
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
	
	//拆分后的新三元组插入NewRelation
	public void insertNewRelation(int projectId, String head, String relation, String tail){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/freelancerresult","root","root");	
			PreparedStatement ps=null; 				
			String sql="insert into relation_2 (projectId, head,relation,tail) values(?,?,?,?) ";
			ps =conn.prepareStatement(sql);
			System.out.println("=="+projectId);
			ps.setInt(1,projectId);
			ps.setString(2,head);
			ps.setString(3,relation);
			ps.setString(4,tail);
		    ps.executeUpdate(); 		    
		    ps.close();  		
			conn.close();			
		}catch(Exception e){
			e.printStackTrace();			
		}		
	}
	
		
	//=================================删除错误关系===================================	
	public void deleteError() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
	        Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/freelancerresult","root","root");
	        Statement stmt=conn.createStatement();
	        String sql="select id, head,relation,tail from relation ";
	             
	        ResultSet rs=stmt.executeQuery(sql);		       
	        int id;
	        String head;
	        String relation;
	        String tail;
	        while(rs.next()){
	        	id=rs.getInt(1);
	        	head=rs.getString(2);
	        	relation=rs.getString(3);
	        	tail=rs.getString(4);
	        	if(tail.equals("they")||head.equals("they")||head.equals("a")||tail.equals("a")||head.equals("need")||tail.equals("need")||head.equals("use")||tail.equals("use")||tail.equals("http")||tail.equals("i")||tail.equals("you")||tail.equals("we")||tail.equals("it")||tail.equals("this")||tail.equals("that")||head.equals("other")||tail.equals("other")){
	        		 deleteRelation(id);
	        	}
	        	if(relation.equals("be")&&(tail.equals("familar")||tail.equals("fluent")||tail.equals("translate")||tail.equals("experienced")||tail.equals("interested")||tail.equals("easy")||tail.equals("possible")||tail.equals("similar")||tail.equals("necessary")||tail.equals("willing")||tail.equals("sure")||tail.equals("do")||tail.equals("good")||tail.equals("open")||tail.equals("lot")||tail.equals("able"))){
	        		 deleteRelation(id);
	        	}
	        	
	        	if(relation.contains(">")||relation.contains("*")||relation.contains("'")){
	        		 deleteRelation(id);
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
	
	
	public void deleteRelation(int id){
		try {
			Class.forName("com.mysql.jdbc.Driver");
	        Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/freelancerresult","root","root");
	        Statement stmt=conn.createStatement();
	        String sql="delete from relation where id="+id;
	        System.out.println(sql);
	        stmt.executeUpdate(sql);		       
	        stmt.close();
	        conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}				
	}
	
	
	
	
	
	
	public static void main(String args[]) throws FileNotFoundException{		
   //	System.setOut(new PrintStream("./result/test.txt"));  //结果输出到文本
	 	
		relationProcess r=new relationProcess();
		attriExtration att=new attriExtration();
		
	//   r.deleteError();  //1.删除错误关系
	//	 r.tailProcess();  //2. tail处理：根据with of for切分三元组
	//	 att.Process();    //3. 属性抽取
		 r.headProcess();  //4.head tail处理：i we it this  you 
		
		                  //5. IndividualNodeMerge
//		r.selectTitle();
//		System.out.println(r.titleList);
		
		System.out.println("finish~");
		
	}
		

}
