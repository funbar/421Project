import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import edu.stanford.nlp.trees.Tree;

public class Watson {

	public static void main(String[] argv) throws IOException {
		
		//check for input parameters
		if(argv.length != 1){
			System.out.println("Error: expecting input file");
			return;
		}
		
		// open and read input file
		String inputFile = argv[0].toString();
		BufferedReader in;
		try{
			in = new BufferedReader (new FileReader(inputFile));
		}catch(FileNotFoundException ex){
			System.out.println(ex.getMessage());
			return;
		}
		ArrayList<String> sents = new ArrayList<String>();
 	    String line = "";
	    String text = "";
	    
	    String sqlQuery = ""; // changes
	    String tempString = ""; // stores specific details for the query ie. year "1987"

	    while((line = in.readLine()) != null){
	    	text += " " + line;
	    	sents.add(line);
	    }
	    in.close();
	    
	    // parse all question in the input file
	    List<Tree> trees = Parser.parse(text);
	    
	    // loop through all parsed questions
	    int i = 0;
	    for (Tree tree : trees) {
	    	// print original question
	    	System.out.println("<QUESTION> " + sents.get(i));
	    	i++;
	    	
	    	// initialize variables to represent the probability of each category
	    	int movie = 0;
	    	int music = 0;
	    	int geography = 0;
	    	
	    	// loop through all nodes in the parse tree
	    	for(Tree st : tree){
	    		// check for noun keywords
		    	if (st.label().value().equals("NN")) {

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
		    	
		    	// WRB = where
		    	// NNP = specific artist/pronoun
		    	if(st.label().value().equals("WRB") || st.label().value().equals("VBZ") || st.label().value().equals("WP") || st.label().value().equals("WDT") || st.label().value().equals("IN")){
		    		sqlQuery = sqlQuery.concat("select pname from Person p ");
		    		
		    	}
		    	if(st.label().value().equals("NNP")){
		    		sqlQuery = sqlQuery.concat("inner join Actor a on a.actor_id = ");
		    		
		    	}
		    	if(st.label().value().equals("NN") && st.nodeString().toLowerCase() == "oscar"){
		    		sqlQuery = sqlQuery.concat("inner join Oscar o on p.id = ");
		    		
		    	}
		    	if(st.label().value().equals("NP") || st.label().value().equals("NN")){
		    		tempString = st.nodeString();
		    		System.out.println("This is tempString: " + tempString);
		    	}
		    	System.out.println("This is sqlQuery: " + sqlQuery);
		    	
		    	// check for verb keywords
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
		    	
		    	// check for adjective keywords
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
		    	}// end if adjective
	    	}// end for all nodes

	    	System.out.print("<CATEGORY> ");
	    	if(movie > music && movie > geography){
	    		System.out.println("movies");
	    	}else if(music > movie && music > geography){
	    		System.out.println("music");
	    	}else if(geography > movie && geography > music){
	    		System.out.println("geography");
	    	}else{
	    		System.out.println("unknown");
	    	}
	    	
	    	System.out.println("<PARSETREE>");
	    	tree.pennPrint();
	    }// end for all sentences 
	    	
	  }// end main
}// end class
