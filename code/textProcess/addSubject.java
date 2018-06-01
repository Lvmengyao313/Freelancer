package textProcess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class addSubject {
	List<String> DesList=new ArrayList<String>();

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String in_path = "./result/text/10000.txt";
		String out_path = "./result/text/10000-.txt";
		addSubject a=new addSubject();
		a.readtxt(in_path);
		a.writeToFile(out_path);
		
	}

	
	public void readtxt(String in_path) throws IOException{
		FileReader fr = new FileReader(in_path);
	    BufferedReader br = new BufferedReader(fr);	
	    String str;
	    while((str = br.readLine()) != null) {
	    	DesList.add(Desprocess(str));
	    }
	}
	
	public static String Desprocess(String Des){
		StringBuffer newDes = new StringBuffer();
		int flag=0;
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		Annotation document = new Annotation(Des);
		pipeline.annotate(document);
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		
		for (CoreMap sentence : sentences) {
			flag=0;
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				String word = token.get(TextAnnotation.class);				
				String pos = token.get(PartOfSpeechAnnotation.class);
				if(flag==0){
					flag=1;
					if(pos.startsWith("V")){
						newDes.append("it ");	
					}														
				}
				newDes.append(word+" ");
			}
		}
	  
		return newDes.toString();
	}
	
	
	
	
	public void writeToFile(String out_path) {
		try{
			File file = new File(out_path);
			if (file.exists()) {
	//			System.out.println("file exists");
			} else {
				file.createNewFile();
			}
            System.setOut(new PrintStream(out_path));
           
            for(String s:DesList){
                System.out.println(s);           	
            }

        }catch(IOException e) {
            e.printStackTrace();    
        }
	}
	
	
	
}
