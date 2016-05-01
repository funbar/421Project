import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.sqlite.*;


import java.util.ArrayList;

import edu.stanford.nlp.trees.Tree;

public class Watson {

	public static void main(String[] argv) throws IOException, SQLException {
		
		//check for input parameters
		if(argv.length != 1){
			System.out.println("Error: expecting input file");
			return;
		}
		/**
		java.sql.Connection c = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:oscar-movie_imdb.sqlite");
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    
	    
	    System.out.println("Opened database successfully");
	    Statement stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM Movie WHERE name LIKE \"Shrek\";" );
	      while ( rs.next() ) {
	         int id = rs.getInt("id");
	         String  name = rs.getString("name");
	         int year  = rs.getInt("year");
	         System.out.println( "ID = " + id );
	         System.out.println( "NAME = " + name );
	         System.out.println( "year = " + year );
	         System.out.println();
	      }
		*/
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
	    while((line = in.readLine()) != null){
	    	text += " " + line;
	    	if(line.length() > 0)
	    		sents.add(line);
	    }
	    in.close();
	    
	    // parse all question in the input file
	    List<Tree> trees = Parser.parse(text);
	    
	    // loop through all parsed questions
	    int i = 0;
	    for (Tree tree : trees) {
	    	SQLQuery query = new SQLQuery();
	    	query.addTable(Table.Actor);
	    	// print original question
	    	System.out.println("<QUESTION> " + sents.get(i));
	    	i++;
	    	
	    	//System.out.println("child: " + tree.getChild(0).value());
	    	if(tree.getChild(0).value().equals("SBARQ"))
	    	{
	    		System.out.println("wh question");
	    	}
	    	else
	    	{
	    		System.out.println("is question");
	    	}

	    	
	    	/**
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
	    	*/
	    	System.out.println("<PARSETREE>");
	    	tree.pennPrint();
	    }// end for all sentences 
	    
	  }// end main
}// end class
