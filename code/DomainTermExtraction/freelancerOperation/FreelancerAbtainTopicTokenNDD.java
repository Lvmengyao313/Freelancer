package pers.tong.MappingTools.freelancerOperation;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import pers.tong.MappingTools.definedDBOperation.FreelancerNCDDBRead;
import pers.tong.MappingTools.definedDBOperation.FreelancerNDDDBInsert;
import pers.tong.MappingTools.definedDataStruct.DataDocDefined;
import pers.tong.MappingTools.definedDataStruct.DataNDDDefined;
import pers.tong.MappingTools.definedFileOperation.FreelancerFileIn;
import pers.tong.MappingTools.definedFileOperation.FreelancerFileNameGet;
import pers.tong.MappingTools.domainTermsExtractOperation.CalculateNDD;

/**
 * class FreelancerAbtainTopicTokenNDD: include the definition of the function to load data from text and the function to write data into texts
 * @author 2tong
 * date:2017.
 */
public class FreelancerAbtainTopicTokenNDD {
	private FreelancerFileNameGet freelancerfileNameGet;
	private String inputFilepath;
	private List<String> file_name_list;
	
	//private FreelancerFileIn freelancerfileIn;
	private List<List<DataDocDefined>> category_documentList;
	private List<DataNDDDefined> category_nddList;
	private CalculateNDD calculateNDD;
	private List<Integer> choosed_category_idList;
	private List<String> choosed_tokenList;
	private List<Double> choosed_token_nddList;
	private int category_id_choosed;

	private double thresholdSetting;
	private boolean isInitialized;
	private boolean isAbtainedFileNameList;
	private boolean isAbtainedCategoryDocumentList;
	private boolean isCreatedCategoryNddList;
	private boolean isAbtainedchoosedParameter;
	private boolean isCalculatedNDD;
	private boolean isInsertedNDD;
	private boolean isFinished;

	//判断一下是否有确认那个词是否在列表中
	
	public FreelancerAbtainTopicTokenNDD(){
		this.freelancerfileNameGet = new FreelancerFileNameGet();
		this.inputFilepath = "";
		this.file_name_list = new ArrayList<>();
		
		//this.freelancerfileIn = new  FreelancerFileIn();
		this.category_documentList = new ArrayList<>();
		this.category_nddList = new ArrayList<>();
		this.calculateNDD = new CalculateNDD();
		this.choosed_category_idList = new ArrayList<>();
		this.choosed_tokenList = new ArrayList<>();
		this.thresholdSetting = 0;
		this.category_id_choosed = 2;
		this.choosed_token_nddList = new ArrayList<>();
		
		this.isInitialized = false;
		this.isAbtainedFileNameList = false;
		this.isAbtainedCategoryDocumentList = false;
		this.isCreatedCategoryNddList = false;
		this.isAbtainedchoosedParameter = false;
		this.isCalculatedNDD = false;
		this.isInsertedNDD = false;
		this.isFinished = false;
		
	}
	public FreelancerAbtainTopicTokenNDD(String inputFilepath,double thresholdSetting){
		this.inputFilepath = inputFilepath;
		this.freelancerfileNameGet = new FreelancerFileNameGet(this.inputFilepath);
		this.file_name_list = new ArrayList<>();
		
		//this.freelancerfileIn = new  FreelancerFileIn();
		this.category_documentList = new ArrayList<>();
		this.category_nddList = new ArrayList<>();
		this.calculateNDD = new CalculateNDD();
		this.choosed_category_idList = new ArrayList<>();
		this.choosed_tokenList = new ArrayList<>();
		this.thresholdSetting = thresholdSetting;
		this.category_id_choosed = 2;
		this.choosed_token_nddList = new ArrayList<>();
		
		this.isInitialized = true;
		this.isAbtainedFileNameList = false;
		this.isAbtainedCategoryDocumentList = false;
		this.isCreatedCategoryNddList = false;
		this.isAbtainedchoosedParameter = false;
		this.isCalculatedNDD = false;
		this.isInsertedNDD = false;
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
	public boolean getisCreatedCategoryNddList(){
		return this.isCreatedCategoryNddList;
	}
	public boolean getisCalculatedNDD(){
		return this.isCalculatedNDD;
	}
	public boolean getisAbtainedchoosedParameter(){
		return this.isAbtainedchoosedParameter;
	}
	public boolean getisInsertedNDD(){
		return this.isInsertedNDD;
	}
	public boolean getisFinished(){
		return this.isFinished;
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
			System.out.println("Finished the abtainFileNameList part ,the file_name_list.size() is  : "+this.file_name_list.size());
		}
		else{
			this.isAbtainedFileNameList = false;
			System.out.println("There is something wrong during the FreelancerAbtainTopicTokenNDD part : the getFileNameList part : u should finish the AbtainFileNameTail part first.");
		}
		return;
	}
	public List<List<DataDocDefined>> getCategoryDocumentList(){
		List<List<DataDocDefined>> category_documentList = new ArrayList<>();
		if(this.category_documentList.size() == 0){
			category_documentList = null;
		}
		else{
			category_documentList = this.category_documentList;
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
			System.out.println("Finished the abtainCategoryDocumentList part ,the category_documentList.size() is : "+this.category_documentList.size());
		}
		else{
			this.isAbtainedCategoryDocumentList = false;
			System.out.println("There is something wrong during the FreelancerAbtainTopicTokenNDD part : the abtainCategoryDocumentList part : u should finish the getFileNameList part first.");
		}
		return;
	}
	public List<DataNDDDefined> getcategoryNddList(){
		List<DataNDDDefined> category_NddList = new ArrayList<>();
		if(this.getisCreatedCategoryNddList()){
			category_NddList = this.category_nddList;
		}
		else{
			category_NddList = null;
			System.out.println("There is something wrong during the FreelancerAbtainTopicTokenNDD part : the getcategoryNddList part : u should finish the createCategoryNcdList part first.");
		}
		return category_NddList;
	}
	public void createCategoryNddList(){
		if(this.getisAbtainedCategoryDocumentList()){
			int index = 0;
			for(List<DataDocDefined> category_document : this.getCategoryDocumentList()){
				DataNDDDefined dataNDDDefinedTemp = new DataNDDDefined(category_document);
				dataNDDDefinedTemp.setCategoryId(index);
				dataNDDDefinedTemp.function();
				this.category_nddList.add(dataNDDDefinedTemp);
				index++;
			}
			this.isCreatedCategoryNddList = true;
			System.out.println("Finished the createCategoryNddList part ,the category_nddList.size() is : "+ this.category_nddList.size());
		}
		else{
			this.isCreatedCategoryNddList = false;
			System.out.println("There is something wrong during the FreelancerAbtainTopicTokenNDD part : the createCategoryNddList part : u should finish the abtainCategoryDocumentList part first.");
		}
		return;
	}
	public void abtainchoosedParameter(){
		//可以扩充的吖
		FreelancerNCDDBRead freelancerNCDDB = new FreelancerNCDDBRead();
		freelancerNCDDB.setTokenCategory(this.category_id_choosed);
		freelancerNCDDB.function();
		if(freelancerNCDDB.getisFinished()){
			this.choosed_category_idList = freelancerNCDDB.getChoosedCategoryIdList();
			this.choosed_tokenList = freelancerNCDDB.getChoosedTokenList();
			this.isAbtainedchoosedParameter = true;
			System.out.println("Finished the abtainchoosedParameter part,the choosed_tokenList.size() is : "+ this.choosed_tokenList.size());
		}
		else{
			this.isAbtainedchoosedParameter = false;
			System.out.println("There is something wrong during the FreelancerAbtainTopicTokenNDD part : the abtainchoosedParameter part : u should check the FreelancerNCDDBIO part.");
		}
		return;
	}
	public List<Integer> getchoosedCategorIdList(){
		List<Integer> choosed_categor_idList = new ArrayList<>();
		if(this.getisAbtainedchoosedParameter()){
			choosed_categor_idList = this.choosed_category_idList;
		}
		else{
			choosed_categor_idList = null;
			System.out.println("There is something wrong during the FreelancerAbtainTopicTokenNDD part : the getchoosedCategorIdList part : u should finish the abtainchoosedParameter part first");
		}
		return choosed_categor_idList;
	}
	public List<String> getchoosedTokenList(){
		List<String> choosed_tokenList = new ArrayList<>();
		if(this.getisAbtainedchoosedParameter()){
			choosed_tokenList = this.choosed_tokenList;
		}
		else{
			choosed_tokenList = null;
			System.out.println("There is something wrong during the FreelancerAbtainTopicTokenNDD part : the getchoosedTokenList part : u should finish the abtainchoosedParameter part first.");
		}
		return choosed_tokenList;
	}
	public void carrycalculateNDD(){
		if(this.getisCreatedCategoryNddList()){
			CalculateNDD calculateNDD = new CalculateNDD();
			calculateNDD.setInitialization(this.getcategoryNddList());
			calculateNDD.createUniqueTokensList();
			//tips: that something u may put out of the for loop
			int category_id = 0;
			String token = "";
			double ndd = 0;
			
			for(int index = 0;index <this.choosed_tokenList.size();index ++){
				category_id = this.choosed_category_idList.get(index);
				token = this.choosed_tokenList.get(index);
				calculateNDD.function(category_id, token);
				ndd = calculateNDD.getTokenNDD();
				calculateNDD.resetVarible();
				System.out.println( ndd+" ndd");
				this.choosed_token_nddList.add(ndd);
			}
			this.calculateNDD = calculateNDD;
			this.isCalculatedNDD = true;
			System.out.println("Finished the carrycalculateNDD part ,the choosed_token_nddList.size() is : "+ this.choosed_token_nddList.size());
		}
		else{
			this.isCalculatedNDD = false;
			System.out.println("There is something wrong during the FreelancerAbtainTopicTokenNDD part : the carrycalculateNDD part : u should finish the createCategoryNddList part first.");
		}
		return;
	}
	public CalculateNDD getcalculateNDD(){
		CalculateNDD calculateNDD = new CalculateNDD();
		if(this.getisCalculatedNDD()){
			calculateNDD = this.calculateNDD;
		}
		else{
			calculateNDD = null;
			System.out.println("There is something wrong during the FreelancerAbtainTopicTokenNDD part : the getChoosedTokenNddList part : u should finish the carrycalculateNDD part first.");
		}
		return calculateNDD;
	}
	public List<Double> getChoosedTokenNddList(){
		List<Double> choosed_token_nddList = new ArrayList<>();
		if(this.getisCalculatedNDD()){
			choosed_token_nddList = this.choosed_token_nddList;
		}
		else{
			choosed_token_nddList = null;
			System.out.println("There is something wrong during the FreelancerAbtainTopicTokenNDD part : the getChoosedTokenNddList part : u should finish the carrycalculateNDD part first.");
		}
		return choosed_token_nddList;
	}
	public void insertedNDD() throws Exception{
		if(this.getisCalculatedNDD()){
			int id = 0;
			String token = "";
			double ndd = 0;
			int category_id = 0;
			int is_topic_term = 0;
			int sumSize = this.choosed_tokenList.size();
			File storencdFile = new File("./result/ndd.txt");
			try {
				storencdFile.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try(PrintWriter storeNcd = new PrintWriter(storencdFile)){
				for(int index = 0; index < sumSize; index++){
					id = index+1;
					token = this.choosed_tokenList.get(index);
					ndd = this.choosed_token_nddList.get(index);
					category_id = this.choosed_category_idList.get(index);
					if(ndd >this.thresholdSetting){
						is_topic_term = 1;
					}
					else{
						is_topic_term = 0;
					}
					storeNcd.println(id+" "+token+" "+ndd+" "+category_id +" "+is_topic_term);
				}
			}
			catch(IOException e){
				e.printStackTrace();
			}
			
			try(FreelancerNDDDBInsert freelancerNDDDBIO = new FreelancerNDDDBInsert();){
				for(int index = 0; index < sumSize; index++){
					id = index+1;
					token = this.choosed_tokenList.get(index);
					ndd = this.choosed_token_nddList.get(index);
					category_id = this.choosed_category_idList.get(index);
					if(ndd >this.thresholdSetting){
						is_topic_term = 1;
					}
					else{
						is_topic_term = 0;
					}
					
					freelancerNDDDBIO.setId(id);
					freelancerNDDDBIO.setCategoryId(category_id);
					freelancerNDDDBIO.setisTopicTerm(is_topic_term);
					freelancerNDDDBIO.setNdd(ndd);
					freelancerNDDDBIO.setToken(token);
					freelancerNDDDBIO.insertDataToDB();
//					if(freelancerNDDDB.getisFinished()){
//						//do nothing , just continue
//					}
//					else{
//						break;
//					}
					if(index%1000==999)
						freelancerNDDDBIO.commit();
				}
				freelancerNDDDBIO.commit();
				}
				//freelancerNDDDBIO.close();
			if(id == sumSize){
				this.isInsertedNDD = true;
				System.out.println("Finished the insertedNDD part,the sum is :"+ sumSize);
			}
			else{
				this.isInsertedNDD = false;
				System.out.println("There is something wrong during the FreelancerAbtainTopicTokenNDD part : the insertedNDD part : u may check the FreelancerNDDDBIO part.");
			}
		}
		else{
			this.isInsertedNDD = false;
			System.out.println("There is something wrong during the FreelancerAbtainTopicTokenNDD part : the insertedNDD part : u may finishe the carrycalculateNDD part first.");
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
					this.createCategoryNddList();
					int idexofcat = 0;
					for(DataNDDDefined category_ndd_temp : this.getcategoryNddList()){
						idexofcat ++;
						System.out.println("第"+idexofcat+"个 category_ndd_temp.getDocumentLengthList().get(0):"+category_ndd_temp.getDocumentLengthList().get(0));
					}
					if(this.getisCreatedCategoryNddList()){
						this.abtainchoosedParameter();
						System.out.println("this.getchoosedCategorIdList().size() : "+this.getchoosedCategorIdList().size());
						System.out.println("this.getchoosedTokenList().size() : "+this.getchoosedTokenList().size());
						if(this.getisAbtainedchoosedParameter()){
							this.carrycalculateNDD();
							if(this.getisCalculatedNDD()){
								this.insertedNDD();
								if(this.getisInsertedNDD()){
									this.isFinished = true;
								}
								else{
									System.out.println("There is something wrong during the FreelancerAbtainTopicTokenNDD part : the function part : the insertedNDD part.");
								}
							}
							else{
								System.out.println("There is something wrong during the FreelancerAbtainTopicTokenNDD part : the function part : the carrycalculateNDD part.");
							}
						}
						else{
							System.out.println("There is something wrong during the FreelancerAbtainTopicTokenNDD part : the function part : the abtainchoosedParameter part.");
						}
					}
					else{
						System.out.println("There is something wrong during the FreelancerAbtainTopicTokenNDD part : the function part : the  createCategoryNddList part.");
					}
				}
				else{
					System.out.println("There is something wrong during the FreelancerAbtainTopicTokenNDD part : the function part : the abtainCategoryDocumentList part. ");
				}
			}
			else{
				System.out.println("There is something wrong during the FreelancerAbtainTopicTokenNDD part : the function part : the abtainFileNameList part.");
			}
		}
		else{
			System.out.println("There is something wrong during the FreelancerAbtainTopicTokenNDD part : the function part : u should finish the intialization first.");
		}
		return;
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String inputFilepath = "./result/filenamelist.txt";
		double thresholdSetting = 0.5;
		FreelancerAbtainTopicTokenNDD freelancerAbtianTopicNDD = new FreelancerAbtainTopicTokenNDD(inputFilepath,thresholdSetting);
		freelancerAbtianTopicNDD.function();
		if(freelancerAbtianTopicNDD.getisFinished()){
			System.out.println("Finished the FreelancerAbtainTopicTokenNDD part.");
		}
		else{
			System.out.println("There is something wrong during the FreelancerAbtainTopicTokenNDD part.");
		}
		return;
	
	}
	
}
