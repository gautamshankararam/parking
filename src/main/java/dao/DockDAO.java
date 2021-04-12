package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import dbutil.DBUtil;
import model.Dock;

import model.Ship;


public class DockDAO {

	public static ArrayList<Dock> getDocks() {
		ArrayList<Dock> docks = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
			
			try {
				
				myConn = DBUtil.getConnection();
				String sql = "select * from dock";
				myStmt = myConn.createStatement();
				myRs = myStmt.executeQuery(sql);
	         
				while (myRs.next()) {
					
	            	int dockId = myRs.getInt("dock_id");
					String dockName = myRs.getString("dock_name");
					int dockCapacity = myRs.getInt("dock_capacity");
					int dockBasicCharge = myRs.getInt("dock_basic_charge");
					int dockBasicDuration = myRs.getInt("dock_basic_duration");
					int dockExtraCharge = myRs.getInt("dock_extra_charge");
					String dockType = myRs.getString("dock_type");
					int dockRent = myRs.getInt("dock_rent");
					ArrayList<Ship> ships = (ArrayList<Ship>) ShipDAO.getShips(dockId); 			
				    Dock dock = new Dock(dockId,dockName,dockCapacity,dockBasicCharge,dockBasicDuration,dockExtraCharge,dockType,dockRent,ships);
					

					docks.add(dock);			
	            }
				
			} catch (SQLException e) {
	
				 e.printStackTrace();
			}finally {
				DBUtil.closeConnection(myConn, myStmt, myRs);
			}
			

			return docks;
	}

	public static Dock getDock(int dockId) {
        
		Dock dock = null;
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;	
		
			
		
		try {
			myConn = DBUtil.getConnection();
			String sql = "select * from dock where dock_id=?";
		
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, dockId);
			
			myRs = myStmt.executeQuery();
			
			
			if (myRs.next()) {
				
				String dockName = myRs.getString("dock_name");
				int dockCapacity = myRs.getInt("dock_capacity");
				int dockBasicCharge = myRs.getInt("dock_basic_charge");
				int dockBasicDuration = myRs.getInt("dock_basic_duration");
				int dockExtraCharge = myRs.getInt("dock_extra_charge");
				String dockType = myRs.getString("dock_type");
				int dockRent = myRs.getInt("dock_rent");
				ArrayList<Ship> ships = (ArrayList<Ship>) ShipDAO.getShips(dockId);				


				dock = new Dock(dockId,dockName,dockCapacity,dockBasicCharge,dockBasicDuration,dockExtraCharge,dockType,dockRent,ships);
				
			}
							
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.closeConnection(myConn, myStmt, myRs);
		}
			
	

		return dock;
	}

	
	public static void addDock(Dock dock) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			
			myConn = DBUtil.getConnection();
			
			
			String sql = "insert into dock(dock_name,dock_capacity,dock_basic_charge,dock_basic_duration,dock_extra_charge,dock_type,dock_rent) values(?,?,?,?,?,?,?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setString(1, dock.getName());
			myStmt.setInt(2, dock.getCapacity());
			myStmt.setInt(3,dock.getBasicCharge());
			myStmt.setInt(4,dock.getBasicDuration());
			myStmt.setInt(5,dock.getExtraCharge());
			myStmt.setString(6,dock.getType());
			myStmt.setInt(7,dock.getRent());
			myStmt.execute();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
			
		
		finally {
			
			DBUtil.closeConnection(myConn, myStmt, null);
		}
		
	}

	public static void deleteDock(int dockId) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			
			myConn = DBUtil.getConnection();
			
			
			String sql = "delete from dock where dock_id=?";
			
			
			myStmt = myConn.prepareStatement(sql);
			
			
			myStmt.setInt(1, dockId);
			
			
			myStmt.execute();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		

		
		finally {
			// clean up JDBC code
			DBUtil.closeConnection(myConn, myStmt, null);
		}
		
	}
	
	public static void updateDock(Dock dock, int dockId) {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			
			myConn = DBUtil.getConnection();
			
			// create SQL update statement
			String sql = "update dock set dock_name=?,dock_capacity=?,dock_basic_charge=?,dock_basic_duration=?,dock_extra_charge=?,dock_type=?,dock_rent=? where dock_id=?";
			
			myStmt = myConn.prepareStatement(sql);
			
			
			myStmt.setString(1, dock.getName());
			myStmt.setInt(2, dock.getCapacity());
			myStmt.setInt(3,dock.getBasicCharge());
			myStmt.setInt(4,dock.getBasicDuration());
			myStmt.setInt(5,dock.getExtraCharge());
			myStmt.setString(6,dock.getType());
			myStmt.setInt(7,dock.getRent());
			myStmt.setInt(8,dockId);
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
