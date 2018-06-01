package pers.tong.MappingTools.freelancerOperation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.*;

public class setSkillName {
	
	public int getCount(String filelist) throws IOException{
		FileReader fr = new FileReader(filelist);
	    BufferedReader br = new BufferedReader(fr);	
		 int count=0;
		 while((br.readLine()) != null) {
		     count++;
		 }
		 System.out.println(count);
		 return count;
	}
		
	public String[] setNcdSkillName(String filelist,int count) throws IOException{
		int i;
		int k=0;
		String str;
		
		FileReader fr = new FileReader(filelist);
	    BufferedReader br = new BufferedReader(fr);	

		String skill[]=new String[count];

		while((str = br.readLine()) != null) {
			StringBuffer s=new StringBuffer();
			
			for(i=0;i<str.length()-4;i++){				
				//if(!(str.charAt(i)>='0'&&str.charAt(i)<='9') )
					s.append(str.charAt(i));								
			}			
		    skill[k++]=s.toString();	
		}		
		 return skill;					
	}
	
	public void setSkill(String skill[]){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/freelancerresult","root","root");	
			Statement stmt=conn.createStatement(); 
			for(int i=0;i<skill.length;i++){
			//	String sql="update ncd set max_newpro_category_name='"+skill[i]+"' where max_newpro_category_id="+i;
				String sql="update ndd set category_name='"+skill[i]+"' where category_id="+i;
				System.out.println(sql);
			    stmt.executeUpdate(sql); 
			}
			
			stmt.close();
            conn.close();
			
		}catch(Exception e){
			e.printStackTrace();			
		}
				
		
	}
	
	public static void main(String[] args) throws IOException {
		setSkillName ncd=new setSkillName();
		int count=ncd.getCount("./result/filenamelist.txt");
		String[] skill=ncd.setNcdSkillName("./result/filenamelist.txt",count);
		
		for(int i=0;i<skill.length;i++)
			  System.out.println(skill[i]);
		
		ncd.setSkill(skill);
		
		System.out.println("finish");
				


	}

}
