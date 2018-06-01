package pers.tong.MappingTools.freelancerOperation;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import pers.tong.MappingTools.definedDBOperation.FreelancerNCDDBIO;
import pers.tong.MappingTools.definedDataStruct.DataDocDefined;
import pers.tong.MappingTools.definedDataStruct.DataNCDDefined;
import pers.tong.MappingTools.definedFileOperation.FreelancerFileIn;
import pers.tong.MappingTools.definedFileOperation.FreelancerFileNameGet;
import pers.tong.MappingTools.domainTermsExtractOperation.CalculateNCD;

public class FreelancerAbtainTopicToken {
	private FreelancerFileNameGet freelancerfileNameGet;
	private String inputFilepath;
	private List<String> file_name_list;
	
	//private FreelancerFileIn freelancerfileIn;
	private List<List<DataDocDefined>> category_documentList;
	private List<DataNCDDefined> category_ncdList;
	private CalculateNCD calculateNCD;
	private List<Double> token_ncdList;
	private List<String> unique_tokensList;
	//private FreelancerNCDDBIO freelancerNCDDB;
	private double thresholdSetting;
	
	private boolean isInitialized;
	private boolean isAbtainedFileNameList;
	private boolean isAbtainedCategoryDocumentList;
	private boolean isCreatedCategoryNcdList;
	private boolean isCalculatedNCD;
	private boolean isInsertedNCD;
	private boolean isFinished;
	
	public FreelancerAbtainTopicToken(){
		this.freelancerfileNameGet = new FreelancerFileNameGet();
		this.file_name_list = new ArrayList<>();
		this.inputFilepath = "";
		
		//this.freelancerfileIn = new FreelancerFileIn();
		this.category_documentList = new ArrayList<>();
		this.category_ncdList = new ArrayList<>();
		this.token_ncdList = new ArrayList<>();
		this.unique_tokensList = new ArrayList<>();
		this.calculateNCD = new CalculateNCD();
		//this.freelancerNCDDB = new FreelancerNCDDBIO();
		this.thresholdSetting = 0;
		
		this.isInitialized = false;
		this.isAbtainedFileNameList = false;
		this.isAbtainedCategoryDocumentList = false;
		this.isCreatedCategoryNcdList = false;
		this.isCalculatedNCD = false;
		this.isInsertedNCD = false;
		this.isFinished = false;
		
	}
	public FreelancerAbtainTopicToken(String inputFilepath,double thresholdSetting){
		this.inputFilepath = inputFilepath;
		this.freelancerfileNameGet = new FreelancerFileNameGet(this.inputFilepath);
		this.file_name_list = new ArrayList<>();
		
		//this.freelancerfileIn = new FreelancerFileIn();
		this.category_documentList = new ArrayList<>();
		this.category_ncdList = new ArrayList<>();
		this.token_ncdList = new ArrayList<>();
		this.unique_tokensList = new ArrayList<>();
		this.calculateNCD = new CalculateNCD();
		//this.freelancerNCDDB = new FreelancerNCDDBIO();
		this.thresholdSetting = thresholdSetting;
		
		this.isInitialized = true;
		this.isAbtainedFileNameList = false;
		this.isAbtainedCategoryDocumentList = false;
		this.isCreatedCategoryNcdList = false;
		this.isCalculatedNCD = false;
		this.isInsertedNCD = false;
		this.isFinished = false;
	}
	
	public boolean getisInitialized(){
		return this.isInitialized;
	}
	public boolean getisAbtainedFileNameList(){
		return this.isAbtainedFileNameList;
	}
	public boolean getisAbtainedCategoryDocumentList(){
		return this.isAbtainedCategoryDocumentList;
	}
	public boolean getisCreatedCategoryNcdList(){
		return this.isCreatedCategoryNcdList;
	}
	public boolean getisCalculatedNCD(){
		return this.isCalculatedNCD;
	}
	public boolean getisInsertedNCD(){
		return this.isInsertedNCD;
	}
	public boolean getisFinished(){
		return this.isFinished;
	}
	public void setThresholdSetting(double thresholdSetting){
		this.thresholdSetting = thresholdSetting;
		return;
	}
	public List<String> getFileNameList(){
		List<String> file_name_list = new ArrayList<>();
		if(this.getisAbtainedFileNameList()){
			file_name_list = this.file_name_list;
		}
		else{
			file_name_list = null;
		}
		return file_name_list;
	}
	public void abtainFileNameList(){
		if(this.getisInitialized()){
			this.freelancerfileNameGet.function();
			this.file_name_list = this.freelancerfileNameGet.getFileNameList();
			this.isAbtainedFileNameList = true;
			System.out.println("Finished the abtainFileNameList part : the file_name_list.size() :"+this.file_name_list.size());
		}
		else{
			this.isAbtainedFileNameList = false;
			System.out.println("There is something wrong during the FreelancerAbtainTopicToken part : the getFileNameList part : u should finish the AbtainFileNameTail part first.");
		}
		return;
	}
	public List<List<DataDocDefined>> getCategoryDocumentList(){
		List<List<DataDocDefined>> category_documentList = new ArrayList<>();
		if(this.getisAbtainedCategoryDocumentList()){
			category_documentList = this.category_documentList;
		}
		else{
			category_documentList = null;
		}
		return category_documentList;
		
	}
	public void abtainCategoryDocumentList(){
		if(this.getisAbtainedFileNameList()){
			for(String file_name : this.getFileNameList()){
				System.out.println(file_name+" : will start to abtain the category_document of this file.");
				FreelancerFileIn freelancerfileIn = new FreelancerFileIn();
				freelancerfileIn.setInputFilePath(file_name);
				freelancerfileIn.function();
				List<DataDocDefined> category_documentList_temp = new ArrayList<>();
				category_documentList_temp = freelancerfileIn.getCategoryDocumentList();
				this.category_documentList.add(category_documentList_temp);
			}
			this.isAbtainedCategoryDocumentList = true;
			System.out.println("Finished the abtainCategoryDocumentList part : the category_documentList.size() :"+ this.category_documentList.size());
		}
		else{
			this.isAbtainedCategoryDocumentList = false;
			System.out.println("There is something wrong during the FreelancerAbtainTopicToken part : the abtainCategoryDocumentList part : u should finish the getFileNameList part first.");
		}
		return;
	}
	public List<DataNCDDefined> getcategoryNcdList(){
		List<DataNCDDefined> category_ncdList = new ArrayList<>();
		if(this.getisCreatedCategoryNcdList()){
			category_ncdList = this.category_ncdList;
		}
		else{
			category_ncdList = null;
			System.out.println("There is something wrong during the FreelancerAbtainTopicToken part : the getcategoryNcdList part : u should do finish the createCategoryNcdList part first.");
		}
		return category_ncdList;
	}
	public void createCategoryNcdList(){
		if(this.getisAbtainedCategoryDocumentList()){
			int tag = 0;
			System.out.println("this.getCategoryDocumentList().size()"+this.getCategoryDocumentList().size());
			for(int index = 0; index <this.getCategoryDocumentList().size(); index++){
				List<DataDocDefined> category_document_temp = this.getCategoryDocumentList().get(index);
				DataNCDDefined  category_ncd = new DataNCDDefined(category_document_temp);
				category_ncd.setCategoryId(index);
				category_ncd.function();
				if(category_ncd.getisFinished()){
					this.category_ncdList.add(category_ncd);
					System.out.println("category_ncd.getUnique_countList().size()"+category_ncd.getUnique_countList().size());
					tag ++;
				}
				else{
					break;
				}
			}
			if(tag == this.getCategoryDocumentList().size()){
				this.isCreatedCategoryNcdList = true;
				System.out.println("Finished the createCategoryNcdList part : the category_ncdList.size() : "+this.category_ncdList.size());
			}
			else{
				this.isCreatedCategoryNcdList = false;
			}
		}
		else{
			this.isCreatedCategoryNcdList = false;
			System.out.println("There is something wrong during the FreelancerAbtainTopicToken part : the createCategoryNcdList part : u should finish the abtainCategoryDocumentList part first.");
		}
		return;
	}
	public CalculateNCD getCalculateNCD(){
		CalculateNCD calculateNCD = new CalculateNCD();
		if(this.getisCalculatedNCD()){
			calculateNCD = this.calculateNCD;
		}
		else{
			calculateNCD = null;
			System.out.println("There is something wrong during the FreelancerAbtainTopicToken part : the getCalculateNCD part : u should finish the carrycalculateNCD part first.");
		}
		return calculateNCD;
	}
	public void carrycalculateNCD(){
		if(this.getisCreatedCategoryNcdList()){
			List<DataNCDDefined> categoryNcdList = this.getcategoryNcdList();
			int category_sum = categoryNcdList.size();
			System.out.println("category_sum :"+category_sum);
			CalculateNCD calculateNCD = new CalculateNCD();
			calculateNCD.setCategorySum(category_sum);
			calculateNCD.setCategoryDataList(categoryNcdList);
			calculateNCD.function();
			if(calculateNCD.getisFinished()){
				this.isCalculatedNCD = true;
				this.unique_tokensList = calculateNCD.getUniqueTokensList();
				this.token_ncdList = calculateNCD.getUniqueNcdList();
				this.calculateNCD = calculateNCD;
				System.out.println("Finished the carrycalculateNCD part.");
			}
			else{
				this.isCalculatedNCD = false;
			}
		}
		else{
			this.isCalculatedNCD = false;
			System.out.println("There is something wrong during the FreelancerAbtainTopicToken part : the carrycalculateNCD part : u should finish the createCategoryNcdList part first.");
		}
		return;
	}
	public List<String> getuniqueTokensList(){
		List<String> unique_tokensList = new ArrayList<>();
		if(this.getisCalculatedNCD()){
			unique_tokensList = this.unique_tokensList;
		}
		else{
			unique_tokensList = null;
			System.out.println("There is something wrong during the FreelancerAbtainTopicToken part : the getCalculateNCD part : u should finish the carrycalculateNCD part first.");
		}
		return unique_tokensList;
	}
	public List<Double> gettokenNcdList(){
		List<Double> token_ncdList = new ArrayList<>();
		if(this.getisCalculatedNCD()){
			token_ncdList = this.token_ncdList;
		}
		else{
			token_ncdList = null;
			System.out.println("There is something wrong during the FreelancerAbtainTopicToken part : the getCalculateNCD part : u should finish the carrycalculateNCD part first.");
		}
		return token_ncdList;
	}
	public void insertedNCD() throws Exception{
		if(this.getisCalculatedNCD()){
			List<String> unique_tokensList = this.getuniqueTokensList();
			List<Double> token_ncdList = this.gettokenNcdList();
			int id = 0;
			String token = "";
			double ncd = 0;
			int token_category = 0;
			int max_newpro_category_id = 0;
			
			int sumSize = unique_tokensList.size();
			File storencdFile = new File("./result/ncd.txt");
			try {
				storencdFile.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try(PrintWriter storeNcd = new PrintWriter(storencdFile)){
				for(int index = 0; index < sumSize; index++){
					id = index +1;
					token = unique_tokensList.get(index);
					ncd = token_ncdList.get(index);
					if(ncd <this.thresholdSetting){
						token_category = 2;
					}
					else{
						token_category = 1;
					}
					max_newpro_category_id = this.getCalculateNCD().getIndexofMaxNewPro(index);
					storeNcd.println(id+" "+token+" "+ncd+" "+token_category +" "+max_newpro_category_id);
				}
			}
			catch(IOException e){
				e.printStackTrace();
			}
			
			try(FreelancerNCDDBIO freelancerNCDDB = new FreelancerNCDDBIO();){
			for(int index = 0; index < sumSize; index++){
				id = index +1;
				token = unique_tokensList.get(index);
				ncd = token_ncdList.get(index);
				if(ncd <this.thresholdSetting){
					token_category = 2;
				}
				else{
					token_category = 1;
				}
				max_newpro_category_id = this.getCalculateNCD().getIndexofMaxNewPro(index);
				
				freelancerNCDDB.setId(id);
				freelancerNCDDB.setToken(token);
				freelancerNCDDB.setNcd(ncd);
				freelancerNCDDB.setTokenCategory(token_category);
				freelancerNCDDB.setMaxNewproCategoryId(max_newpro_category_id);
				freelancerNCDDB.insertDataToDB();
//				if(freelancerNCDDB.getisFinished()){
//					//do nothing , just continue
//				}
//				else{
//					break;
//				}
				if(index%1000==999)
					freelancerNCDDB.commit();
			}
			freelancerNCDDB.commit();
			}
			//freelancerNCDDB.close();
			if(id == sumSize){
				this.isInsertedNCD = true;
				System.out.println("Finished the insertedNCD() part.");
			}
			else{
				this.isInsertedNCD = false;
				System.out.println("There is something wrong during the FreelancerAbtainTopicToken part : the insertedNCD part : u should check the insertDataTODB part.");
			}
		}
		else{
			this.isInsertedNCD = false;
			System.out.println("There is something wrong during the FreelancerAbtainTopicToken part : the insertedNCD part : u should finish the getCalculateNCD part first.");
		}
		return;
	}
	public void function() throws Exception{
		if(this.getisInitialized()){
			this.abtainFileNameList();
			for(String file_namme :this.getFileNameList()){
				System.out.println(file_namme);
			}
			if(this.getisAbtainedFileNameList()){
				this.abtainCategoryDocumentList();
				int index = 0;
				for(List<DataDocDefined> category_docu :this.category_documentList){
					index ++;
					System.out.println("第"+index+"个 category_document.get(0).getDocLength:"+category_docu.get(0).getDocLength());
					for(String token : category_docu.get(0).getdocTokensList()){
						System.out.print(token+ " ");
					}
					System.out.println(" ");
				}
				if(this.getisAbtainedCategoryDocumentList()){
					this.createCategoryNcdList();
					int indexofncd = 0;
					for(DataNCDDefined category_ncd :this.getcategoryNcdList()){
						indexofncd ++;
						System.out.println("第"+indexofncd+"个 category_ncd 的CategoryId :"+category_ncd.getCategoryId()+"DocumentLengthSum() :"+category_ncd.getDocumentLengthSum());
					}
				if(this.getisCreatedCategoryNcdList()){
						this.carrycalculateNCD();
						
						if(this.getisCalculatedNCD()){
							this.insertedNCD();
							if(this.getisInsertedNCD()){
								this.isFinished = true;
							}
							else{
								//do nothing ,just give the tips
								System.out.println("There is something wrong during the FreelancerAbtainTopicToken part : the function part : u should finish the valid insertedNCD part first.");
							}
						}
						else{
							//do nothing ,just give the tips
							System.out.println("There is something wrong during the FreelancerAbtainTopicToken part : the function part : u should finish the valid carrycalculateNCD part first.");
						}
					}
					else{
						//do nothing ,just give the tips
						System.out.println("There is something wrong during the FreelancerAbtainTopicToken part : the function part : u should finish the valid createCategoryNcdList part first.");
					}
				}
				else{
					//do nothing ,just give the tips
					System.out.println("There is something wrong during the FreelancerAbtainTopicToken part : the function part : u should finish the valid abtainCategoryDocumentList part first.");
				}
			}
			else{
				//do nothing ,just give the tips
				System.out.println("There is something wrong during the FreelancerAbtainTopicToken part : the function part : u should finish the valid abtainFileNameList part first.");
			}
	}
		else{
			//do nothing ,just give the tips
			System.out.println("There is something wrong during the FreelancerAbtainTopicToken part : the funciton part : u should finish the initialization first.");
		}
		return;
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String inputFilepath = "./result/filenamelist.txt";
		double thresholdSetting = 1.0;
		FreelancerAbtainTopicToken freelancerAbtainTopicTokenTemp = new FreelancerAbtainTopicToken(inputFilepath,thresholdSetting);
		freelancerAbtainTopicTokenTemp.function();
		if(freelancerAbtainTopicTokenTemp.getisFinished()){
			System.out.println("Finished the FreelancerAbtainTopicToken part.");
		}
		else{
			System.out.println("There is something wrong during the FreelancerAbtainTopicToken part,please check the part.");
		}
		return;
	}
	
}
