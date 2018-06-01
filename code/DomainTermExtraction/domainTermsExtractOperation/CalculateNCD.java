package pers.tong.MappingTools.domainTermsExtractOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pers.tong.MappingTools.definedDataStruct.DataNCDDefined;

public class CalculateNCD {
	private int category_sum;
	private List<DataNCDDefined>  category_dataList;
	private List<String> unique_tokensList;
	private List<Double> token_ncdList;
	private List<List<Double>> token_category_probabilityList;
	private List<Double> token_category_sumprobabilityList;
	private List<List<Double>> token_category_newproList;
	
	private boolean isInitialized;
	private boolean isCreatedTokenList;
	private boolean isCreatedTokenCategoryProbabilityList;
	private boolean isCreatedTokenCategoryNewProList;
	private boolean isFinished;
	
	public CalculateNCD(){
		this.category_sum = 0;
		this.category_dataList = new ArrayList<>();
		this.unique_tokensList = new ArrayList<>();
		this.token_ncdList = new ArrayList<>();
		this.token_category_probabilityList = new ArrayList<>();
		this.token_category_sumprobabilityList = new ArrayList<>();
		this.token_category_newproList= new ArrayList<>();
		
		this.isFinished = false;
		this.isCreatedTokenList = false;
		this.isCreatedTokenCategoryProbabilityList = false;
		this.isCreatedTokenCategoryNewProList = false;
		this.isInitialized = false;
	}
/*	
	public CalculateNCD(int category_sum,List<DataNCDDefined> category_dataList){
		this.category_sum = category_sum;
		this.unique_tokensList = new ArrayList<>();
		this.token_ncdList = new ArrayList<>();
		this.token_category_probabilityList = new ArrayList<>();
		this.token_category_sumprobabilityList= new ArrayList<>();
		this.token_category_newproList = new ArrayList<>();
		
		this.isFinished = false;
		this.isCreatedTokenList = false;
		this.isCreatedTokenCategoryProbabilityList = false;
		this.isCreatedTokenCategoryNewProList = false;
		if((this.category_sum != 0) && (this.category_sum == category_dataList.size())){
			this.category_dataList = category_dataList;
			this.isInitialized = true;
		}
		else{
			this.isInitialized = false;
			System.out.println("There is something wrong during the CalculateNCD part : u should finish the valid initializaiton.");
		}
		
	}
*/	
	public boolean getisFinished(){
		return this.isFinished;
	}
	public boolean getisInitialized(){
		return this.isInitialized;
	}
	public boolean getisCreatedTokenList(){
		return this.isCreatedTokenList;
	}
	public boolean getisCreatedTokenCategoryProbabilityList(){
		return this.isCreatedTokenCategoryProbabilityList;
	}
	public boolean getisCreatedTokenCategoryNewProList(){
		return this.isCreatedTokenCategoryNewProList;
	}
	
	public void setCategorySum(int category_sum){
		this.category_sum = category_sum;
		return;
	}
	public void setCategoryDataList(List<DataNCDDefined> category_dataList){
		if((this.category_sum != 0) && (this.category_sum == category_dataList.size())){
			this.category_dataList = category_dataList;
			this.isInitialized = true;
		}
		else{
			this.isInitialized = false;
			System.out.println("There is something wrong during the CalculateNCD part : the setCategoryDataList part :  u should finish the valid initializaiton.");
		}
		return;
	}
	
	public List<String> getUniqueTokensList(){
		List<String> unique_tokensList = new ArrayList<>();
		if(this.getisCreatedTokenList()){
			unique_tokensList = this.unique_tokensList;
		}
		else{
			unique_tokensList = null;
			System.out.println("There is something wrong during the process of the CalculateNCD part : u may should finish the createUniqueTokensList part please.");
		}
		return unique_tokensList;
	}
	public List<Double> getUniqueNcdList(){
		List<Double> unique_ncdList = new ArrayList<>();
		if(this.getisFinished()){
			unique_ncdList = this.token_ncdList;
		}
		else{
			unique_ncdList = null;
			System.out.println("There is something wrong during the process of the CalculateNCD part : u may should finish the function part please.");
		}
		return unique_ncdList;
	}
	public int getIndexofMaxNewPro(int indexofToken){
		List<Double> token_category_newpros = this.getTokenCategoryNewProList().get(indexofToken);
		int sumSize = token_category_newpros.size();
		int indexofCategory = 0;
		double temp = 0;
		
		for(int index = 0 ;index <sumSize;index ++){
			if(index == 0){
				temp = token_category_newpros.get(index);
			}
			else{
				if(temp > token_category_newpros.get(index)){
					//do nothing
				}
				else{
					temp = token_category_newpros.get(index);
					indexofCategory = index;
				}
			}
		}
		return indexofCategory;
	} 
	public List<List<Double>> getTokenCategoryProbabilityList(){
		List<List<Double>> token_category_probabilityList = new ArrayList<>();
		if(this.getisCreatedTokenCategoryProbabilityList()){
			token_category_probabilityList = this.token_category_probabilityList;
		}
		else{
			token_category_probabilityList = null;
			System.out.println("There is something wrong during the process of the CalculateNCD part : u may should finish the function part please.");
		}
		return token_category_probabilityList;
	}
	public double getTokenCategorySumprobabilityList(int index){
		double token_category_sumprobability = -1;
		if(this.getisCreatedTokenCategoryProbabilityList()){
			token_category_sumprobability = this.token_category_sumprobabilityList.get(index);
		}
		else{
			System.out.println("There is something wrong during the process of the CalculateNCD part : u may should finish the function part please.");
		}
		return token_category_sumprobability;
	}
	public List<List<Double>> getTokenCategoryNewProList(){
		List<List<Double>> token_category_ncdList = new ArrayList<>();
		if(this.getisCreatedTokenCategoryNewProList()){
			token_category_ncdList = this.token_category_newproList;
		}
		else{
			token_category_ncdList = null;
			System.out.println("There is something wrong during the process of the CalculateNCD part : u may should finish the function part please.");
		}
		return token_category_ncdList;
	}
	
	public void createUniqueTokensList(){
		if(this.getisInitialized()){
			int index = 0;
			for(DataNCDDefined category_data : this.category_dataList){
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
		}
		else{
			//do nothing,just give the tips
			this.isCreatedTokenList = false;
			System.out.println("There is something wrong during the CalculateNCD part : u should finish the valid initializaiton.");
		}
		return ;
	}
	public void createTokenCategoryProbabilityList(){
		if(this.getisCreatedTokenList()){
			
			for(String token : this.unique_tokensList){
				double  token_category_probobility_moledcule = 0;
				double  token_category_probobility_denominator = 0;
				List<Double> token_category_probobility_moleculeList = new ArrayList<>();
				double moledcule_moledcule = 0;
				double moledcule_denominator = 0;
				for(DataNCDDefined category_data : this.category_dataList){
					//System.out.println("this.category_dataList.size()£º" +this.category_dataList.size());
					if(category_data.getisContained(token)){
						Map<String,Integer> uniqueCountListTemp = category_data.getUnique_countList();
						if( uniqueCountListTemp.get(token) != null){
							moledcule_denominator += uniqueCountListTemp.get(token);
						}
						else{
							
						}
					}
					else{
						//do nothing
					}
				}
				for(DataNCDDefined category_data : this.category_dataList){
					if(category_data.getisContained(token)){
						Map<String,Integer> uniqueCountListTemp = category_data.getUnique_countList();
						if(uniqueCountListTemp.get(token) != null){
							moledcule_moledcule = uniqueCountListTemp.get(token);
						}
						else{
							
						}
					}
					else{
						moledcule_moledcule = 0;
					}
					double probability = moledcule_moledcule/moledcule_denominator;
					double categroy_token_length = category_data.getDocumentLengthSum();
					token_category_probobility_moledcule = probability/categroy_token_length;
					token_category_probobility_denominator += token_category_probobility_moledcule;
					token_category_probobility_moleculeList.add(token_category_probobility_moledcule);
				}
				this.token_category_probabilityList.add(token_category_probobility_moleculeList);
				this.token_category_sumprobabilityList.add(token_category_probobility_denominator);
			}
			this.isCreatedTokenCategoryProbabilityList = true;
		}
		else{
			//do nothing.just give the tips
			this.isCreatedTokenCategoryProbabilityList = false;
			System.out.println("There is something wrong during the CalculateNCD part : the createTokenCategoryProbabilityList part : u should finish the createUniqueTokensList part first.");
		}
		return ;
	}
	public void createTokenCategoryNewProList(){
		if(this.getisCreatedTokenCategoryProbabilityList()){
			int tag = 0;
			for(int index = 0; index < this.getTokenCategoryProbabilityList().size(); index ++){
				List<Double> token_category_probability =  this.getTokenCategoryProbabilityList().get(index);
				double token_category_probability_denominator = this.getTokenCategorySumprobabilityList(index);
				
				if(token_category_probability_denominator != -1){
					List<Double> token_category_probability_newList = new ArrayList<>();
					
					for(double token_category_probability__moledcule : token_category_probability){
						double token_category_probability_new = token_category_probability__moledcule/token_category_probability_denominator;
						token_category_probability_newList.add(token_category_probability_new);
					}
					
					this.token_category_newproList.add(token_category_probability_newList);
					tag ++;
				}
				else{
					break;
				}
			}
			if(tag == this.getTokenCategoryProbabilityList().size()){
				this.isCreatedTokenCategoryNewProList = true;
			}
			else{
				this.isCreatedTokenCategoryNewProList = false;
			}
		}
		else{
			this.isCreatedTokenCategoryNewProList = false;
			System.out.println("There is something wrong during the CalculateNCD part : the createTokenCategoryNcdList part : u should finish the createTokenCategoryProbabilityList part first.");
		}
		return;
	}
	public void createTokenNcdList(){
		if(this.getisCreatedTokenCategoryNewProList()){
			
			for(List<Double> token_category_newpros : this.getTokenCategoryNewProList()){
				double unique_token_ncd = 0;
				for(double token_category_newpro  : token_category_newpros ){
					if(token_category_newpro == 0){
						//do nothing
					}
					else{
						double token_category_ncd = -(token_category_newpro * Math.log(token_category_newpro)/Math.log(2));
						unique_token_ncd += token_category_ncd;
					}
				}
				this.token_ncdList.add(unique_token_ncd);
			}
			this.isFinished = true;
		}
		else{
			this.isFinished = false;
			System.out.println("There is something wrong during the CalculateNCD part : the createTokenNcdList part : u should finish the createTokenCategoryProbabilityList part first.");
		}
		return;
	}
	
	public void function(){
		if(this.getisInitialized()){
			//create the unique words list
			this.createUniqueTokensList();
			if(this.getisCreatedTokenList()){
				this.createTokenCategoryProbabilityList();
				if(this.getisCreatedTokenCategoryProbabilityList()){
					this.createTokenCategoryNewProList();
					if(this.getisCreatedTokenCategoryNewProList()){
						this.createTokenNcdList();
						if(this.getisFinished()){
							//do nothing
							System.out.println("Finished the CalculateNCD part.");
						}
						else{
							//do noting ,just give the tips
							System.out.println("There is something wrong during the the CalculateNCD part : the function part :the createTokenNcdList part");
						}
					}
					else{
						//do noting ,just give the tips
						System.out.println("There is something wrong during the the CalculateNCD part : the function part : the createTokenCategoryNewProList part.");
					}
				}
				else{
					//do noting ,just give the tips
					System.out.println("There is something wrong during the the CalculateNCD part : the function part : the createTokenCategoryProbabilityList part.");
				}
			}
			else{
				//do nothing,just give the tips
				System.out.println("There is something wrong during the CalculateNCD part : the function part : the createUniqueTokensList part.");
			}
		}
		else{
			this.isFinished = false;
			System.out.println("There is something wrong during the CalculateNCD part : u should finish the valid initializaiton.");
		}
		return ;
	}
}
