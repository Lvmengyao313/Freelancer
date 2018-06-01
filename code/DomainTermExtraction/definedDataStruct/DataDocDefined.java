package pers.tong.MappingTools.definedDataStruct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataDocDefined {
	private int doc_length;
	private List<String> doc_tokensList;
	private List<String> doc_uniquetokensList;
	private Map<String,Integer> token_valueMap;
	
	private boolean isInitialized;
	private boolean isPreProcessed;
	
	public DataDocDefined(){
		this.doc_length = 0;
		this.doc_tokensList = new ArrayList<>();
		this.doc_uniquetokensList = new ArrayList<>();
		this.token_valueMap = new HashMap<String,Integer>();
		
		this.isInitialized = false;
		this.isPreProcessed = false;
	}
	
	public DataDocDefined(List<String> doc_tokensList){
		this.doc_length = doc_tokensList.size();
		this.doc_tokensList = doc_tokensList;
		this.isInitialized = true;
		this.doc_uniquetokensList = new ArrayList<>();
		this.token_valueMap = new HashMap<String,Integer>();
		
		this.isPreProcessed = false;
	} 
	public void setDocTokensList(List<String> doc_tokensList){
		this.doc_tokensList = doc_tokensList;
		this.doc_length = doc_tokensList.size();
		this.isInitialized = true;
		return;
	}
	
	public boolean getisInitialized(){
		return this.isInitialized;
	}
	public boolean getisPreProcessed(){
		return this.isPreProcessed;
	}
	public int getDocLength(){
		int doc_length = -1;
		if(this.getisInitialized()){
			doc_length = this.doc_length;
		}
		else{
			//do nothing ,just give the tips
			doc_length = 0;
			System.out.println("There is something wrong during the DataDocDefine part: u may check if u finish the initialization.");
		}
		return doc_length;
	}
	public List<String> getdocTokensList(){
		return this.doc_tokensList;
	}
	public List<String> getDocUniqueTokensList(){
		List<String> doc_uniquetokensList = new ArrayList<>();
		if(this.getisPreProcessed()){
			doc_uniquetokensList = this.doc_uniquetokensList;
		}
		else{
			doc_uniquetokensList = null;
			System.out.println("There is something wrong during the DataDocDefine part: u may check if u finish the function().");
		}
		return doc_uniquetokensList;
	}
	public Map<String,Integer> getTokenValueList(){
		Map<String,Integer> token_valueList = new HashMap<String,Integer>();
		if(this.getisPreProcessed()){
			token_valueList = this.token_valueMap;
		}
		else{
			token_valueList = null;
			System.out.println("There is something wrong during the DataDocDefine part: u may check if u finish the function().");
		}
		return token_valueList;
	}
	public boolean getisContained(String token){
		boolean isContained = false;
		if(this.token_valueMap.containsKey(token)){
			isContained = true;
		}
		else{
			//do nothing
		}
		return isContained;
	}
	
	public int gettokenTimes(String token){
		int token_times = 0;
		if(this.getisContained(token)){
			token_times = this.token_valueMap.get(token);
		}
		else{
			//do nothing
		}
		return token_times;
	}
	
	public void function(){
		if(this.getisInitialized()){
			//do nothing
			for(String token : this.doc_tokensList){
				int token_value = 0;
				if(this.token_valueMap.containsKey(token)){
					token_value = this.token_valueMap.get(token);
					token_value ++;
					this.token_valueMap.put(token, token_value);
				}
				else{
					token_value = 1;
					this.token_valueMap.put(token, token_value);
					this.doc_uniquetokensList.add(token);
				}
			}
			this.isPreProcessed = true;
		}
		else{
			System.out.println("There is something wrong during the DataDocDefine part: u may check if u finish the initialization.");
			this.isPreProcessed = false;
		}
		return;
	}
}

