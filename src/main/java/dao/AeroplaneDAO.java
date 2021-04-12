package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import dbutil.DBUtil;
import model.Aeroplane;


public class AeroplaneDAO {
	
	public static ArrayList<Aeroplane> getAeroplanes(int apronId) {
        ArrayList<Aeroplane> aeroplanes = new ArrayList<>();
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
			
			try {
				myConn = DBUtil.getConnection();
				String sql = "select * from aeroplane where apron_id=?";
				myStmt = myConn.prepareStatement(sql);
				myStmt.setInt(1, apronId);
				
				myRs = myStmt.executeQuery();
				while (myRs.next()) {
					
	            	int aeroplaneId = myRs.getInt("aeroplane_id");
					String aeroplaneName = myRs.getString("aeroplane_name");
					String apronName = myRs.getString("apron_name");
					int aeroplaneParkingDuration = myRs.getInt("aeroplane_parking_duration");
					int aeroplaneParkingCost = myRs.getInt("aeroplane_parking_cost");
					Aeroplane aeroplane = new Aeroplane(aeroplaneId,aeroplaneName,apronId,apronName,aeroplaneParkingDuration,aeroplaneParkingCost);
					aeroplanes.add(aeroplane);			
	            }
				
			} catch (SQLException e) {
	
				 e.printStackTrace();
			}finally {
				DBUtil.closeConnection(myConn, myStmt, myRs);
			}
			

			return aeroplanes;

		
	}
	
	public static void addAeroplane(Aeroplane aeroplane, int apronId) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			
			myConn = DBUtil.getConnection();
			
			
			String sql = "insert into aeroplane(aeroplane_name,apron_id,apron_name,aeroplane_parking_duration,aeroplane_parking_cost) values(?,?,?,?,?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setString(1, aeroplane.getName());
			myStmt.setInt(2, apronId);
			myStmt.setString(3, aeroplane.getApronName());
			myStmt.setInt(4, aeroplane.getParkingDuration());
			myStmt.setInt(5,aeroplane.getParkingCost());
			
			
			
			
			myStmt.execute();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
			
		
		finally {
			
			DBUtil.closeConnection(myConn, myStmt, null);
		}
		
		
	}


	


	public static void deleteAeroplane(int aeroplaneId) {
	    Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			
			myConn = DBUtil.getConnection();
			
			
			String sql = "delete from aeroplane where aeroplane_id=?";
			
			
			myStmt = myConn.prepareStatement(sql);
			
			
			myStmt.setInt(1, aeroplaneId);
			
			
			myStmt.execute();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		

		
		finally {
			// clean up JDBC code
			DBUtil.closeConnection(myConn, myStmt, null);
		}
		
		
	}
	
	
	
	
	public static void deleteAeroplanes(int apronId) {
		
		ArrayList<Aeroplane> aeroplanes = AeroplaneDAO.getAeroplanes(apronId);
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			
			myConn = DBUtil.getConnection();
			
			
			for(int i=0;i<aeroplanes.size();i++) {
				
				String sql = "delete from aeroplane where aeroplane_id=?";
				
				myStmt = myConn.prepareStatement(sql);
				
				
				myStmt.setInt(1, aeroplanes.get(i).getId());
				
				
				myStmt.execute();
				
			}
			
			
			
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		

		
		finally {
			// clean up JDBC code
			DBUtil.closeConnection(myConn, myStmt, null);
		}
		
		
	}


}
