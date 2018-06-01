package pers.tong.MappingTools.domainTermsExtractOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pers.tong.MappingTools.definedDataStruct.DataDocDefined;
import pers.tong.MappingTools.definedDataStruct.DataNDDDefined;

/**
 * class CalculateNDD: include the definition of the function to load data from text and the function to write data into texts
 * @author 2tong
 * date:2017.
 */
// u choose the indexofToken,the category_id,then return the token_ndd,so just one double number
public class CalculateNDD {
	private int category_id;
	private int category_sum;
	private int indexofToken;
	private double token_ndd;
	private List<String> unique_tokensList;
	private List<Double> token_document_probabilityList;
	private List<Double> token_document_newproList;
	private double token_document_sumprobability;
	private List<DataNDDDefined> category_dataList;
	
	private boolean isFinished;
	private boolean isInitialized;
	private boolean isSetted;
	private boolean isCreatedTokenList;
	private boolean isCreatedTokenDocumentProbabilityList;
	private boolean isCreatedTokenDocumentNewProList;
	
	public CalculateNDD(){
		this.category_id = 0;
		this.category_sum = 0;
		this.indexofToken = 0;
		this.unique_tokensList = new ArrayList<>();
		this.token_ndd = 0;
		this.token_document_probabilityList = new ArrayList<>();
		this.token_document_sumprobability= 0;
		this.token_document_newproList = new ArrayList<>();
		this.category_dataList = new ArrayList<>();
		
		this.isCreatedTokenDocumentNewProList = false;
		this.isCreatedTokenDocumentProbabilityList = false;
		this.isCreatedTokenList = false;
		this.isFinished = false;
		this.isSetted = false;
		this.isInitialized = false;
	}
	
	public CalculateNDD(List<DataNDDDefined> category_dataList){
		this.category_id = 0;
		this.category_sum = category_dataList.size();
		this.indexofToken = 0;
		this.unique_tokensList = new ArrayList<>();
		this.token_ndd = 0;
		this.token_document_probabilityList = new ArrayList<>();
		this.token_document_sumprobability = 0;
		this.token_document_newproList = new ArrayList<>();
		this.category_dataList = category_dataList;
		
		this.isCreatedTokenDocumentNewProList = false;
		this.isCreatedTokenDocumentProbabilityList = false;
		this.isCreatedTokenList = false;
		this.isFinished = false;
		this.isSetted = false;
		this.isInitialized = true;
	}
	public void resetVarible(){
		this.category_id = 0;
		this.indexofToken = 0;
		this.token_ndd = 0;
		this.token_document_newproList = new ArrayList<>();
		this.token_document_probabilityList = new ArrayList<>();
		this.token_document_sumprobability = 0;
		
		this.isSetted = false;
		this.isCreatedTokenDocumentProbabilityList = false;
		this.isCreatedTokenDocumentNewProList = false;
		this.isFinished = false;
		return ;
	}
	public boolean getisInitialized(){
		return this.isInitialized;
	}
	public boolean getisFinished(){
		return this.isFinished;
	}
	public boolean getisCreatedTokenDocumentNewProList(){
		return this.isCreatedTokenDocumentNewProList;
	}
	public boolean getisCreatedTokenDocumentProbabilityList(){
		return this.isCreatedTokenDocumentProbabilityList;
	}
	public boolean getisCreatedTokenList(){
		return this.isCreatedTokenList;
	}
	public boolean getisSetted(){
		return this.isSetted;
	}
	public void setInitialization(List<DataNDDDefined> category_dataList){
		this.category_sum = category_dataList.size();
		this.category_dataList = category_dataList;
		this.isInitialized = true;
		return;
	}
	public void setChoosedIndex(int category_id,int indexofToken){
		if(this.getisCreatedTokenList()){
			if((category_id < this.category_sum) && (category_id > -1)){
					this.category_id = category_id;
					this.indexofToken = indexofToken;
					this.isSetted = true;
			}
			else{
				//do nothing,just give the tips
				this.isSetted = false;
				System.out.println("There is something wrong during the CalculateNDD part : the setChoosedIndex part : u should input the valid category_id.");
			}
		}
		else{
			//do nothing ,just give the tips
			this.isSetted = false;
			System.out.println("There is something wrong during the CalculateNDD part : the setChoosedIndex part : u should finish the createTokenList part first.");
		}
		return;
	}
	public double getTokenNDD(){
		double choosed_ndd = 1;
		if(this.getisFinished()){
			choosed_ndd = this.token_ndd;
		}
		else{
			//do nothing,just give the tips
			System.out.println("There is something wrong during the CalculateNDD part : the  getChoosedNDD part : u should finish the function part first.");
		}
		this.isFinished = false;
		return choosed_ndd;
	}
	public List<String> getuniqueTokensList(){
		List<String> unique_tokensList = new ArrayList<>();
		if(this.getisCreatedTokenList()){
			unique_tokensList = this.unique_tokensList;
		}
		else{
			unique_tokensList = null;
			System.out.println("There is something wrong during the CalculateNDD part : the getuniqueTokensList part : u should finish the createTokenList part first");
		}
		return  unique_tokensList;
	}
	public List<Double> gettokenDocumentProbabilityList(){
		List<Double> token_document_probabilityList = new ArrayList<>();
		if(this.isCreatedTokenDocumentProbabilityList){
			token_document_probabilityList = this.token_document_probabilityList;
		}
		else{
			token_document_probabilityList = null;
			System.out.println("There is something wrong during the CalculateNDD part : the gettokenDocumentProbabilityList part : u should finish the createTokenDocumentProbabilityList part first.");
		}
		return token_document_probabilityList;
	}
	public double getTokenDocumentSumprobability(){
		double token_document_sumprobability = 1;
		if(this.isCreatedTokenDocumentProbabilityList){
			token_document_sumprobability = this.token_document_sumprobability;
		}
		else{
			System.out.println("There is something wrong during the CalculateNDD part : the getTokenDocumentSumprobability part : u should finish the createTokenDocumentProbabilityList part first.");
		}
		return token_document_sumprobability;
	}
	public List<Double> gettokenDocumentNewproList(){
		List<Double> token_document_newproList = new ArrayList<>();
		if(this.getisCreatedTokenDocumentNewProList()){
			token_document_newproList = this.token_document_newproList;
		}
		else{
			token_document_newproList = null;
			System.out.println("There is something wrong during the CalculateNDD part : the gettokenDocumentNewproList part : u should finish the createTokenDocumentNewproList part first.");
		}
		return token_document_newproList;
	}
	public void createUniqueTokensList(){
		if(this.getisInitialized()){
			int index = 0;
			for(DataNDDDefined category_data : this.category_dataList){
				index ++;
				if(index  == 1){
					this.unique_tokensList = category_data.getCategoryUniquetokenList();
				}
				else{
					for(String token : category_data.getCategoryUniquetokenList()){
						if(this.unique_tokensList.contains(token)){
							//do nothing
						}
						else{
							this.unique_tokensList.add(token);
						}
					}
				}
			}
			this.isCreatedTokenList = true;
			System.out.println( "Finished the  CalculateNDD part : the createUniqueTokensList part,the unique_tokensList.size() :" +unique_tokensList.size());
		}
		else{
			//do nothing,just give the tips
			this.isCreatedTokenList = false;
			System.out.println("There is something wrong during the CalculateNDD part : the  createUniqueTokensList part : u should finish the valid initializaiton.");
		}
		return ;
	}
	public void createTokenDocumentProbabilityList(){
		if(this.getisSetted()){
			DataNDDDefined category_choosed_data = this.category_dataList.get(this.category_id);
			String token_choosed_data = this.unique_tokensList.get(this.indexofToken);
			int token_choosed_index = 0;
			double probability = 0;
			int document_sum = category_choosed_data.getDocumentSum();
			if(category_choosed_data.getCategoryUniquetokenList().contains(token_choosed_data)){
				token_choosed_index = category_choosed_data.getCategoryUniquetokenList().indexOf(token_choosed_data);
			}
			else{
				//do nothing
				System.out.println("There is something wrong during the CalculateNDD part : th createTokenDocumentProbabilityList part : the given token is not in the given category.");
				this.isCreatedTokenDocumentProbabilityList = false;
				return;
			}
			List<DataDocDefined> category_document_list = category_choosed_data.getdocumentDataList();
			for(int index = 0; index < document_sum; index++){
				List<Integer> category_token_sumlist = category_choosed_data.getcategoryUniqueTokenSumList();
				double category_token_sum = category_token_sumlist.get(token_choosed_index);
				double document_token_number = 0;
				Map<String,Integer> document_token_count = category_document_list.get(index).getTokenValueList();
				if(document_token_count.get(token_choosed_data) != null){
					document_token_number = document_token_count.get(token_choosed_data);
				}
				else{
					//do nothing
				}
				probability = document_token_number/category_token_sum;
				double document_length = category_document_list.get(index).getDocLength();
				probability = probability/document_length;
				this.token_document_probabilityList.add(probability);
				this.token_document_sumprobability += probability;
			}
			this.isCreatedTokenDocumentProbabilityList = true;
		}
		else{
			//do nothing ,just give the tips
			this.isCreatedTokenDocumentProbabilityList = false;
			System.out.println("There is something wrong during the CalculateNDD part : the createTokenDocumentProbabilityList part : u should finish the valid parameter setting.");
		}
		return;
	}
	public void createTokenDocumentNewproList(){
		if(this.getisCreatedTokenDocumentProbabilityList()){
			double new_probability = 0;
			for(double token_document_probability : this.gettokenDocumentProbabilityList()){
				double token_ducument_sumpro = this.getTokenDocumentSumprobability();
				new_probability = token_document_probability/token_ducument_sumpro;
				this.token_document_newproList.add(new_probability);
			}
			this.isCreatedTokenDocumentNewProList = true;
		}
		else{
			//do nothing , just give the tips
			this.isCreatedTokenDocumentNewProList = false;
			System.out.println("There is something wrong during the CalculateNDD part : the createTokenDocumentNewproList part : u should finish the createTokenDocumentProbabilityList part first.");
		}
		return;
	}
	public void createTokenNDD(){
		if(this.getisCreatedTokenDocumentNewProList()){
			for(double token_document_newpro : this.gettokenDocumentNewproList()){
				if(token_document_newpro == 0){
					//do nothing
				}
				else{
					double token_ndd_part = -(token_document_newpro * Math.log(token_document_newpro)/Math.log(2));
					System.out.println("token_document_newpro"+token_document_newpro);
					this.token_ndd += token_ndd_part;
				}
			}
			this.isFinished = true;
		}
		else{
			//do nothing ,just give the tips
			this.isFinished = false;
			System.out.println("There is something wrong during the CalculateNDD part : the createTokenNDD part : u should finish the createTokenDocumentNewproList part first.");
		}
		return;
	}
	public void function(int category_id,String token){
			if(this.getisCreatedTokenList()){
				int indexofToken = this.unique_tokensList.indexOf(token);
				System.out.println("indexofToken"+indexofToken);
				System.out.println("category_id"+category_id);
				this.setChoosedIndex(category_id, indexofToken);
				if(this.getisSetted()){
					this.createTokenDocumentProbabilityList();
					if(this.getisCreatedTokenDocumentProbabilityList()){
						this.createTokenDocumentNewproList();
						if(this.getisCreatedTokenDocumentNewProList()){
							this.createTokenNDD();
						}
						else{
							//do nothing
						}
					}
					else{
						//do nothing
					}
				}
				else{
					//do nothing
				}
			}
			else{
				//do nothing
			}
		return;
	}
}
