package pers.tong.MappingTools.freelancerOperation;

import java.io.File;

public class readFileName {	
	public static void main(String[] args){
		File file=new File("./result/category/");
		
		String []test=file.list();
		for(int i=0;i<test.length;i++)
		{
			System.out.println(test[i]);
		}
	}

}
