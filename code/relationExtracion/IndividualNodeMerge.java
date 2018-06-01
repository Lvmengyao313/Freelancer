package relationExtracion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class IndividualNodeMerge {
	private String nameofDB1;
	private String nameofDB2;
	private String nameofProjectTable;
	private String nameofRelationTable;
	private String dbDriver;
	private String dbUrl1;
	private String dbUrl2;
	private String dbUser;
	private String dbPass;
	
    public void init(String nameOfDB1,String nameOfDB2,String nameofProjectTable,String nameofRelationTable){
    	this.nameofDB1 = nameOfDB1;
    	this.nameofDB2 = nameOfDB2;
    	this.nameofProjectTable = nameofProjectTable;
    	this.nameofRelationTable = nameofRelationTable;
    	this.dbDriver = "com.mysql.jdbc.Driver";
    	this.dbUrl1 = "jdbc:mysql://localhost:3306/"+this.nameofDB1;
    	this.dbUrl2 = "jdbc:mysql://localhost:3306/"+this.nameofDB2;
    	this.dbUser = "root";
    	this.dbPass = "root";
    	
    }
    public void function(){
    	
    	List<Integer> projectIds = new ArrayList<>();
    	String selectProjectIdInfo = "select projectId from "+this.nameofRelationTable +" group by projectId;";
    	Connection connProjectId = getConn2();
    	Statement stmtProjectId = null;
		try
		{
			stmtProjectId = connProjectId.createStatement();
			ResultSet rsProjectIdInfo = stmtProjectId.executeQuery(selectProjectIdInfo);
			
			while(rsProjectIdInfo.next())
			{
				projectIds.add(rsProjectIdInfo.getInt("projectId"));
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		if(stmtProjectId != null)
		{
			try{
				stmtProjectId.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		if(connProjectId != null )
		{
			try{
				connProjectId.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		
	
		List<String> headsSum = new ArrayList<>();
		List<String> tailsSum = new ArrayList<>();
		List<Integer> pojectIdSum = new ArrayList<>();
		
		String selectRelationInfo = "select head,tail,projectId from "+this.nameofRelationTable+";";
    	Connection connRelation = getConn2();
    	Statement stmtRelation = null;
		try
		{
			stmtRelation = connRelation.createStatement();
			ResultSet rsRelationInfo = stmtRelation.executeQuery(selectRelationInfo);
			
			while(rsRelationInfo.next())
			{
				headsSum.add(rsRelationInfo.getString("head"));
				tailsSum.add(rsRelationInfo.getString("tail"));
				pojectIdSum.add(rsRelationInfo.getInt("projectId"));
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		if(stmtRelation != null)
		{
			try{
				stmtRelation.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		if(connRelation != null )
		{
			try{
				connRelation.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		List<String> projectTitles = new ArrayList<>();
		for(Integer projectid: projectIds){
			String selectTitleInfo = "select title from "+this.nameofProjectTable+" where num = "+projectid+";";
	    	Connection connTitle = getConn1();
	    	Statement stmtTitle = null;
			try
			{
				stmtTitle = connTitle.createStatement();
				ResultSet rsTitleInfo = stmtTitle.executeQuery(selectTitleInfo);
				String title;
				while(rsTitleInfo.next())
				{
					title=rsTitleInfo.getString(1).toLowerCase().replace("\\'", "'");
					title=title.replace("/", " ").replace("(", " ").replace(")", " ").replace("&", " ").replace(",", " ");
	            	String arr[]=title.split("\\s+");
	            	StringBuffer sb=new StringBuffer();
	            	for(String s:arr){
	            		if(s.endsWith(".com")||s.startsWith("www.")||s.startsWith("http")){
	            			s="www.com";
	            		}
	            		sb.append(" "+s);
	            	}										
					projectTitles.add(sb.substring(1));
				}

			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			
			if(stmtTitle != null)
			{
				try{
					stmtTitle.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
			
			if(connTitle != null )
			{
				try{
					connTitle.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		

		for(int indexofId = 0 ; indexofId < projectIds.size();indexofId++){
			List<String> headTemps = new ArrayList<>();
			List<String> tailTemps = new ArrayList<>();
			String title = projectTitles.get(indexofId);
			Integer id =  projectIds.get(indexofId);
			for(int index = 0;index < pojectIdSum.size();index++){
				if(pojectIdSum.get(index).equals(id)){
					headTemps.add(headsSum.get(index));
					tailTemps.add(tailsSum.get(index));
				}
			}	
			for(int indexofhead = 0; indexofhead <headTemps.size();indexofhead++){
				if(tailTemps.contains(headTemps.get(indexofhead))||title.equals(headTemps.get(indexofhead))){
					//do nothing
					System.out.println(headTemps.get(indexofhead));
				}
				else{
					String insertRelationInfo = "insert into "+this.nameofRelationTable+" (projectId,head,relation,tail) values(?,?,?,?)";
					Connection connRelationAdd = getConn2();
					PreparedStatement preStmtInsert = null;
					try
					{
						preStmtInsert = connRelationAdd.prepareStatement(insertRelationInfo);
						preStmtInsert.setInt(1, id);
						preStmtInsert.setString(2, title);
						preStmtInsert.setString(3,"r");
						preStmtInsert.setString(4,headTemps.get(indexofhead));
						int tag = 0;
						tag = preStmtInsert.executeUpdate();
						System.out.println(tag);
					}					
					catch (SQLException e)
					{
						e.printStackTrace();
					}
					
					if(preStmtInsert != null)
					{
						try{
							preStmtInsert.close();
						}
						catch(SQLException e)
						{
							e.printStackTrace();
						}
					}
					
					if(connRelationAdd != null )
					{
						try{
							connRelationAdd.close();
						}
						catch(SQLException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		}
		
    }
    
    public Connection getConn1()
	{
		Connection conn=null;
		try
		{
			Class.forName(this.dbDriver);
			conn = DriverManager.getConnection(this.dbUrl1,this.dbUser,this.dbPass);//要输入的是三个参数
		}catch (ClassNotFoundException e){		
			e.printStackTrace();
		}catch (SQLException e){
			e.printStackTrace();
		}
		return conn;
	}
    
    public Connection getConn2()
	{
		Connection conn=null;
		try
		{
			Class.forName(this.dbDriver);
			conn = DriverManager.getConnection(this.dbUrl2,this.dbUser,this.dbPass);//要输入的是三个参数
		}catch (ClassNotFoundException e){		
			e.printStackTrace();
		}catch (SQLException e){
			e.printStackTrace();
		}
		return conn;
	}
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IndividualNodeMerge IndividualNode = new IndividualNodeMerge();
		IndividualNode.init("freelancer","freelancerresult", "project", "relation_2qc");
		IndividualNode.function();
		System.out.println("finish~");
	}
}
