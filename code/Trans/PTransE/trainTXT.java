package Trans.PTransE;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
    Map<String,Long> entity2id=new HashMap<String,Long>();
    Map<String,Integer> relation2id=new HashMap<String,Integer>();
    Map<Integer,String> id2relation=new HashMap<Integer,String>();
    Map<List<String>,List<Map<String,Double>>> confidence=new HashMap<List<String>,List<Map<String,Double>>>();
    List<HashMap<String,Double>> train_pra=new ArrayList<HashMap<String,Double>>();
    Map<String,Integer> path_dict=new HashMap<String,Integer>();
    Map<String,Integer> r_dict=new HashMap<String,Integer>();
    Map<String,Integer> path_r_dict=new HashMap<String,Integer>();
    int numR=0;

	public void getriple(){
		Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "lmy" ) );
		Session session = driver.session();
		
		session.run("match (n1:M)-[r:CV]->(n2:M) where size(r.projectIds)>=3 "
				+ "return id(n1) as n1_id,id(n2) as n2_id, n1.value as n1_value, n2.value as n2_value, r.relation as r_relation")
		.forEachRemaining(record -> {
			long n1_id=record.get("n1_id").asLong();
			long n2_id=record.get("n2_id").asLong();
			String n1_value=record.get("n1_value").asString().replace(" ","_");
			String n2_value=record.get("n2_value").asString().replace(" ","_");
			String r_relation=record.get("r_relation").asString().replace(" ","_");
			head.add(n1_value);
			tail.add(n2_value);
			relation.add(r_relation);
			if(!entity2id.containsKey(n1_value)){
				entity2id.put(n1_value, n1_id);
			}
			if(!entity2id.containsKey(n2_value)){
				entity2id.put(n2_value, n2_id);
			}

			if(!relation2id.containsKey(r_relation)){
				relation2id.put(r_relation, numR);
				id2relation.put( numR,r_relation);
				numR++;
				
			}
				
		});
		
		System.out.println(entity2id);
		System.out.println(id2relation);
		
		//2Â·¾¶
		for(int i=0;i<head.size();i++){
			long id1=entity2id.get(head.get(i));
			long id2=entity2id.get(tail.get(i));
			int idR=relation2id.get(relation.get(i));
			System.out.println(id1+" "+id2);
			HashMap<String,Double> m=new HashMap<String,Double>();
			session.run("start e1=node("+id1+"),e2=node("+id2+") "
					+ " match (e1)-[r1:CV]->(e:M)-[r2:CV]->(e2) where size(r1.projectIds)>=3 and size(r2.projectIds)>=3"
					+ " return distinct  r1.relation as r1_relation, r2.relation as r2_relation,"
					+ " r1.projectIds as r1_projectIds, r2.projectIds as r2_projectIds")
			.forEachRemaining(record -> {
				String r1_relation=record.get("r1_relation").asString().replace(" ","_");
				String r2_relation=record.get("r2_relation").asString().replace(" ","_");
				List<String> r1_projectIds=new ArrayList<String>();
				r1_projectIds.addAll(convert(record.get("r1_projectIds").asList(), String.class));
				List<String> r2_projectIds=new ArrayList<String>();
				r2_projectIds.addAll(convert(record.get("r2_projectIds").asList(), String.class));
				r1_projectIds.retainAll(r2_projectIds);
				
				if(r1_projectIds.size()>=2){
					System.out.println(r1_relation+" "+r2_relation);		
					double reli=1.0;				
					m.put(relation2id.get(r1_relation)+" "+relation2id.get(r2_relation), reli);				

					if(!path_dict.containsKey(relation2id.get(r1_relation)+" "+relation2id.get(r2_relation))){
						path_dict.put(relation2id.get(r1_relation)+" "+relation2id.get(r2_relation), 0);
					}
					path_dict.put(relation2id.get(r1_relation)+" "+relation2id.get(r2_relation), path_dict.get(relation2id.get(r1_relation)+" "+relation2id.get(r2_relation))+1);
					
					if(!path_r_dict.containsKey(relation2id.get(r1_relation)+" "+relation2id.get(r2_relation)+"->"+idR)){
						path_r_dict.put(relation2id.get(r1_relation)+" "+relation2id.get(r2_relation)+"->"+idR, 0);
					}
					path_r_dict.put(relation2id.get(r1_relation)+" "+relation2id.get(r2_relation)+"->"+idR, path_r_dict.get(relation2id.get(r1_relation)+" "+relation2id.get(r2_relation)+"->"+idR)+1 );				
				}
					
					
			});
			//3Â·¾¶
			session.run("start e1=node("+id1+"),e2=node("+id2+") "
					+ " match (e1)-[r1:CV]->(n:M)-[r2:CV]->(m:M)-[r3:CV]->(e2) where size(r1.projectIds)>=3 and size(r2.projectIds)>=3  and size(r2.projectIds)>=3"
					+ " return distinct  r1.relation as r1_relation, r2.relation as r2_relation,  r3.relation as r3_relation, "
					+ " r1.projectIds as r1_projectIds, r2.projectIds as r2_projectIds,  r3.projectIds as r3_projectIds")
			.forEachRemaining(record -> {
				String r1_relation=record.get("r1_relation").asString().replace(" ","_");
				String r2_relation=record.get("r2_relation").asString().replace(" ","_");
				String r3_relation=record.get("r3_relation").asString().replace(" ","_");
				List<String> r1_projectIds=new ArrayList<String>();
				r1_projectIds.addAll(convert(record.get("r1_projectIds").asList(), String.class));
				List<String> r2_projectIds=new ArrayList<String>();
				r2_projectIds.addAll(convert(record.get("r2_projectIds").asList(), String.class));
				List<String> r3_projectIds=new ArrayList<String>();
				r3_projectIds.addAll(convert(record.get("r3_projectIds").asList(), String.class));
				r1_projectIds.retainAll(r2_projectIds);
				r1_projectIds.retainAll(r3_projectIds);
				
				if(r1_projectIds.size()>=2){
					System.out.println(r1_relation+" "+r2_relation+" "+r3_relation);
					double reli=1.0;				
					m.put(relation2id.get(r1_relation)+" "+relation2id.get(r2_relation)+" "+relation2id.get(r3_relation), reli);				

					if(!path_dict.containsKey(relation2id.get(r1_relation)+" "+relation2id.get(r2_relation)+" "+relation2id.get(r3_relation))){
						path_dict.put(relation2id.get(r1_relation)+" "+relation2id.get(r2_relation)+" "+relation2id.get(r3_relation), 0);
					}
					path_dict.put(relation2id.get(r1_relation)+" "+relation2id.get(r2_relation)+" "+relation2id.get(r3_relation), path_dict.get(relation2id.get(r1_relation)+" "+relation2id.get(r2_relation)+" "+relation2id.get(r3_relation))+1);
					
					if(!path_r_dict.containsKey(relation2id.get(r1_relation)+" "+relation2id.get(r2_relation)+" "+relation2id.get(r3_relation)+"->"+idR)){
						path_r_dict.put(relation2id.get(r1_relation)+" "+relation2id.get(r2_relation)+" "+relation2id.get(r3_relation)+"->"+idR, 0);
					}
					path_r_dict.put(relation2id.get(r1_relation)+" "+relation2id.get(r2_relation)+" "+relation2id.get(r3_relation)+"->"+idR, path_r_dict.get(relation2id.get(r1_relation)+" "+relation2id.get(r2_relation)+" "+relation2id.get(r3_relation)+"->"+idR)+1 );
					
				}
				
			});
//			System.out.println(m);
			train_pra.add(m);
		}		
		System.out.println(train_pra.size());	

		session.close();
		driver.close();
	}
	
	public void write() throws FileNotFoundException{
		System.setOut(new PrintStream("./src/Trans/PTransE/entity2id.txt"));
		int i=0;
		for(String s:entity2id.keySet()){			
			System.out.println(s+"	"+i);
			i++;
		}
		System.setOut(new PrintStream("./src/Trans/PTransE/relation2id.txt"));

		for(int j:id2relation.keySet()){
			System.out.println(id2relation.get(j)+"	"+j);
		}
		
		System.setOut(new PrintStream("./src/Trans/PTransE/train_pra.txt"));
		for(int j=0;j<head.size();j++){
			System.out.println(head.get(j)+" "+tail.get(j)+" "+relation2id.get(relation.get(j)));			
			if(train_pra.get(j).size()==0){
				System.out.println(0);
				continue;
			}				
			double sum=0.0;
			for(String s:train_pra.get(j).keySet()){
				sum+=train_pra.get(j).get(s);
			}
			for(String s:train_pra.get(j).keySet()){
				if(train_pra.get(j).get(s)/sum>=0.01)
					train_pra.get(j).put(s, train_pra.get(j).get(s)/sum);
				else
					train_pra.remove(s);
			}
			System.out.print(train_pra.get(j).size()+" ");
			for(String s:train_pra.get(j).keySet()){
				int len=s.split(" ").length;
				System.out.print(len+" "+s+" "+train_pra.get(j).get(s)+" ");
			}
			System.out.println();
		}
				
		System.setOut(new PrintStream("./src/Trans/PTransE/confident.txt"));
		
		for(String p:path_dict.keySet()){
			System.out.println(p.split(" ").length+" "+p);
			Map<Integer,Double> out=new  HashMap<Integer,Double>();
			for(int idR:id2relation.keySet()){
				if(path_r_dict.containsKey(p+"->"+idR)){
					double pro=path_r_dict.get(p+"->"+idR)*1.0/path_dict.get(p);
					out.put(idR, pro);
				}
			}
			System.out.print(out.size()+" ");
			for(int id:out.keySet()){
				System.out.print(id+" "+out.get(id)+" ");
			}
			System.out.println();
			
		}		
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> convert(List<?> list, Class<T> c) {
	    return (List<T>)list;
	}
	
	
	public static void main(String args[]) throws FileNotFoundException {
		trainTXT t=new trainTXT();
		t.getriple();
		t.write();
	}

}
