package pattern.demo;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Values;
import pattern.demo.cluster;
import pattern.demo.Link;
public class demo {
	
	String outputF=new String();
	String outputB=new String();
	/*簇状模式*/
	public List<cluster> findCluster(String input){
		List<cluster> clusterList=new ArrayList<cluster>();
		Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "lmy" ) );
		Session session = driver.session();
		session.run("match (n1:DG)-[]-(n2:DG) where n1.value=$value "
				+ "return distinct n2.value as n2_value,n1.projectIds as n1_projectIds,n2.projectIds as n2_projectIds",
				Values.parameters("value",input))
			.forEachRemaining(record -> {
				String n2_value=record.get("n2_value").asString();
				List<Integer> n1_projectIds=new ArrayList<Integer>();
        		n1_projectIds.addAll(convert(record.get("n1_projectIds").asList(), Integer.class));
				List<Integer> n2_projectIds=new ArrayList<Integer>();
        		n2_projectIds.addAll(convert(record.get("n2_projectIds").asList(), Integer.class));
        		n1_projectIds.retainAll(n2_projectIds);
        		System.out.println(n2_value+" "+n1_projectIds);
				cluster c=new cluster();
				c.setValue(n2_value);
				c.setProjectIds(n1_projectIds);
				clusterList.add(c);
		 }); 
		return clusterList;
	}
	
	
	/*链路模式*/
	public Link findLink(String input){

		Link link=new Link();
		Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "lmy" ) );
		Session session = driver.session();
		session.run("match (n:MG{value:$value}) return id(n) as id, n.value as value,n.projectIds as projectIds",Values.parameters("value",input))
		.forEachRemaining(record -> {
			List<Integer> projectIds=new ArrayList<Integer>();
			projectIds.addAll(convert(record.get("projectIds").asList(), Integer.class));
			List<Long> nodeIds=new LinkedList<Long>();
			long id=record.get("id").asLong();
			nodeIds.add(id);
			String s=record.get("value").asString();
//			double pp=Math.log(projectIds.size())/projectIds.size();
			double pp=0.01;
			
			System.out.println("Front Pattern:");		  //前置路径
			LinkFront(1.0,projectIds, nodeIds,id,s,pp);	
			link.setOutputFrontList(StringtoList(outputF));
			System.out.println("Behind Pattern:");        //后置路径
			LinkBehind(1.0,projectIds, nodeIds,id,s,pp);
			link.setOutputBehindList(StringtoList(outputB));

		});
		session.close();
		driver.close();
		return link;
		
		
	}
	
	public void LinkBehind(double P,List<Integer> front_projectIds,List<Long> nodeIds,long nodeId,String s,double pp){
		Boolean flag=true;
		Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "lmy" ) );
		Session session = driver.session();
		StatementResult result=session.run("match (n1:MG)-[r:CV]->(n2:MG) "
				+ "where id(n1)=$nodeId  "
				+ "return r.relation as relation, n2.value as n2_value,r.projectIds as r_projectIds,id(n2) as id",
				Values.parameters("nodeId",nodeId));
		if(!result.hasNext()&&nodeIds.size()>=2){
			System.out.println(s);
			outputB+=s+"\n";
		}
		while (result.hasNext()){
			Record record = result.next();
			List<Integer> projectIds=new ArrayList<Integer>();
			projectIds.addAll(convert(record.get("r_projectIds").asList(), Integer.class));
			if(nodeIds.size()!=1){
				projectIds.retainAll(front_projectIds);
			}			
			long id=record.get("id").asLong();
			
			if(nodeIds.contains(id)){
				if(nodeIds.size()>=2&&!result.hasNext()&&flag){
					System.out.println(s);
					outputB+=s+"\n";
				}
			}
			else{
				double p=(double)projectIds.size()/(double)front_projectIds.size();
//				System.out.println(projectIds.size()+" "+front_projectIds.size()+" "+p);
				if(P*p>=pp){
					String s1=s+"->"+record.get("relation").asString()+"->"+record.get("n2_value").asString();
					List<Long> nodeIds2=new LinkedList<Long>();
					nodeIds2.addAll(nodeIds);
					nodeIds2.add(id);
					flag=false;
					LinkBehind(P*p, projectIds, nodeIds2, id,s1,pp);
				}
				else{
					if(nodeIds.size()>=2&&!result.hasNext()&&flag){						
						System.out.println(s);
						outputB+=s+"\n";
					}
				}
			}
			
		}
				
		session.close();
		driver.close();
	}
	
	public void LinkFront( double P,List<Integer> front_projectIds,List<Long> nodeIds,long nodeId,String s,double pp){
//		System.out.println("nodeId："+nodeId+"  "+"s："+s);
		Boolean flag=true;
		Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "lmy" ) );
		Session session = driver.session();
		StatementResult result=session.run("match (n1:MG)<-[r:CV]-(n2:MG) "
				+ "where id(n1)=$nodeId "
				+ "return r.relation as relation, n2.value as n2_value,r.projectIds as r_projectIds,id(n2) as id",
				Values.parameters("nodeId",nodeId));
		if(!result.hasNext()&&nodeIds.size()>=2){
			System.out.println(s);
			outputF+=s+"\n";
		}
		while (result.hasNext()){
			Record record = result.next();
			List<Integer> projectIds=new ArrayList<Integer>();
			projectIds.addAll(convert(record.get("r_projectIds").asList(), Integer.class));
			if(nodeIds.size()!=1){
				projectIds.retainAll(front_projectIds);
			}			
			long id=record.get("id").asLong();
			
			if(nodeIds.contains(id)){
				if(nodeIds.size()>=2&&!result.hasNext()&&flag){
					System.out.println(s);
					outputF+=s+"\n";
				}
			}
			else{
				double p=(double)projectIds.size()/(double)front_projectIds.size();
//				System.out.println(projectIds.size()+" "+front_projectIds.size()+" "+p);
				if(p*P>=pp){
					String s1=record.get("n2_value").asString()+"->"+record.get("relation").asString()+"->"+s;
					List<Long> nodeIds2=new LinkedList<Long>();
					nodeIds2.addAll(nodeIds);
					nodeIds2.add(id);
					flag=false;
					LinkFront(P*p, projectIds, nodeIds2, id,s1,pp);
				}
				else{
					if(nodeIds.size()>=2&&!result.hasNext()&&flag){						
						System.out.println(s);
						outputF+=s+"\n";
					}
				}
			}
			
		}
				
		session.close();
		driver.close();
	}
	
	
	public List<ArrayList<String>> StringtoList(String output){

		List<ArrayList<String>> outputList=new ArrayList<ArrayList<String>>();
		String str[]=output.split("\n");
		for(String s:str){
			String ss[]=s.split("->");			
			List<String> sList=new ArrayList<String>();
			sList.addAll(Arrays.asList(ss));
			outputList.add((ArrayList<String>) sList);
		}
		return outputList;
	}
	
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> convert(List<?> list, Class<T> c) {
	    return (List<T>)list;
	}

	
	public static void main(String[] args) throws FileNotFoundException {
		demo d=new demo();
/*		
		
        //cluster patter for "website design"
		List<cluster> clusterList=d.findCluster("website design");
		for(cluster c: clusterList){
			System.out.println(c.getValue()+" "+c.getProjectIds());
		}
*/
		
		//link patter for "app"
		Link link = d.findLink("app");
		for(List<String> slist: link.getOutputFrontList()){
			System.out.println("-- "+slist);
		}
		for(List<String> slist: link.getOutputBehindList()){
			System.out.println("++"+slist);
		}
		
	}
}
