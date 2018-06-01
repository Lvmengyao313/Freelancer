package pers.tong.MappingTools.definedDataStruct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataNCDDefined {
	private int category_id;
	private int document_length_sum;
	private List<DataDocDefined> document_dataList;
	private List<String> category_uniquetokenList;
	private Map<String,Integer> unique_countList;
	
	private boolean isInitialized;
	private boolean isPreProcessed;
	private boolean isFinished;
/*	
	public DataNCDDefined(){
		this.category_id = 0;
		this.document_length_sum = 0;
		this.document_dataList = new ArrayList<>();
		this.category_uniquetokenList = new ArrayList<>();
		this.unique_countList = new HashMap<String,Integer>();
		
		this.isInitialized = false;
		this.isPreProcessed = false;
		this.isFinished = false;
	}
*/	
	public DataNCDDefined(List<DataDocDefined> document_dataList){
		this.category_id = 0;
		this.document_length_sum = 0;
		this.document_dataList = document_dataList;
		this.category_uniquetokenList = new ArrayList<>();
		this.unique_countList = new HashMap<String,Integer>();
		
		this.isInitialized = true;
		this.isPreProcessed = false;
		this.isFinished = false;
	}
	public void setCategoryId(int category_id){
		this.category_id = category_id;
		return;
	}
	public void setDocumentDataList(List<DataDocDefined> document_dataList){
		this.document_dataList = document_dataList;
		this.isInitialized = true;
		return;
	}
	public boolean getisInitialized(){
		return this.isInitialized;
	}
	public boolean getisPreProcessed(){
		return this.isPreProcessed;
	}
	public boolean getisFinished(){
		return this.isFinished;
	}
	public int getCategoryId(){
		return this.category_id;
	}
	public Map<String,Integer> getUnique_countList(){
		return this.unique_countList;
	}
	public int getDocumentLengthSum(){
		int document_length_sum = -1;
		if(this.getisInitialized()){
			document_length_sum = this.document_length_sum;
		}
		else{
			//Do nothing,just give the tips
			System.out.println("There is something wrong during the DataNCDDefined part: the getDocumentLengthSum part :u may initialize the document_dataList first.");
		}
		return document_length_sum;
	}
	public List<String> getCategoryUniquetokenList(){
		List<String> category_uniquetokenList = new ArrayList<>();
		if(this.getisPreProcessed()){
			category_uniquetokenList = this.category_uniquetokenList;
		}
		else{
			//do nothing,just give the tips 
			category_uniquetokenList = null;
			System.out.println("There is something wrong during the DataNCDDefined part:the getCategoryUniquetokenList part:u may finish the PreProcesse of the funciton first.");
		}
		return category_uniquetokenList;
	}
	
	public boolean getisContained(String token){
		boolean isContained = false;
		if(this.category_uniquetokenList.contains(token)){
				isContained = true;
		}
		return isContained;
	}
	public int getUniqueCount(String token){
		int unique_count = -1;
		if(this.getisPreProcessed()){
			if(this.getisContained(token)){
				unique_count = this.unique_countList.get(token);
			}
			else{
				unique_count = 0;
			}
		}
		else{
			//do nothing ,just give the tips
			System.out.println("There is something wrong during the DataNCDDefined part: the getUniqueProbabilityList part :u may finish the PreProcesse of the funciton first.");
		}
		return unique_count;
	}
	
	public void function(){
		if(this.isInitialized){
			int index = 0;
			
			for(DataDocDefined document_data : this.document_dataList){
				index ++;
				if(index == 1){
					this.document_length_sum += document_data.getDocLength();
					this.category_uniquetokenList = document_data.getDocUniqueTokensList();
					this.unique_countList = document_data.getTokenValueList();
					if(this.unique_countList == null){
						System.out.println("this.unique_countList == null");
					}
				}
				else{
					this.document_length_sum += document_data.getDocLength();
					for(String token : document_data.getDocUniqueTokensList()){
						int token_value = 0;
						if(!this.getisContained(token)){
							token_value = 1;
							this.category_uniquetokenList.add(token);
							this.unique_countList.put(token, token_value);
						}
						else{
							token_value = this.unique_countList.get(token);
							token_value ++;
							this.unique_countList.put(token, token_value);
						}
					}
				}
			}
			this.isPreProcessed = true;
			this.isFinished = true;
		}
		else{
			//Do nothing,just give the tips
			System.out.println("There is something wrong during the DataNCDDefined part: the function part:u may initialize the document_dataList first.");
		}
		return;
	}
}
