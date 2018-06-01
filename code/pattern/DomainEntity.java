package pattern.demo;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;


public class DomainEntity {
	List<String> DomainEntity;
	
	public DomainEntity(){
		DomainEntity=new ArrayList<String>();
	}
	
	public void setDomainList(){
		Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "lmy" ) );
		Session session = driver.session();
		session.run("match (n:DG) return n.value as value")
		.forEachRemaining(record -> {
			DomainEntity.add(record.get("value").asString());
		});		
	}
	
	public List<String> getDomainList(){
		return  DomainEntity;
	}
/*	
	public String mark(String s){

		StringBuffer output=new StringBuffer();
		String[] str=s.split(" ");
		for(int i=0;i<str.length;i++){
			if(i<str.length-1){
				if(DomainEntity.contains(str[i]+" "+str[i+1])){
					output.append(" ("+str[i]+" "+str[i+1]+")");
					i=i+1;
				}
				else{
					if(DomainEntity.contains(str[i])){
						output.append(" ("+str[i]+")");
					}
					else{
						output.append(" "+str[i]);
					}
						
				}
					
			}
			else{
				if(DomainEntity.contains(str[i])){
					output.append(" ("+str[i]+")");
				}	
				else{
					output.append(" "+str[i]);
				}
					
			}
		}
		return output.toString().substring(1);
				
	}
*/	
	public Map<String,Integer> mark(Set<String> set){
		Map<String,Integer> map=new HashMap<String,Integer>();
		for(String s:set){
			if(DomainEntity.contains(s))
				map.put(s, 1);
			else
				map.put(s, 0);
		}
		return map;
		
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		DomainEntity d=new DomainEntity();
		d.setDomainList();
		Set<String> set=new HashSet<String>();
		Map<String,Integer> map=d.mark(set);
		
	}
		
	
	

}
