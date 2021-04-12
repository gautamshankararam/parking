package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dbutil.DBUtil;
import model.Apron;
import model.Dock;
import model.ParkingArea;
import model.Revenue;


public class RevenueDAO {

	public static ArrayList<Revenue>  getDetails() {
		ArrayList<Revenue> details = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = DBUtil.getConnection();
			String sql = "select * from revenue";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			
			while(myRs.next()) {
				int id = myRs.getInt("revenue_id");
				String slot = myRs.getString("revenue_slot");
				int amount = myRs.getInt("revenue_amount");
				int vehicles = myRs.getInt("vehicles");
				int duration = myRs.getInt("duration");
				int rent = myRs.getInt("rent");
				Revenue revenue = new Revenue(id,slot,amount,vehicles,duration,rent);
				details.add(revenue);
			}
		}catch (SQLException e) {
			
			 e.printStackTrace();
		}finally {
			DBUtil.closeConnection(myConn, myStmt, myRs);
		}
	
		
		
		
		return details;
	}

	public static void updateRevenue(Revenue revenue, int revenueId) {
		
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			myConn = DBUtil.getConnection();
		
			Revenue currentRevenue = RevenueDAO.getDetail(revenueId);
			String sql = "update revenue set revenue_amount=?,vehicles=?,duration=? where revenue_id=?";
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1,revenue.getAmount()+currentRevenue.getAmount());
			myStmt.setInt(2,1+currentRevenue.getVehicles());
			myStmt.setInt(3,revenue.getDuration()+currentRevenue.getDuration());
			myStmt.setInt(4,revenueId);
			myStmt.execute();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			
			DBUtil.closeConnection(myConn, myStmt, null);
		}
		
		

	}
	
public static void updateRevenue(int revenueId) {
		int rent = 0;
		if(revenueId==1) {
			ArrayList<ParkingArea> areas = AreaDAO.getParkingAreas();
			for (int i = 0; i < areas.size(); i++) { 
	            rent+=areas.get(i).getRent();
			}
		}else if(revenueId==2) {
			ArrayList<Dock> docks = DockDAO.getDocks();
			for (int i = 0; i < docks.size(); i++) { 
	            rent+=docks.get(i).getRent();
			}
		}else if(revenueId==3) {
			ArrayList<Apron> aprons = ApronDAO.getAprons();
			for (int i = 0; i < aprons.size(); i++) { 
	            rent+=aprons.get(i).getRent();
			}
		}
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			myConn = DBUtil.getConnection();
		    
			
			
			String sql = "update revenue set rent=? where revenue_id=?";
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1,rent);
			
			myStmt.setInt(2,revenueId);
			myStmt.execute();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			
			DBUtil.closeConnection(myConn, myStmt, null);
		}
		
		

	}
	
	

	private static Revenue getDetail(int revenueId) {
		Revenue revenue = null;
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;	
		
		try {
			myConn = DBUtil.getConnection();
			String sql = "select * from revenue where revenue_id=?";
		
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, revenueId);
			
			myRs = myStmt.executeQuery();
			
			if(myRs.next()) {
				
				int amount=myRs.getInt("revenue_amount");
				String revenueSlot = myRs.getString("revenue_slot");
				int vehicles = myRs.getInt("vehicles");
				int duration = myRs.getInt("duration");
				revenue = new Revenue(revenueId,revenueSlot,amount,vehicles,duration);
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.closeConnection(myConn, myStmt, myRs);
		}
		
		
		return revenue;
	}

}

  
