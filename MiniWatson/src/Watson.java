import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.sql.SQLException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.trees.LabeledScoredTreeNode;
import edu.stanford.nlp.trees.Tree;

public class Watson {

	private static java.sql.Connection c = null;
	
	/**
	 * main class, accepts input, opens database, calls parser, semantic representation,
	 * runs the SQL commands, and displays output 
	 * 
	 * @param argv - input parameters
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void main(String[] argv) throws IOException
	{
		
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
	    while((line = in.readLine()) != null){
	    	text += " " + line;
	    	if(line.length() > 0)
	    		sents.add(line);
	    }
	    in.close();
	    
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:oscar-movie_imdb.sqlite");
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    
	    // parse all question in the input file
	    List<Tree> trees = Parser.parse(text);
	    
	    // loop through all parsed questions
	    int i = 0;
	    for (Tree tree : trees) {
	    	// print original question
	    	System.out.println("<QUESTION> " + sents.get(i));
	    	i++;
	    	
	    	SQLQuery query = getQuery(tree);
	    	
	    	System.out.println("<SQL Statement>");
	    	System.out.println(query.toString());
	    	
	    	System.out.println("<ANSWER>");
	    	
	    	System.out.println("<PARSETREE>");
	    	tree.pennPrint();
	    }// end for all sentences 
	    
	}// end main
	
	/**
	 * Start semantic attachments and lambda reductions at ROOT of each question
	 * ROOT -> SBARQ for wh questions and
	 * ROOT -> SQ for yes/no questions
	 * 
	 * @param sent - ROOT node for a questions parse tree
	 * @return the SQL query string representation of the question
	 */
	private static SQLQuery getQuery(Tree sent)
	{
		SQLQuery query = new SQLQuery();
    	Tree qt;
    	if((qt = sent.getChild(0)).value().equals("SBARQ"))
    	{// ROOT -> SBARQ for all wh questions
    		System.out.println("wh question: ");
    		System.out.println(qt.getLeaves().get(0));
    		
    		
    	}
    	else if((qt = sent.getChild(0)).value().equals("SQ"))// all yes/no questions
    	{// ROOT -> SQ for all yes/no questions
    		System.out.println("is question: " + qt.getLeaves().get(0));
	    	sqSem(qt, query);
	    	
	    	
    	}

		return query;
	}// end getQuery
	
	/**
	 * Semantic attachments for SQ
	 * 
	 * @param sq - a SQ tree node
	 * @param query - our query building object
	 * @return
	 */
	private static String sqSem(Tree sq, SQLQuery query)
	{
		query.setSelect("count(*)"); // semantic attachment for all "is questions"
    	List<Tree> sqrule = sq.getChildrenAsList();
    	/**
    	System.out.println(qrule.size());
    	for(Tree child : qrule)
    	{
    		System.out.println(child.nodeString());
    	}*/
    	if((sqrule.size() == 4)&&sqrule.get(1).nodeString().equals("NP")&&sqrule.get(2).nodeString().equals("VP"))
    	{// SQ -> VBD NP VP .
    		System.out.println("SQ -> VBD NP VP .");
    		Tree np = sqrule.get(1);
    		String npsem = npSem(np, query);
    		Tree vp = sqrule.get(2);
    		String vpsem = vpSem(vp, query);
    		return "";
    	}
    	if((sqrule.size()==4)&&sqrule.get(1).nodeString().equals("NP")&&sqrule.get(2).nodeString().equals("NP"))
    	{// SQ -> VBZ NP NP
    		System.out.println("SQ -> VBZ NP NP");
    		Tree np1 = sqrule.get(1);
    		String np1sem = npSem(np1, query);
    		Tree np2 = sqrule.get(2);
    		String np2sem = npSem(np2, query);
    		return "";
    	}
    	return "";
	}
	
	
	/**
	 * NP semantic attachments
	 * 
	 * @param np - a NP tree node
	 * @param query - our query building object
	 * @return
	 */
	private static String npSem(Tree np, SQLQuery query)
	{
		for(Tree l : np)
		{
			if(l.nodeString().equals("NNP"))
			{
				nnpSem(l.getChild(0), query);
			}
			if(l.nodeString().equals("NN"))
			{
				nnSem(l.getChild(0), query);
			}
			if(l.nodeString().equals("PP"))
			{
				ppSem(l, query);
			}
		}
		return "";
	}
	
	/**
	 * NNP semantic attachments
	 * 
	 * @param nnp - a NNP tree node
	 * @param query - our query building object
	 * @return
	 */
	private static String nnpSem(Tree nnp, SQLQuery query)
	{
		String pn = nnp.nodeString();
		//System.out.println("Found proper noun: " + pn);
		if(isPerson(pn))
		{
			query.addWhere("P.name LIKE \"%" + pn + "%\"");
		}
		else if(isMovie(pn))
		{
			query.addWhere("M.name LIKE \"%" + pn + "%\"");
		}
		return "";
	}
	
	/**
	 * NN semantic attachments
	 * 
	 * @param nn
	 * @param query
	 * @return
	 */
	private static String nnSem(Tree nn, SQLQuery query)
	{
		String noun = nn.nodeString();
		if(noun.equals("director"))
		{
			query.setFrom("FROM Person P");
			query.setFrom("INNER JOIN Director D on P.id = D.director_id");
		}
		else if(noun.equals("actor"))
		{
			query.setFrom("FROM Person P");
			query.setFrom("INNER JOIN Actor A on P.id = A.actor_id");
		}
		
		return "";
	}
	
	/**
	 * VP semantic attachments 
	 * 
	 * @param vp - a VP tree node
	 * @param query -  our query building object
	 * @return
	 */
	private static String vpSem(Tree vp, SQLQuery query)
	{
		List<Tree> vprule = vp.getChildrenAsList();
		if((vprule.size() == 2) && vprule.get(0).nodeString().equals("VB") && vprule.get(1).nodeString().equals("NP"))
		{
			System.out.println("VP -> VB NP");
			Tree vb = vprule.get(0);
			String vbsem = vbSem(vb, query);
			Tree np = vprule.get(1);
			String npsem = npSem(np,query);
			System.out.println("npsem: " + npsem);
			query.lambdaWhere(npsem);
			
		}
		if((vprule.size() == 2) && (vprule.get(0).nodeString().equals("VB")) && (vprule.get(1).nodeString().equals("PP")))
		{
			System.out.println("VP -> VB NP");
			Tree vb = vprule.get(0);
			String vbsem = vbSem(vb, query);
			Tree pp = vprule.get(1);
			String npsem = ppSem(pp,query);
			//System.out.println("npsem: " + npsem);
			//query.lambdaWhere(npsem);
		}
		
		return "";
	}
	
	/**
	 * PP semantic attachments 
	 * 
	 * @param pp - a PP tree node
	 * @param query - our query building object
	 * @return
	 */
	private static String ppSem(Tree pp, SQLQuery query)
	{
		//System.out.println("reached");
		List<Tree> pprule = pp.getChildrenAsList();
		//System.out.println("pp child 1: " + pprule.get(0).nodeString());
		if((pprule.size() == 2) && (pprule.get(0).nodeString().equals("IN")) && (pprule.get(1).nodeString().equals("NP")))
		{// PP -> IN NP
			System.out.println("PP -> IN NP");
			Tree in = pprule.get(0);
			String inSem = inSem(in, query);
			Tree np = pprule.get(1);
			String npsem = npSem(np, query);
			return npsem;
		}
		return "";
	}
	
	public static String inSem(Tree in, SQLQuery query)
	{
		String prep = in.getChild(0).nodeString();
		System.out.println("Prep: "+ prep);
		if(prep.equals("by"))
		{// IN -> by
			System.out.println("IN -> by");
			query.setFrom("FROM Person P");
			query.setFrom("INNER JOIN Director D on P.id = D.director_id");
			query.setFrom("INNER JOIN Movie M on D.movie_id = M.id");
		}
		
		return "";
	}
	
	/**
	 * VB semantic attachments
	 * 
	 * @param vb - a VB tree node
	 * @param query - our query building object
	 * @return
	 */
	private static String vbSem(Tree vb, SQLQuery query)
	{
		String verb = vb.getChild(0).nodeString();
		//System.out.println("verb: " + verb);
		
		if(verb.equals("direct"))
		{
			query.setFrom("FROM Person P");
			query.setFrom("INNER JOIN Director D on P.id = D.director_id");
			query.setFrom("INNER JOIN Movie M on D.movie_id = M.id");
			//query.setWhere("M.name LIKE \"%LAMBDA%\" AND P.name LIKE \"%LAMBDA%\"");
			return "direct";
		}
		if(verb.equals("act") || verb.equals("star"))
		{
			query.setFrom("FROM Person P");
			query.setFrom("INNER JOIN Actor A on P.id = A.actor_id");
			query.setFrom("INNER JOIN Movie M on A.movie_id = M.id");
			//query.setWhere("M.name LIKE \"%LAMBDA%\" AND P.name LIKE \"%LAMBDA%\"");
			return "star";
		}
		
		return "";
	}
	
	
	/**
	 * Lookup a proper noun in the movie table to see if it is a part
	 * of a movie title
	 * 
	 * @param name - a proper noun
	 * @return true if name exists in our movie table, false otherwise
	 */
	private static boolean isMovie(String name)
	{
		try
		{
			Statement stmt = c.createStatement();
			String query = "SELECT COUNT(*) FROM Movie WHERE name LIKE \"%" + name + "%\"";
		    ResultSet rs = stmt.executeQuery(query);
		    if(rs.next())
		    {
		    	if(rs.getInt(1) >= 1)
		    	{
		    		//System.out.println(name + " is movie");
		    		return true;
		    	}
		    	else
		    		return false;
		    }
		    else
		    	return false;
		}
	    catch(Exception e )
		{
	    	System.out.println("SQL error in movie lookup");
	    	return false;
	    	//System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    //System.exit(0);
		}	
	}
	
	/**
	 * Lookup a proper noun in the person table to see if it is a part
	 * of a persons name
	 * 
	 * @param name - a proper noun
	 * @return true if name exists in our person table, false otherwise
	 */
	private static boolean isPerson(String name)
	{
		try
		{
			Statement stmt = c.createStatement();
			String query = "SELECT COUNT(*) FROM Person WHERE name LIKE \"%" + name + "%\"";
			ResultSet rs = stmt.executeQuery(query);
		    if(rs.next())
		    {
		    	if(rs.getInt(1) >= 1)
		    	{
		    		//System.out.println(name + " is person");
		    		return true;
		    	}
		    	else
		    		return false;
		    }
		    else
		    	return false;
		}
	    catch(Exception e )
		{
	    	System.out.println("SQL error in person lookup");
	    	return false;
	    	//System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    //System.exit(0);
		}
	}
}// end class
