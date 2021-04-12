package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import dbutil.DBUtil;
import model.Aeroplane;
import model.Apron;

public class ApronDAO {
	
public static ArrayList<Apron> getAprons(){
		
		ArrayList<Apron> aprons = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
			
			try {
				myConn = DBUtil.getConnection();
				String sql = "select * from apron";
				myStmt = myConn.createStatement();
				myRs = myStmt.executeQuery(sql);
	         
				while (myRs.next()) {
					
					int apronId = myRs.getInt("apron_id");
					String apronName = myRs.getString("apron_name");
					int apronCapacity = myRs.getInt("apron_capacity");
					int apronBasicCharge = myRs.getInt("apron_basic_charge");
					int apronBasicDuration = myRs.getInt("apron_basic_duration");
					int apronExtraCharge = myRs.getInt("apron_extra_charge");
					String apronType = myRs.getString("apron_type");
					int apronRent = myRs.getInt("apron_rent");
					ArrayList<Aeroplane> aeroplanes = (ArrayList<Aeroplane>) AeroplaneDAO.getAeroplanes(apronId);				
					Apron apron = new Apron(apronId,apronName,apronCapacity,apronBasicCharge,apronBasicDuration,apronExtraCharge,apronType,apronRent,aeroplanes);
					
					aprons.add(apron);			
	            }
				
			} catch (SQLException e) {
	
				 e.printStackTrace();
			}finally {
				DBUtil.closeConnection(myConn, myStmt, myRs);
			}
			

			return aprons;
	
}

	public static Apron getApron(int apronId) {
		
		Apron apron = null;
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;	
		
			
		
		try {
			myConn = DBUtil.getConnection();
			String sql = "select * from apron where apron_id=?";
		
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, apronId);
			
			myRs = myStmt.executeQuery();
			
			
			if (myRs.next()) {
				
				String apronName = myRs.getString("apron_name");
				int apronCapacity = myRs.getInt("apron_capacity");
				int apronBasicCharge = myRs.getInt("apron_basic_charge");
				int apronBasicDuration = myRs.getInt("apron_basic_duration");
				int apronExtraCharge = myRs.getInt("apron_extra_charge");
				String apronType = myRs.getString("apron_type");
				int apronRent = myRs.getInt("apron_rent");
				ArrayList<Aeroplane> aeroplanes = (ArrayList<Aeroplane>) AeroplaneDAO.getAeroplanes(apronId);				


				apron = new Apron(apronId,apronName,apronCapacity,apronBasicCharge,apronBasicDuration,apronExtraCharge,apronType,apronRent,aeroplanes);
			}
							
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.closeConnection(myConn, myStmt, myRs);
		}
			
		return apron;
		
	
	}

	
	public static void addApron(Apron apron) {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			
			myConn = DBUtil.getConnection();
			
			
			String sql = "insert into apron(apron_name,apron_capacity,apron_basic_charge,apron_basic_duration,apron_extra_charge,apron_type,apron_rent) values(?,?,?,?,?,?,?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setString(1, apron.getName());
			myStmt.setInt(2, apron.getCapacity());
			myStmt.setInt(3,apron.getBasicCharge());
			myStmt.setInt(4,apron.getBasicDuration());
			myStmt.setInt(5,apron.getExtraCharge());		
			myStmt.setString(6,apron.getType());
			myStmt.setInt(7,apron.getRent());
			
			myStmt.execute();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
			
		
		finally {
			
			DBUtil.closeConnection(myConn, myStmt, null);
		}



		
	}

	public static void deleteApron(int apronId) {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			
			myConn = DBUtil.getConnection();
			
			
			String sql = "delete from apron where apron_id=?";
			
			
			myStmt = myConn.prepareStatement(sql);
			
			
			myStmt.setInt(1, apronId);
			
			
			myStmt.execute();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		

		
		finally {
			// clean up JDBC code
			DBUtil.closeConnection(myConn, myStmt, null);
		}
		
	}
	
	public static void updateApron(Apron apron, int apronId) {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			
			myConn = DBUtil.getConnection();
			
			
			String sql = "update apron set apron_name=?,apron_capacity=?,apron_basic_charge=?,apron_basic_duration=?,apron_extra_charge=?,apron_type=?,apron_rent=? where apron_id=?";
			
			myStmt = myConn.prepareStatement(sql);
			
			
			myStmt.setString(1, apron.getName());
			myStmt.setInt(2, apron.getCapacity());
			myStmt.setInt(3,apron.getBasicCharge());
			myStmt.setInt(4,apron.getBasicDuration());
			myStmt.setInt(5,apron.getExtraCharge());
			myStmt.setString(6,apron.getType());
			myStmt.setInt(7,apron.getRent());
			myStmt.setInt(8,apronId);
			
			myStmt.execute();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			
			DBUtil.closeConnection(myConn, myStmt, null);
		}
		
	}

}
