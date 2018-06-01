package textProcess;

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

public class Processfuction {
	
	public String replace(String Des){		
		Des=Des.replace("\r",". ").replace("\n",". ").replace(",", ".");
		Des=Des.replace("'ll", " will").replace("'m", " am").replace("'re", " are").replace("'ve", " have").replace("'d", " would").replace("\\", "");
		Des=Des.replace("looking for", "need").replace("am looking for", "need").replace("are looking for", "need").replace("are in need of", "need").replace("similar to", "like");	
		Des=Des.replace(">"," ").replace("my", "").replace("your", "").replace("our", "").replace("their", "");
		Des=Des.replace("want to", "").replace("be able to", "").replace("am able to", "").replace("are able to", "").replace("is able to", "").replace("need to", "").replace("would like to", "").replace("have to", "").replace("has to", "").replace("is willing to", "").replace("trying to", "").replace("try to", "");
		return Des;		
	}
	
	public String Desprocess(String Des){
		String newDes = "";
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		Annotation document = new Annotation(Des);
		pipeline.annotate(document);
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		List<String> wordList = new ArrayList<String>();
		List<String> posList = new ArrayList<String>();
		
		for (CoreMap sentence : sentences) {

			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				String word = token.get(TextAnnotation.class);				
				String pos = token.get(PartOfSpeechAnnotation.class);
				
				if(!pos.equals("RB")&&!word.equals("would")&&!word.equals("must")&&!word.equals("may")&&!word.equals("will")&&!word.equals("maybe")&&!word.equals("should")&&!word.equals("can")&&!word.equals("please")){
					
					if(word.equals("-LRB-")){
						wordList.add("(");
						posList.add(pos);						
					}
					else if(word.equals("-RRB-")){
						wordList.add(")");
						posList.add(pos);	
					}
					else if(word.startsWith("www.")||word.startsWith("http:")||word.startsWith("https:")||word.endsWith(".com")){
						wordList.add("www.com");
						posList.add(pos);	
					}
					else{
						wordList.add(word);
						posList.add(pos);	
					}
					
				}				
			}
			String s = "";
			for (int i = 0; i < posList.size(); i++) {
				if(i>0&&i<posList.size()-1){
					if((wordList.get(i).equals("that")||wordList.get(i).equals("which")||wordList.get(i).equals("who"))&& posList.get(i-1).startsWith("N") &&posList.get(i+1).startsWith("V") ){
						int k=i-1;
						while(k>=0&&posList.get(k).startsWith("N")){
							s=wordList.get(k)+" "+s;
							k--;
						}						
//						newDes+=". "+wordList.get(i-1)+" ";		
						newDes+=". "+s+" ";	
						s="";
					}
					else{
						newDes+=wordList.get(i)+" ";			
					}					
				}
				else{
					newDes+=wordList.get(i)+" ";					
				}										
			}			
			wordList.removeAll(wordList);
			posList.removeAll(posList);
		}
		return newDes;
					
	}
	
	  public void Titleprocess(String title){
	    	
			
	  }
	  
	  
	  public static void main(String args[]){
		  Processfuction pf=new  Processfuction();
		  String des="Need  English-speaker who  transcripe  audio ";
		  des=pf.replace(des);  
      	 des=pf.Desprocess(des);
      	
      	System.out.println(des);
	  }
	  
	  

}
