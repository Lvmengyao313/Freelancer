package similarity;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Values;

import similarity.wordSimilarity;

public class nodeSim {
	static double a=0.84;
	static double b=0.8;

	List<List<String>> graghlist;
	public nodeSim(){
		graghlist=new ArrayList<List<String>>();
	}
	public void setGraghlist(int n){
		for(int i=4800;i<=4900;i++){
			graghlist.add(getNodes(i));
			
		}
	}
	
	public void function(int n){
		
		for(int i=1;i<=n-1;i++){			
			for(int j=i+1;j<=n;j++){
				createMap(i,j,graghlist.get(i-1),graghlist.get(j-1));
			}	
		}
		
	}

	
	public List<String> getNeighbors(int i,String str){
		List<String> Neighborslist=new ArrayList<String>();
		Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "lmy" ) );
		Session session = driver.session();
		session.run("MATCH (a:SG)-[]-(b:SG) where a.projectId=$id and a.valve=$value RETURN b.value as val",Values.parameters("id",i+"","value",str))
			.forEachRemaining(record -> {
				Neighborslist.add(record.get("val").asString());
			});
		session.close();
		driver.close();			
		return Neighborslist;
	}
	
	public List<String> getNodes(int i) {
		List<String> Nodeslist=new ArrayList<String>();
		Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "lmy" ) );
		Session session = driver.session();
		session.run("MATCH (a:SG) where a.projectId=$id RETURN a.value as val",Values.parameters("id",i+""))
			.forEachRemaining(record -> {
				Nodeslist.add(record.get("val").asString());
			});
		session.close();
		driver.close();			
		return Nodeslist;
	}
	

	
	
	public void createMap(int index1,int index2,List<String> Nodelist1,List<String> Nodelist2){		
		double sim=0;
			
		for(String str1:Nodelist1){			
			for(String str2:Nodelist2){
				sim=wordSimilarity.phasesim(str1,str2);
				if(sim>=0.84){
					System.out.println(index1+"\t"+index2+"\t"+str1+"\t"+str2+"\t"+sim);
				//	insertMap(index1,index2,str1,str2,sim);
				}
/*				
				else if(sim>=b){
					List<String> Neighborslist1=new ArrayList<String>();
					List<String> Neighborslist2=new ArrayList<String>();
					Neighborslist1=getNeighbors(index1,str1);
					Neighborslist2=getNeighbors(index2,str2);
					if(haveNeighborMap(Neighborslist1,Neighborslist2)){
						System.out.println(index1+"\t"+index2+"\t"+str1+"\t"+str2+"\t"+sim);
	//					insertMap(index1,index2,str1,str2,sim);
					}
					
					Neighborslist1.clear();
					Neighborslist1.clear();
				}	
*/				
			}
		}	
	}
	
	public static boolean haveNeighborMap(List<String> Neighborslist1,List<String> Neighborslist2){
		for(String str1:Neighborslist1){			
			for(String str2:Neighborslist2){
				if(wordSimilarity.phasesim(str1,str2)>a){
					return true;
				}
			}
		}
		return false;
	}
	

	

	public static void main(String[] args) throws Exception {
		
		nodeSim ns=new nodeSim();
	    System.setOut(new PrintStream("./src/similarity/t100_1.txt"));	    	    
	    long startTime = System.currentTimeMillis();    //获取开始时间	
	    int n=100;
	    ns.setGraghlist(n);
	    long midTime = System.currentTimeMillis();    //获取结束时间	     
	    ns.function(n);
	    long endTime = System.currentTimeMillis();    //获取结束时间
	    System.out.println("程序运行时间：" + (midTime - startTime)/1000 + "s");
	    System.out.println("程序运行时间：" + (endTime - midTime)/1000 + "s");
		System.out.println("程序运行时间：" + (endTime - startTime)/1000 + "s"); 
	}

}
