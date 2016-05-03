import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
public class SQLConn {
/*
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
	*/
	
	public void resultFromDB(String query) throws java.sql.SQLException {
	Connection c = null;
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:oscar-movie_imdb.sqlite");
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    
    String defaultQuery = "SELECT count(*) FROM Movie WHERE name LIKE \"Shrek\";";
    
    System.out.println("Opened database successfully");
    Statement stmt = c.createStatement();
    if(query == null){
    	ResultSet rs = stmt.executeQuery(defaultQuery);
        while ( rs.next() ) {
        	// returns first column as resultset
        	int count = rs.getInt(1);
            System.out.println();
            if(count > 0)
            {
            	System.out.println("Yes");
            	
            }
            else if(count <= 0)
            {
            	System.out.println("No");
            }
            else
            {
            	// this will just return the count as a string, not really what we want. 
            	System.out.println("This is the string: " + rs.getString(1));
            }
         }

    }
    	else{
      ResultSet rs = stmt.executeQuery(query);
      while ( rs.next() ) {
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

}
