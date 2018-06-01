package pattern;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Values;

public class keyword {
	List<String> keywordList=new ArrayList<String>();
	
	public void getkeyword(){
		try{	
			 Class.forName("com.mysql.jdbc.Driver");
	         Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/freelancerresult","root","root");
	         Statement stmt=conn.createStatement();
	         String sql="select token from ndd where is_topic_term=1 ";
	         ResultSet rs=stmt.executeQuery(sql);	         
	         while(rs.next()){
	        	 keywordList.add(rs.getString(1));  		            	
	         }
	         stmt.close();
	         conn.close();
	     } catch (ClassNotFoundException e) {
	         e.printStackTrace();
	     }
	     catch (SQLException e) {
	         e.printStackTrace();
	     }				
	}
	
	public Boolean isKey(String ph){
		ph=ph.replace("-", " ");
		String str[]=ph.split(" ");
		if(str.length>=8)
			return false;
		for(String s:str){
			if(keywordList.contains(s)){
				return true;
			}
		}
		return false;
	}
	
	public static String getMostEntity(List<String> list){
		 Map<String, Integer> map = new HashMap<String, Integer>();
		 for(String s : list){
	            if(map.containsKey(s)){
	                map.put(s, map.get(s)+1);
	            }else{ 
	                map.put(s, 1);	                
	            }
	     }
		 int max=0;
	     String maxString = null;
	     for(String s : map.keySet()){
	            int num = map.get(s);
	            if(num>max){
	                maxString = s;
	                max = num;
	            }
	        }
	     return maxString;
	}
/*	
	public void getNodes(){
		Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "lmy" ) );
		Session session = driver.session();
		session.run("match(ns:MG)  return id(ns) as id,ns.value as ns_value")
		.forEachRemaining(record -> {
			long ns_id=record.get("id").asLong();
			String ph=record.get("ns_value").asString();
			if(isKey(ph)){
				String sql="start n=node("+ns_id+") set n:K";
//				System.out.println(sql);
				session.run(sql);
				System.out.println(ns_id+" "+ph);
			}
		});
	}	
*/	

	public void getNodes(){
		Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "lmy" ) );
		Session session = driver.session();
		session.run("match(ns:MG)  return id(ns) as id,ns.values as ns_values")
		.forEachRemaining(record -> {
			long ns_id=record.get("id").asLong();
			List<String> ns_values=new ArrayList<String>();				
			ns_values.addAll(convert(record.get("ns_values").asList(), String.class));
			String ph=getMostEntity(ns_values);
			session.run("start n=node("+ns_id+") set n.value=$value",Values.parameters("value",ph));
			if(isKey(ph)){
				session.run("start n=node("+ns_id+") set n.key=true");
				System.out.println(ns_id+" "+ph);
			}
		});
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> convert(List<?> list, Class<T> c) {
	    return (List<T>)list;
	}
	
	public static void main(String[] args) {
		keyword en =new keyword();
		en.getkeyword();
		en.getNodes();
		
	}
	

}
