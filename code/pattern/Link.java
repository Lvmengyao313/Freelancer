package pattern.demo;

import java.util.ArrayList;
import java.util.List;

public class Link {
	List<ArrayList<String>> outputFrontList;
	List<ArrayList<String>> outputBehindList;
	
	public Link(){
		 outputFrontList=new ArrayList<ArrayList<String>>();
		 outputBehindList=new ArrayList<ArrayList<String>>();
	}
	
	public List<ArrayList<String>> getOutputFrontList() {
		return outputFrontList;
	}
	
	public void setOutputFrontList(List<ArrayList<String>> outputFrontList) {
		this.outputFrontList = outputFrontList;
	}
	
	public List<ArrayList<String>> getOutputBehindList() {
		return outputBehindList;
	}
	
	public void setOutputBehindList(List<ArrayList<String>> outputBehindList) {
		this.outputBehindList = outputBehindList;
	}

	
}
