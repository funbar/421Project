
import java.sql.*;

public class SQLite
{
  public static void main( String args[] ) 
  {
    Connection c = null;
    try {
      Class.forName("SQLite.JDBCDriver");
      // Note: /test.db is the test.db in the *current* working directory
      c = DriverManager.getConnection("jdbc:sqlite:/test.db","","");
      c.setAutoCommit(false);

      Statement st = c.createStatement();
      int rc = st.executeUpdate( "INSERT INTO x(b) VALUES('qwer')" );
      System.out.println( "insert returns " + rc );

      ResultSet rs = st.executeQuery( "SELECT * FROM x" );
      while ( rs.next() ) {
	int i = rs.getInt(1);
	String  s = rs.getString(2);
	System.out.println( "i=" + i + ", s=" + s );
      }
      rs.close();
      st.close();

      c.commit();
      c.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(1);
      try {
	if ( c != null && ! c.isClosed() ) {
	  c.rollback();
	  c.close();
	}
      } catch ( SQLException sql ) { 
	// ignore
      }
    }
  }
}