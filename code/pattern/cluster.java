package pattern.demo;

import java.util.ArrayList;
import java.util.List;

public class cluster {
	public String value;
	public List<Integer> projectIds;
	
	public cluster(){
		 value=new String();
		 projectIds=new ArrayList<Integer>();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<Integer> getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(List<Integer> projectIds) {
		this.projectIds = projectIds;
	}
	

}
