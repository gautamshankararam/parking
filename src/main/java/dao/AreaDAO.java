package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



import dbutil.DBUtil;
import model.Car;
import model.ParkingArea;

public class AreaDAO {

	public static ArrayList<ParkingArea> getParkingAreas(){
		
		ArrayList<ParkingArea> areas = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
			
			try {
				
				myConn = DBUtil.getConnection();
				String sql = "select * from area";
				myStmt = myConn.createStatement();
				myRs = myStmt.executeQuery(sql);
	         
				while (myRs.next()) {
					
	            	int areaId = myRs.getInt("area_id");
					String areaName = myRs.getString("area_name");
					int areaCapacity = myRs.getInt("area_capacity");
					int areaBasicCharge = myRs.getInt("area_basic_charge");
					int areaBasicDuration = myRs.getInt("area_basic_duration");
					int areaExtraCharge = myRs.getInt("area_extra_charge");
					String areaType = myRs.getString("area_type");
					int areaRent = myRs.getInt("area_rent");
					ArrayList<Car> cars = (ArrayList<Car>) CarDAO.getCars(areaId);				
					ParkingArea area = new ParkingArea(areaId,areaName,areaCapacity,areaBasicCharge,areaBasicDuration,areaExtraCharge,areaType,areaRent,cars);
					
					areas.add(area);			
	            }
				
			} catch (SQLException e) {
	
				 e.printStackTrace();
			}finally {
				DBUtil.closeConnection(myConn, myStmt, myRs);
			}
			

			return areas;
	
}

	public static ParkingArea getArea(int areaId) {
		
		ParkingArea area = null;
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;	
		
			
		
		try {
			myConn = DBUtil.getConnection();
			String sql = "select * from area where area_id=?";
		
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, areaId);
			
			myRs = myStmt.executeQuery();
			
			
			if (myRs.next()) {
				
				String areaName = myRs.getString("area_name");
				int areaCapacity = myRs.getInt("area_capacity");
				int areaBasicCharge = myRs.getInt("area_basic_charge");
				int areaBasicDuration = myRs.getInt("area_basic_duration");
				int areaExtraCharge = myRs.getInt("area_extra_charge");
				String areaType = myRs.getString("area_type");
				int areaRent = myRs.getInt("area_rent");
				ArrayList<Car> cars = (ArrayList<Car>) CarDAO.getCars(areaId);				


				area = new ParkingArea(areaId,areaName,areaCapacity,areaBasicCharge,areaBasicDuration,areaExtraCharge,areaType,areaRent,cars);
			}
							
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.closeConnection(myConn, myStmt, myRs);
		}
			
		return area;
		
	
	}


	

public static void addParkingArea(ParkingArea area) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			
			myConn = DBUtil.getConnection();
			
			
			String sql = "insert into area(area_name,area_capacity,area_basic_charge,area_basic_duration,area_extra_charge,area_type,area_rent) values(?,?,?,?,?,?,?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			
			myStmt.setString(1, area.getName());
			myStmt.setInt(2, area.getCapacity());
			myStmt.setInt(3,area.getBasicCharge());
			myStmt.setInt(4,area.getBasicDuration());
			myStmt.setInt(5,area.getExtraCharge());
			myStmt.setString(6,area.getType());
			myStmt.setInt(7,area.getRent());
			
			myStmt.execute();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
			
		
		finally {
			
			DBUtil.closeConnection(myConn, myStmt, null);
		}
	
}


	public static void deleteArea(int areaId) {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			
			myConn = DBUtil.getConnection();
			
			
			String sql = "delete from area where area_id=?";
			
			
			myStmt = myConn.prepareStatement(sql);
			
			
			myStmt.setInt(1, areaId);
			
			
			myStmt.execute();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		

		
		finally {
			// clean up JDBC code
			DBUtil.closeConnection(myConn, myStmt, null);
		}

		
	}

	public static void updateArea(ParkingArea area, int areaId) {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			
			myConn = DBUtil.getConnection();
			
			// create SQL update statement
			String sql = "update area set area_name=?,area_capacity=?,area_basic_charge=?,area_basic_duration=?,area_extra_charge=?,area_type=?,area_rent=? where area_id=?";
			
			myStmt = myConn.prepareStatement(sql);
			
			
			myStmt.setString(1, area.getName());
			myStmt.setInt(2, area.getCapacity());
			myStmt.setInt(3,area.getBasicCharge());
			myStmt.setInt(4,area.getBasicDuration());
			myStmt.setInt(5,area.getExtraCharge());
			myStmt.setString(6,area.getType());
			myStmt.setInt(7,area.getRent());
			myStmt.setInt(8,areaId);
			// execute SQL statement
			myStmt.execute();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			// clean up JDBC objects
			DBUtil.closeConnection(myConn, myStmt, null);
		}
		
	}
}
