package pers.tong.MappingTools.definedDBOperation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

public class FreelancerNCDDBRead {
	private String dbDriver; 
	private String dbUrl;
	private String dbUser;
	private String dbPass;
	
	private double thresholdSetting;
	private int token_category;
	private List<Integer> choosed_categoryIdList;
	private List<String> choosed_tokenList;
	
	private boolean isFinished;
	private boolean isChoosedbyThreshold;
	
	public FreelancerNCDDBRead(){
		this.dbDriver = "com.mysql.jdbc.Driver";
		this.dbUrl = "jdbc:mysql://localhost:3306/freelancerresult";//根据实际情况变化
		this.dbUser = "root";
		this.dbPass = "root";
		
		this.choosed_categoryIdList = new ArrayList<>();
		this.choosed_tokenList = new ArrayList<>();
		this.token_category = 0;
		this.thresholdSetting = 0;
		this.isFinished = false;
		this.isChoosedbyThreshold = false;
	}
	//u may choose input the token_category,false
/*	
	public FreelancerNCDDBRead(int token_category,boolean ChoosedbyThreshold){
		this.dbDriver = "com.mysql.jdbc.Driver";
		this.dbUrl = "jdbc:mysql://localhost:3306/freelancerresult";//根据实际情况变化
		this.dbUser = "root";
		this.dbPass = "root";
		
		this.choosed_categoryIdList = new ArrayList<>();
		this.choosed_tokenList = new ArrayList<>();
		this.token_category = token_category;
		this.thresholdSetting = 0;
		this.isFinished = false;
		this.isChoosedbyThreshold = ChoosedbyThreshold;
	}
	
	// u may choose input the thresholdSetting,true
	public FreelancerNCDDBRead(double thresholdSetting,boolean ChoosedbyThreshold){
		this.dbDriver = "com.mysql.jdbc.Driver";
		this.dbUrl = "jdbc:mysql://localhost:3306/freelancerresult";//根据实际情况变化
		this.dbUser = "root";
		this.dbPass = "root";
		
		this.choosed_categoryIdList = new ArrayList<>();
		this.choosed_tokenList = new ArrayList<>();
		this.token_category = 0;
		this.thresholdSetting = thresholdSetting;
		this.isFinished = false;
		this.isChoosedbyThreshold = ChoosedbyThreshold;
	}
*/	
	// u may choose the first kind of the initialization,then u can choose setThresholdSetting,the isChoosedbyThreshold will be set as true
	public void setThresholdSetting(double thresholdSetting){
		this.thresholdSetting = thresholdSetting;
		this.isChoosedbyThreshold = true;
	}
	
	// u may choose the first kind of the initialization,then u can choose setTokenCategory,the isChoosedbyThreshold will be set as false
	public void setTokenCategory(int token_category) {
		// TODO Auto-generated method stub
		this.token_category = token_category;
		this.isChoosedbyThreshold = false;
		return;
	}
	public boolean getisFinished() {
		// TODO Auto-generated method stub
		return this.isFinished;
	}
	public boolean getisChoosedbyThreshold(){
		return this.isChoosedbyThreshold;
	}
	public List<Integer> getChoosedCategoryIdList() {
		// TODO Auto-generated method stub
		List<Integer> choosed_categoryIdList = new ArrayList<>();
		if(this.getisFinished()){
			choosed_categoryIdList = this.choosed_categoryIdList;	
		}
		else{
			choosed_categoryIdList = null;
		}
		return choosed_categoryIdList;
	}

	public List<String> getChoosedTokenList() {
		// TODO Auto-generated method stub
		List<String> choosed_tokenList = new ArrayList<>();
		if(this.getisFinished()){
			choosed_tokenList = this.choosed_tokenList;
		}
		else{
			choosed_tokenList = null;
		}
		return choosed_tokenList;
	}
	
	public Connection getConn()
	{
		Connection conn=null;
		try
		{
			Class.forName(dbDriver);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		try
		{
			conn = DriverManager.getConnection(dbUrl,dbUser,dbPass);//要输入的是三个参数
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return conn;
	}
	public void chooseDataFromDBbyCat() {
		// TODO Auto-generated method stub
		int token_category_choosed = this.token_category;
		String token_temp = "";
		int max_newpro_category_id = 0;
		String selectDataInfo = "select token,max_newpro_category_id from ncd where token_category = '"+token_category_choosed+"';";
		Connection connData = getConn();
		Statement stmtData = null;
		
		try
		{
			stmtData = connData.createStatement();
			ResultSet rsDataInfo = stmtData.executeQuery(selectDataInfo);
			
			while(rsDataInfo.next())
			{
				token_temp = rsDataInfo.getString(1);
				max_newpro_category_id = rsDataInfo.getInt(2);
				this.choosed_tokenList.add(token_temp);
				this.choosed_categoryIdList.add(max_newpro_category_id);
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		if(stmtData != null)
		{
			try{
				stmtData.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		if(connData != null )
		{
			try{
				connData.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		this.isFinished = true;
		return;
	}
	public void chooseDataFromDBbyThr(){
		double threshold_setting_choosed = this.thresholdSetting;
		String token_temp = "";
		int max_newpro_category_id = 0;
		String selectDataInfo = "select token,max_newpro_category_id from tokenncdsecond where ncd < '"+threshold_setting_choosed+"';";
		Connection connData = getConn();
		Statement stmtData = null;
		
		try
		{
			stmtData = connData.createStatement();
			ResultSet rsDataInfo = stmtData.executeQuery(selectDataInfo);
			
			while(rsDataInfo.next())
			{
				token_temp = rsDataInfo.getString(1);
				max_newpro_category_id = rsDataInfo.getInt(2);
				this.choosed_tokenList.add(token_temp);
				this.choosed_categoryIdList.add(max_newpro_category_id);
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		if(stmtData != null)
		{
			try{
				stmtData.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		if(connData != null )
		{
			try{
				connData.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		this.isFinished = true;
		 return;
	}
	public void function(){
		if(this.getisChoosedbyThreshold()){
			//u choose setThresholdSetting
			this.chooseDataFromDBbyThr();
		}
		else{
			//u choose setTokenCategory
			this.chooseDataFromDBbyCat();
		}
		return;
	}
}
