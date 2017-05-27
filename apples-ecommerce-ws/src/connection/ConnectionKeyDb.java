package connection;

import java.sql.*;

public class ConnectionKeyDb {
	
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;

	public static final String url = "jdbc:postgresql://54.186.125.96/key_db";

	public static final String user = "postgres";
	public static final String pw = "postgres";
	//public static final String url = "jdbc:postgresql://alcor.inf.unibz.it/ds_group9";
	//public static final String user = "ds_group9";
	//public static final String pw = "ohD6chai";
	
	public static Connection connect() {
	 try {
		  Class.forName("org.postgresql.Driver");
		  Connection con = DriverManager.getConnection(url, user, pw);
		  return con;
		  }
		  catch (ClassNotFoundException cnfe) {
		  System.out.println(cnfe.toString());
		  }
		  catch (SQLException sqle) {
		  System.out.println(sqle.toString());
		  }
	 	return null;
	}
	
	public static void close(Connection c)  {
		try {
			if(c != null) {
				c.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
