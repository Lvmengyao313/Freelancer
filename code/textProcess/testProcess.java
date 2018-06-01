package textProcess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class testProcess {
	List<String> titleList;
	List<String> DesList;
	Processfuction pf;
	
	public  testProcess(){
		titleList=new ArrayList<String>();
		DesList=new ArrayList<String>();
		pf=new Processfuction();
	}
	
	public List<String> getTitleList() {
		return titleList;
	}

	public List<String> getDesList() {
		return DesList;
	}
	public Processfuction getPf() {
		return pf;
	}

	
	public void DBRead(){
		 try {
	            Class.forName("com.mysql.jdbc.Driver");
	            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/freelancer","root","root");
	            Statement stmt=conn.createStatement();
	       //     String sql="select title, description from project_sub where id<=5000100";
	            String sql="select  title,description from project where  num<=10000"; 
	            ResultSet rs=stmt.executeQuery(sql);
	            String des;
	            while(rs.next()){	                
//	            	titleList.add(rs.getString(1));
	            	des=rs.getString(2).toLowerCase();
	            //	System.out.println(des);
	            	des=pf.replace(des);  
	            	des=pf.Desprocess(des);
	            	DesList.add(des);
	            	System.out.println(des);
	            }
	            
	            conn.close();
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        catch (SQLException e) {
	            e.printStackTrace();
	        }
	}
	
	
	public void writeToFile() {
		try{
			String path="./result/text/10000.txt";
			File file = new File(path);
			if (file.exists()) {
	//			System.out.println("file exists");
			} else {
				file.createNewFile();
			}
            System.setOut(new PrintStream(path));
           
            for(String s:DesList){
                System.out.println(s);           	
            }

        }catch(IOException e) {
            e.printStackTrace();    
        }
	}
	
	
	public static void main(String args[]){
		testProcess tp=new testProcess();
		tp.DBRead();
//		String s=tp.getPf().Desprocess("An online admin that is completely automated to run the site must be included.");
//		System.out.println(tp.DesList.size());
		
		System.out.println("writeToFile");
		tp.writeToFile();
		System.out.println("finish~");
		
	}

	
	
	

}
