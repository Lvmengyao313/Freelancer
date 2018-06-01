package pers.tong.MappingTools.definedFileOperation;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;

public class FreelancerFileNameGet {
	private FileInputStream fis;
	private InputStreamReader isr;
	private BufferedReader br;
	private String inputFilepath;
	private String file_name_head;
	private List<String> file_nameList;
	
	private boolean isFinished;
	private boolean isInitialized;
	
	public FreelancerFileNameGet(){
		this.fis = null;
		this.isr = null;
		this.br = null;
		this.inputFilepath = "";
		this.file_name_head = "./result/category/";
		this.file_nameList = new ArrayList<>();
		
		this.isFinished = false;
		this.isInitialized = false;
	}
	
	public FreelancerFileNameGet(String inputFilepath){
		this.fis = null;
		this.isr = null;
		this.br = null;
		this.inputFilepath = inputFilepath;
		this.file_name_head = "./result/category/";
		this.file_nameList = new ArrayList<>();
		
		this.isFinished = false;
		this.isInitialized = true;
	}

	public void setFileNameHead(String file_name_head){
		this.file_name_head = file_name_head;
		return;
	}
	public void setInputFilePath(String inputFilepath){
		this.inputFilepath = inputFilepath;
		return;
	}
	public boolean getisFinished(){
		return this.isFinished;
	}
	public boolean getisInitialized(){
		return this.isInitialized;
	}
	public String createFileName(String file_name_tail){
		String file_name = "";
		file_name += this.file_name_head;
		file_name += file_name_tail;
		return file_name;
	}
	public List<String> getFileNameList(){
		List<String> file_nameList = new ArrayList<>();
		if(this.getisFinished()){
			file_nameList = this.file_nameList;
		}
		else{
			file_nameList = null;
			System.out.println("There is something wrong during the FreelancerFileNameGet part : the getFileNameList part : u should finish the funciton part first.");
		}
		return file_nameList;
	}
	public void function(){
		if(this.getisInitialized()){
			try {
				this.fis = new FileInputStream(this.inputFilepath);// FileInputStream   
				this.isr = new InputStreamReader(this.fis,"utf-8");// InputStreamReader 是字节流通向字符流的桥梁,
			    this.br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
			    String file_name_tail = null;
			    while(( file_name_tail = this.br.readLine()) != null ){
			    	String file_name = this.createFileName(file_name_tail);
			    	this.file_nameList.add(file_name);
			    }
			    this.isFinished = true;
			    } 
			catch (FileNotFoundException e)
			      {
			       System.out.println("Error: The specified file to load was not found!");
			       this.isFinished = false;
			      } 
			catch (IOException e)
			      {
			       System.out.println("Error: Failed to read the specified file!");
			       this.isFinished = false;
			      }
			finally {
			       try {
			         this.br.close();
			         this.isr.close();
			         this.fis.close();
			         this.isFinished = true;
			       } 
			       catch (IOException e)
			       {
			        e.printStackTrace();
			        this.isFinished = false;
			       }
			      }
		}
		else{
			//do nothing ,just give the tips
			this.isFinished = false;
			System.out.println("There is something wrong during the FreelancerFileNameGet part : the function part : u should finish the initialization first.");
		}
		return ;
	}
	
}
