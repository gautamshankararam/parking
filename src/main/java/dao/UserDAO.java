package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



import dbutil.DBUtil;

import model.User;

public class UserDAO {

	public static void signup(User user) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			
			myConn = DBUtil.getConnection();
			
			
			String sql = "insert into user(user_email,user_password,user_role,user_status) values(?,?,?,?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setString(1, user.getEmail());
			myStmt.setString(2, user.getPassword());
			myStmt.setString(3, user.getRole());
			myStmt.setString(4, user.getStatus());
			myStmt.execute();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
			
		
		finally {
			
			DBUtil.closeConnection(myConn, myStmt, null);
		}
		
	}

	public static User getUser(String email) {
		User user = null;
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = DBUtil.getConnection();
			String sql = "select * from user where user_email=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, email);
			
			myRs = myStmt.executeQuery();
			while (myRs.next()) {
				
            	int userId = myRs.getInt("user_id");
				String userEmail = myRs.getString("user_email");
				String userPassword = myRs.getString("user_password");
				String userRole = myRs.getString("user_role");
				String userStatus = myRs.getString("user_status");
				user = new User(userId,userEmail,userPassword,userRole,userStatus);
							
            }
			
		} catch (SQLException e) {

			 e.printStackTrace();
		}finally {
			DBUtil.closeConnection(myConn, myStmt, myRs);
		}

		return user;
	}

	public static ArrayList<User> getUsers() {
		
        ArrayList<User> users = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
			
			try {
				
				myConn = DBUtil.getConnection();
				String sql = "select * from user";
				myStmt = myConn.createStatement();
				myRs = myStmt.executeQuery(sql);
	         
				while (myRs.next()) {
					
	            	int id = myRs.getInt("user_id");
					String email = myRs.getString("user_email");
					String password = myRs.getString("user_password");
					String role = myRs.getString("user_role");
					String status = myRs.getString("user_status");
					
									
					User user = new User(id,email,password,role,status);
					
					users.add(user);			
	            }
				
			} catch (SQLException e) {
	
				 e.printStackTrace();
			}finally {
				DBUtil.closeConnection(myConn, myStmt, myRs);
			}
			

			return users;

	}

	public static void updateUser(User user,int userId) {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			
			myConn = DBUtil.getConnection();
			
			
			String sql = "update user set user_status=?,user_password=? where user_id=?";
			
			myStmt = myConn.prepareStatement(sql);
			
			
			myStmt.setString(1, "active");
			myStmt.setString(2, user.getPassword());
			myStmt.setInt(3, userId);
			
			myStmt.execute();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			
			DBUtil.closeConnection(myConn, myStmt, null);
		}		
		
	}

}
