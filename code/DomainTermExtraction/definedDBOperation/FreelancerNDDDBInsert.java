package pers.tong.MappingTools.definedDBOperation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FreelancerNDDDBInsert  implements AutoCloseable {
	private String dbDriver; 
	private String dbUrl;
	private String dbUser;
	private String dbPass;
	private Connection connNdd;
	private PreparedStatement preStmtInsert;
	private String insertNddInfo;
	
	private int id;
	private String token;
	private double ndd;
	private int category_id;
	private int is_topic_term;
	//0:不是领域词汇，1：是领域词汇
	
	private boolean isFinished;
	
	public FreelancerNDDDBInsert(){
		this.dbDriver = "com.mysql.jdbc.Driver";
		this.dbUrl = "jdbc:mysql://localhost:3306/freelancerresult";//根据实际情况变化
		this.dbUser = "root";
		this.dbPass = "root";
		this.id = 0;
		this.token = "";
		this.ndd = 0;
		this.category_id = 0;
		this.is_topic_term = 0;
		this.isFinished = false;
		
		this.insertNddInfo = "insert into ndd(id,token,category_id,ndd,is_topic_term) values(?,?,?,?,?)";
		this.connNdd = getConn();
	    try {
			this.preStmtInsert = this.connNdd.prepareStatement(insertNddInfo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/*	
	public FreelancerNDDDBInsert(int id,String token,double ndd,int category_id,int is_topic_term){
		this.dbDriver = "com.mysql.jdbc.Driver";
		this.dbUrl = "jdbc:mysql://localhost:3306/freelancerresult";//根据实际情况变化
		this.dbUser = "root";
		this.dbPass = "root";
		this.id = id;
		this.token = token;
		this.ndd = ndd;
		this.category_id = category_id;
		this.is_topic_term = is_topic_term;
		this.isFinished = false;
		
		this.insertNddInfo = "insert into ndd(id,token,category_id,ndd,is_topic_term) values(?,?,?,?,?)";
		this.connNdd = getConn();
	    try {
			this.preStmtInsert = this.connNdd.prepareStatement(insertNddInfo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/	
	public boolean getisFinished(){
		return this.isFinished;
	}
	public void setToken(String token){
		this.token = token;
		return;
	}
	public void setNdd(double ndd){
		this.ndd = ndd;
		return;
	}
	public void setId(int id){
		this.id = id;
		return;
	}
	//1:is topic term 0: is not topic term
	public void setisTopicTerm(int is_topic_term){
		this.is_topic_term = is_topic_term;
		return;
	}
	public void setCategoryId(int category_id){
		this.category_id = category_id;
		return;
	}
	public void resetInitializaiton(int id,String token,double ndd,int category_id,int is_topic_term){
		this.id = id;
		this.token = token;
		this.ndd = ndd;
		this.category_id = category_id;
		this.is_topic_term = is_topic_term;
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
		double ndd = this.ndd;
		int category_id = this.category_id;
		int is_topic_term = this.is_topic_term;
	
		try
		{
			preStmtInsert.setInt(1,id);
			preStmtInsert.setString(2, token);
			preStmtInsert.setInt(3, category_id);
			preStmtInsert.setDouble(4, ndd);
			preStmtInsert.setInt(5,is_topic_term);
			
			preStmtInsert.addBatch();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		this.isFinished = true;
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
		
		if(connNdd != null )
		{
			try{
				connNdd.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
}
