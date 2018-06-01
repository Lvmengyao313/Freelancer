package Trans.TransE;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Values;

public class trainTXT {
    List<String> head=new ArrayList<String>();
    List<String> tail=new ArrayList<String>();
    List<String> relation=new ArrayList<String>();
    Set<String> entityList=new HashSet<String>();
    Set<String> relationList=new HashSet<String>();
/*    
	public void getriple(){
		Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "lmy" ) );
		Session session = driver.session();
		session.run("match (n1:M)-[r:CV]->(n2:M) where size(r.projectIds)>=3 return distinct id(n1) as n1_id,id(n2) as n2_id")
		.forEachRemaining(record -> {
			int flag=1;
			long n1_id=record.get("n1_id").asLong();
			long n2_id=record.get("n2_id").asLong();
			StatementResult result=session.run("start e1=node("+n1_id+"), e2=node("+n2_id+") "
					+ "match (e1)-[r:CV]->(e2)  "
					+ "return e1.value as e1_val, e2.value as e2_val, r.relation as r_rel order by size(r.projectIds) desc");
			while (result.hasNext()){
				Record record1 = result.next();
				if(flag==1){
					String e1=record1.get("e1_val").asString();
					String e2=record1.get("e2_val").asString();
      	   			String r=record1.get("r_rel").asString();     	   			
      	   			if(e1.length()<=30){
      	   				System.out.println(e1+"	"+e2+"	"+r);
      	   				head.add(e1);
      	   				tail.add(e2);
      	   				relation.add(r);
      	   			}
      	   			flag=0;      	   			     	   			
				}
			}														
		});
		entityList.addAll(head);
		entityList.addAll(tail);
		relationList.addAll(relation);		
		session.close();
		driver.close();
	}
*/	
	public void getriple(){
		Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "lmy" ) );
		Session session = driver.session();
		session.run("match (e1:G)-[r]->(e2:G) where size(r.projectIds)>=3 return e1.value as e1_val, e2.value as e2_val, r.relation as r_rel")
		.forEachRemaining(record -> {
			String e1=record.get("e1_val").asString();
			String e2=record.get("e2_val").asString();
	   		String r=record.get("r_rel").asString();  
	   		System.out.println(e1+"	"+e2+"	"+r);
      	   
//	   		if(e1.length()<=100&&e2.length()<=100){
	   			head.add(e1);
	   			tail.add(e2);
	   			relation.add(r);
//	   		}
														
		});
		entityList.addAll(head);
		entityList.addAll(tail);
		relationList.addAll(relation);		
		session.close();
		driver.close();
	}
		
	public void write() throws FileNotFoundException{
		System.setOut(new PrintStream("./src/Trans/TransE/train.txt"));
		for(int i=0;i<head.size();i++){
			System.out.println(head.get(i)+"	"+tail.get(i)+"	"+relation.get(i));
		}
		System.setOut(new PrintStream("./src/Trans/TransE/entity2id.txt"));
		int i=0;
		for(String s:entityList){			
			System.out.println(s+"	"+i);
			i++;
		}
		System.setOut(new PrintStream("./src/Trans/TransE/relation2id.txt"));
		i=0;
		for(String s:relationList){
			System.out.println(s+"	"+i);
			i++;
		}
	}
	
	
	public static void main(String args[]) throws FileNotFoundException {
		trainTXT t=new trainTXT();
		t.getriple();
		t.write();
	}

}
