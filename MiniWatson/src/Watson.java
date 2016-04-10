import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import edu.stanford.nlp.trees.Tree;

public class Watson {

	public static void main(String[] argv) throws IOException {
		  
		
		String inputFile = argv[0].toString();
		//System.out.println("This is inputFile" + inputFile);
		BufferedReader in = new BufferedReader (new FileReader(inputFile));

	    String text = ""; 
	    
	    while((text = in.readLine()) != null){

	    List<Tree> trees = Parser.parse(text);
	    for (Tree tree : trees) {
	    	final StringBuilder sb = new StringBuilder();
	    	for ( final Tree t : tree.getLeaves() ) {
	    	     sb.append(t.toString()).append(" ");
	    	}
	    	System.out.println("<QUESTION> " + sb.toString());
	    	int movie = 0;
	    	int music = 0;
	    	int geography = 0;
	    	for(Tree st : tree){
		    	if (st.label().value().equals("NN")) {
		            //System.out.println("NN " + st.getChild(0).value());
		            String wd = st.getChild(0).value().toLowerCase();
		            if(Keywords.MovieNouns.containsKey(wd)){
		            	movie += Keywords.MovieNouns.get(wd);
		            }
		            if(Keywords.MusicNouns.containsKey(wd)){
		            	music += Keywords.MusicNouns.get(wd);
		            }
		            if(Keywords.GeoNouns.containsKey(wd)){
		            	geography += Keywords.GeoNouns.get(wd);
		            }
		        }// end if noun
		    	if (st.label().value().equals("VB") || st.label().value().equals("VBD") || st.label().value().equals("VBN")) {
		            //System.out.println("VB " + st.getChild(0).value());
		            String wd = st.getChild(0).value().toLowerCase();
		            if(Keywords.MovieVerbs.containsKey(wd)){
		            	movie += Keywords.MovieVerbs.get(wd);
		            }
		            if(Keywords.MusicVerbs.containsKey(wd)){
		            	music += Keywords.MusicVerbs.get(wd);
		            }
		            if(Keywords.GeoVerbs.containsKey(wd)){
		            	geography += Keywords.GeoVerbs.get(wd);
		            }
		        }// end if verb
		    	if(st.label().value().equals("JJR") || st.label().value().equals("JJS") || st.label().value().equals("JJ")){
		    		//System.out.println("JJR " + st.getChild(0).value());
		            String wd = st.getChild(0).value().toLowerCase();
		            if(Keywords.MovieAdjectives.containsKey(wd)){
		            	movie += Keywords.MovieAdjectives.get(wd);
		            }
		            if(Keywords.MusicAdjectives.containsKey(wd)){
		            	music += Keywords.MusicAdjectives.get(wd);
		            }
		            if(Keywords.GeoAdjectives.containsKey(wd)){
		            	geography += Keywords.GeoAdjectives.get(wd);
		            }
		    	}
	    	}// end for all nodes
	    	//System.out.println("Movies: " + movie);
	    	//System.out.println("Music: " + music);
	    	//System.out.println("Geography: " + geography);
	    	System.out.print("<CATEGORY> ");
	    	int max = Math.max(movie, Math.max(music, geography));
	    	if(max == movie){
	    		System.out.println("Movies");
	    	}
	    	if(max == music){
	    		System.out.println("Music");
	    	}
	    	if(max == geography){
	    		System.out.println("Geography");
	    	}
	    	System.out.println("<PARSETREE>");
	    	tree.pennPrint();
	    	}// end for all sentences 
	    }// end of while
	    
	  }
}
