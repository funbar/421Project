import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;


public class SQLConn {

	static final String DBDriver  ="com.mysql.jdbc.Driver";
	static final String strConn   ="jdbc:mysql://localhost/";
	static final String DBusername="admin";
	static final String DBpassword="admin";
	

	public static String loadDriver () {
	    String sErr = "";
	    try {
	      java.sql.DriverManager.registerDriver((java.sql.Driver)(Class.forName(DBDriver).newInstance()));
	    }
	    catch (Exception e) {
	      sErr = e.toString();
	    }
	    return (sErr);
	  }

	java.sql.Connection connectToDB() throws java.sql.SQLException {
	    System.out.println("*** reached ***");

	    return java.sql.DriverManager.getConnection(strConn , DBusername, DBpassword);
	}
	
	public static void main(String[] argv) throws IOException, SQLException 
	{
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
	    while ( rs.next() )
	    {
	    	int id = rs.getInt("id");
	        String  name = rs.getString("name");
	        int year  = rs.getInt("year");
	        System.out.println( "ID = " + id );
	        System.out.println( "NAME = " + name );
	        System.out.println( "year = " + year );
	        System.out.println();
	    }
	
	}
}
