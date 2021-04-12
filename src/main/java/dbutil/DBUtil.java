package dbutil;

import java.sql.*;

public class DBUtil {
	
	public static Connection getConnection()
	{
		Connection conn = null;
		try 
		{
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn=DriverManager.getConnection( "jdbc:mysql://localhost:3306/parking?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","Bhanuregga@99");  
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return conn;
	}
	
	
	public static void closeConnection(Connection myConn,Statement myStmt, ResultSet myRs)
	{
		try {
			if (myRs != null) {
				myRs.close();
			}
			
			if (myStmt != null) {
				myStmt.close();
			}
			
			if (myConn != null) {
				myConn.close();   
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}

}
