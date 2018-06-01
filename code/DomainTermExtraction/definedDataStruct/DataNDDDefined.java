package pers.tong.MappingTools.definedDataStruct;

import java.util.ArrayList;
import java.util.List;

/**
 * class DataNDDDefined: include the definition of the function to load data from text and the function to write data into texts
 * @author 2tong
 * date:2017
 */

public class DataNDDDefined {
	private int category_id;
	private int document_sum;
	private List<Integer> document_lengthList;
	private List<String> category_uniquetokenList;
	private List<Integer> category_uniquetoken_sumList;
	private List<DataDocDefined> document_dataList;
	
	private boolean isInitialized;
	private boolean isFinished;
	
	public DataNDDDefined(){
		this.category_id = 0;
		this.document_sum = 0;
		this.document_lengthList = new ArrayList<>();
		this.category_uniquetokenList = new ArrayList<>();
		this.document_dataList = new ArrayList<>();
		this.category_uniquetoken_sumList = new ArrayList<>();
		
		this.isInitialized = false;
		this.isFinished = false;
	}
	
	public DataNDDDefined(List<DataDocDefined> document_dataList){
		this.category_id = 0;
		this.document_sum = document_dataList.size();
		this.document_lengthList = new ArrayList<>();
		this.category_uniquetokenList = new ArrayList<>();
		this.document_dataList = document_dataList;
		this.category_uniquetoken_sumList = new ArrayList<>();
		
		this.isInitialized = true;
		this.isFinished = false;
	}
	public void setCategoryId(int category_id){
		this.category_id = category_id;
		return;
	}
	public void setDocumentSum(int document_sum){
		this.document_sum = document_sum;
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
	public boolean getisFinished(){
		return this.isFinished;
	}
	public int getCategoryId(){
		return this.category_id;
	}
	public List<DataDocDefined> getdocumentDataList(){
		if(this.getisInitialized()){
			return this.document_dataList;
		}
		else{
			return null;
		}
	}
	public List<Integer> getDocumentLengthList(){
		List<Integer> document_lengthList = new ArrayList<>();
		if(this.getisFinished()){
			document_lengthList = this.document_lengthList;
		}
		else{
			//do nothing , just give the tips
			document_lengthList = null;
			System.out.println("There is something wrong during the process of the DataNDDDefined part : the getDocumentLengthList part : u may finish the function first.");
		}
		return document_lengthList;
	}
	public List<String> getCategoryUniquetokenList(){
		List<String> category_uniquetokenList = new ArrayList<>();
		if(this.getisFinished()){
			category_uniquetokenList = this.category_uniquetokenList;
		}
		else{
			//do nothing ,just give the tips
			category_uniquetokenList = null;
			System.out.println("There is something wrong during the process of the DataNDDDefined part : the getCategoryUniquetokenList part : u may finish the function first.");
		}
		return category_uniquetokenList;
	}
	public List<Integer> getcategoryUniqueTokenSumList(){
		List<Integer> category_uniquetoken_sumList = new ArrayList<>();
		if(this.getisFinished()){
			category_uniquetoken_sumList = this.category_uniquetoken_sumList;
		}
		else{
			category_uniquetoken_sumList = null;
			System.out.println("There is something wrong during the process of the DataNDDDefined part : the getCategoryUniquetokenList part : u may finish the function first.");
		}
		return category_uniquetoken_sumList;
	}
	public int getDocumentSum(){
		int document_sum = -1;
		if(this.getisInitialized()){
			document_sum = this.document_sum;
		}
		else{
			//do nothing ,just give the tips
			System.out.println("There is something wrong during the process of the DataNDDDefined part : the function part : u may finish the initialization first.");
		}
		return document_sum;
	}
	public void function(){
		if(this.getisInitialized()){
			int index = 0;
			for(DataDocDefined doc_data : this.document_dataList){
				index ++;
				this.document_lengthList.add(doc_data.getDocLength());
				if(index == 1){
					this.category_uniquetokenList = doc_data.getDocUniqueTokensList();
				}
				else{
					for(String token : doc_data.getDocUniqueTokensList()){
						if(this.category_uniquetokenList.contains(token)){
							//do nothing
						}
						else{
							this.category_uniquetokenList.add(token);
						}
					}
				}
			}
			for(String token : this.category_uniquetokenList){
				int category_token_sum = 0;
				for(DataDocDefined doc_data : this.document_dataList){
					if(doc_data.getTokenValueList().get(token) != null){
						category_token_sum += doc_data.getTokenValueList().get(token);
					}
					else{
						//do nothing ,just continue
					}
				}
				this.category_uniquetoken_sumList.add(category_token_sum); 
			}
			this.isFinished = true;
		}
		else{
			//do nothing,just give the tips
			this.isFinished = false;
			System.out.println("There is something wrong during the process of the DataNDDDefined part : the function part : u may finish the initialization first.");
		}
		return;
	}
	
}
