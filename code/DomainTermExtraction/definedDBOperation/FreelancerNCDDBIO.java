package pers.tong.MappingTools.definedDBOperation;

import java.sql.*;

public class FreelancerNCDDBIO implements AutoCloseable {
	private String dbDriver; 
	private String dbUrl;
	private String dbUser;
	private String dbPass;
	
	private int id;
	private String token;
	private double ncd;
	private int token_category;
	private int max_newpro_category_id;
	private boolean isFinished;
	private Connection connRelation;
	private PreparedStatement preStmtInsert;
	
	public FreelancerNCDDBIO(){
		this.dbDriver = "com.mysql.jdbc.Driver";
		this.dbUrl = "jdbc:mysql://localhost:3306/freelancerresult";//根据实际情况变化
		this.dbUser = "root";
		this.dbPass = "root";
		this.id = 0;
		this.token = "";
		this.ncd = 0;
		this.token_category = 0;
		this.max_newpro_category_id = 0;
		this.isFinished = false;
		
		String insertGroupInfo = "insert into ncd(id,token,ncd,token_category,max_newpro_category_id) values(?,?,?,?,?)";
	    connRelation = getConn();
	    try {
			preStmtInsert = connRelation.prepareStatement(insertGroupInfo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/*
	public FreelancerNCDDBIO(int id,String token,double ncd,int token_category,int max_newpro_category_id){
		this.dbDriver = "com.mysql.jdbc.Driver";
		this.dbUrl = "jdbc:mysql://localhost:3306/freelancerresult";//根据实际情况变化
		this.dbUser = "root";
		this.dbPass = "root";
		this.id = id;
		this.token = token;
		this.ncd = ncd;
		this.token_category = token_category;
		this.max_newpro_category_id = max_newpro_category_id;
		this.isFinished = false;
	}
*/	
	public boolean getisFinished(){
		return this.isFinished;
	}
	public void setToken(String token){
		this.token = token;
		return;
	}
	public void setNcd(double ncd){
		this.ncd = ncd;
		return;
	}
	public void setId(int id){
		this.id = id;
		return;
	}
	public void setTokenCategory(int token_category){
		this.token_category = token_category;
		return;
	}
	public void setMaxNewproCategoryId(int max_newpro_category_id){
		this.max_newpro_category_id = max_newpro_category_id;
		return;
	}
	public void resetisFinished(){
		this.isFinished = false;
	}
	public void setInitializaiton(int id,String token,double ncd,int token_category,int max_newpro_category_id){
		this.id = id;
		this.token = token;
		this.ncd = ncd;
		this.token_category = token_category;
		this.max_newpro_category_id = max_newpro_category_id;
		this.isFinished = false;
		return;
	}
	
	public Connection getConn()
	{
		Connection conn=null;
		try
		{
			Class.forName(this.dbDriver);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		try
		{
			conn = DriverManager.getConnection(this.dbUrl,this.dbUser,this.dbPass);//要输入的是三个参数
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return conn;
	}
	
	public void insertDataToDB(){
		int id = this.id;
		String token = this.token;
		double ncd = this.ncd;
		int token_category = this.token_category;
		int max_newpro_category_id = this.max_newpro_category_id;
		
		int tag = 0;
		
		try//(Connection connRelation = getConn())
		{
			
			preStmtInsert.setInt(1,id);
			preStmtInsert.setString(2, token);
			preStmtInsert.setDouble(3, ncd);
			preStmtInsert.setInt(4, token_category);
			preStmtInsert.setInt(5, max_newpro_category_id);
			
			preStmtInsert.addBatch();
//			tag = preStmtInsert.executeUpdate();
			tag = 1;
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		if(tag == 0){
			//Do nothing
		}
		else{
			this.isFinished = true;
		}
		
		return;
	}
	
	
	public void commit(){
		try {
			this.preStmtInsert.executeBatch();
			this.preStmtInsert.clearBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		if(this.preStmtInsert != null)
		{
			try{
				this.preStmtInsert.close();
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
	}
}
