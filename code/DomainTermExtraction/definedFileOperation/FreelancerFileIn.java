package pers.tong.MappingTools.definedFileOperation;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;

import pers.tong.MappingTools.definedDataStruct.DataDocDefined;

public class FreelancerFileIn {
	private FileInputStream fis;
	private InputStreamReader isr;
	private BufferedReader br;
	private String inputFilepath;
	private List<DataDocDefined> category_documentList;
	
	private boolean isFinished;
	private boolean isInitialized;
	
	public FreelancerFileIn(){
		this.fis = null;
		this.isr = null;
		this.br = null;
		this.inputFilepath = "";
		this.category_documentList = new ArrayList<>();
		
		this.isFinished = false;
		this.isInitialized = false;
	}
/*	
	public FreelancerFileIn(String inputFilepath){
		this.fis = null;
		this.isr = null;
		this.br = null;
		this.inputFilepath = inputFilepath;
		this.category_documentList = new ArrayList<>();
		
		this.isFinished = false;
		this.isInitialized=  true;
	}
*/	
	public boolean getisFinished(){
		return this.isFinished;
	}
	public boolean getisInitialized(){
		return this.isInitialized;
	}
	public void setInputFilePath(String inputFilepath){
		this.inputFilepath = inputFilepath;
		this.isInitialized = true;
		return;
	}
	public List<DataDocDefined> getCategoryDocumentList(){
		List<DataDocDefined> category_documentList = new ArrayList<>();
		if(this.getisFinished()){
			category_documentList = this.category_documentList;
		}
		else{
			category_documentList = null;
			System.out.println("There is something wrong during the FreelancerFileIn part : the getCategoryDocumentList part : u should finish the function part first.");
		}
		return category_documentList;
	}
	
	public void function(){
		if(this.getisInitialized()){
			try {
				this.fis = new FileInputStream(this.inputFilepath);// FileInputStream   
				this.isr = new InputStreamReader(this.fis,"utf-8");// InputStreamReader 是字节流通向字符流的桥梁,
			    this.br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
			    String doc_data = null;
			    int index = 0;
			    while((doc_data = this.br.readLine()) != null ){
			    	index ++;
			    	if(index == 1){
			    		System.out.println(doc_data);
			    	}
			    	String[] doc_tokens = doc_data.split("\\s+");
			    	List<String> doc_tokensList = new ArrayList<>();
			    	for(String doc_token : doc_tokens){
			    		doc_tokensList.add(doc_token);
			    	}
			    	DataDocDefined category_document = new DataDocDefined(doc_tokensList);
			    	category_document.function();
			    	this.category_documentList.add(category_document);
			    	}
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
			System.out.println("There is something wrong during the FreelancerFileIn part : the function part : u should finish the initialization first.");
		}
		return;
	}
}
