
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
	    return java.sql.DriverManager.getConnection(strConn , DBusername, DBpassword);
	}

}
