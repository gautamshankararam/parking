package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import dbutil.DBUtil;
import model.Ship;

public class ShipDAO {
	
	public static ArrayList<Ship> getShips(int dockId) {
        ArrayList<Ship> ships = new ArrayList<>();
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
			
			try {
				
				myConn = DBUtil.getConnection();
				String sql = "select * from ship where dock_id=?";
				myStmt = myConn.prepareStatement(sql);
				myStmt.setInt(1, dockId);
				
				myRs = myStmt.executeQuery();
				while (myRs.next()) {
					
	            	int shipId = myRs.getInt("ship_id");
					String shipName = myRs.getString("ship_name");
					String dockName = myRs.getString("dock_name");
					int shipParkingDuration = myRs.getInt("ship_parking_duration");
					int shipParkingCost = myRs.getInt("ship_parking_cost");
					Ship ship = new Ship(shipId,shipName,dockId,dockName,shipParkingDuration,shipParkingCost);
					ships.add(ship);			
	            }
				
			} catch (SQLException e) {
	
				 e.printStackTrace();
			}finally {
				DBUtil.closeConnection(myConn, myStmt, myRs);
			}
			

			return ships;

		
	}

	
	public static void addShip(Ship ship, int dockId) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			
			myConn = DBUtil.getConnection();
			
			
			String sql = "insert into ship(ship_name,dock_id,dock_name,ship_parking_duration,ship_parking_cost) values(?,?,?,?,?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setString(1, ship.getName());
			myStmt.setInt(2, dockId);
			myStmt.setString(3, ship.getDockName());
			myStmt.setInt(4, ship.getParkingDuration());
			myStmt.setInt(5, ship.getParkingCost());
			
			myStmt.execute();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
			
		
		finally {
			
			DBUtil.closeConnection(myConn, myStmt, null);
		}

		
	}

	

	public static void deleteShip(int shipId) {
		// TODO Auto-generated method stub
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			
			myConn = DBUtil.getConnection();
			
			
			String sql = "delete from ship where ship_id=?";
			
			
			myStmt = myConn.prepareStatement(sql);
			
			
			myStmt.setInt(1, shipId);
			
			
			myStmt.execute();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		

		
		finally {
			// clean up JDBC code
			DBUtil.closeConnection(myConn, myStmt, null);
		}
		
	}

	public static void deleteShips(int dockId) {
		
		ArrayList<Ship> ships = ShipDAO.getShips(dockId);
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			
			myConn = DBUtil.getConnection();
			
			
			for(int i=0;i<ships.size();i++) {
				
				String sql = "delete from ship where ship_id=?";
				
				myStmt = myConn.prepareStatement(sql);
				
				
				myStmt.setInt(1, ships.get(i).getId());
				
				
				myStmt.execute();
				
			}
			
			
			
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		

		
		finally {
			
			DBUtil.closeConnection(myConn, myStmt, null);
		}
		
	}
	

}
