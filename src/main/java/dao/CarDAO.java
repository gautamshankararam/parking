package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import dbutil.DBUtil;
import model.Car;

public class CarDAO {

    public static ArrayList<Car> getCars(int areaId) {
        ArrayList<Car> cars = new ArrayList<>();
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
			
			try {
				myConn = DBUtil.getConnection();
				String sql = "select * from car where area_id=?";
				myStmt = myConn.prepareStatement(sql);
				myStmt.setInt(1, areaId);
				
				myRs = myStmt.executeQuery();
				while (myRs.next()) {
					
	            	int carId = myRs.getInt("car_id");
					String carName = myRs.getString("car_name");
					String areaName = myRs.getString("area_name");
					int carParkingDuration = myRs.getInt("car_parking_duration");
					int carParkingCost = myRs.getInt("car_parking_cost");
					Car car = new Car(carId,carName,areaId,areaName,carParkingDuration,carParkingCost);
					cars.add(car);			
	            }
				
			} catch (SQLException e) {
	
				 e.printStackTrace();
			}finally {
				DBUtil.closeConnection(myConn, myStmt, myRs);
			}
			

			return cars;

		
	}
	
	
	
	public static void addCar(Car car,int areaId) {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			
			myConn = DBUtil.getConnection();
			
			String sql = "insert into car(car_name,area_id,area_name,car_parking_duration,car_parking_cost) values(?,?,?,?,?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			
			myStmt.setString(1, car.getName());
			myStmt.setInt(2, areaId);
			myStmt.setString(3, car.getAreaName());
			myStmt.setInt(4, car.getParkingDuration());
			myStmt.setInt(5, car.getParkingCost());
			
			myStmt.execute();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
			
		
		finally {
			
			DBUtil.closeConnection(myConn, myStmt, null);
		}
		
	}

	public static void deleteCar(int carId) {
	    Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			
			myConn = DBUtil.getConnection();
			
			
			String sql = "delete from car where car_id=?";
			
			
			myStmt = myConn.prepareStatement(sql);
			
			
			myStmt.setInt(1, carId);
			
			
			myStmt.execute();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		

		
		finally {
			// clean up JDBC code
			DBUtil.closeConnection(myConn, myStmt, null);
		}
		
		
	}
	
public static void deleteCars(int areaId) {
		
		ArrayList<Car> cars = CarDAO.getCars(areaId);
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			
			myConn = DBUtil.getConnection();
			
			
			for(int i=0;i<cars.size();i++) {
				
				String sql = "delete from car where car_id=?";
				
				myStmt = myConn.prepareStatement(sql);
				
				
				myStmt.setInt(1, cars.get(i).getId());
				
				
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


//	public static void updateCar(Car newCar, int id) {
//		Connection myConn = null;
//		PreparedStatement myStmt = null;
//
//		try {
//			
//			myConn = DBUtil.getConnection();
//			
//			// create SQL update statement
//			String sql = "update car set car_id=?, car_name=?, area_id=? where car_id=?";
//			
//			myStmt = myConn.prepareStatement(sql);
//			
//			
//			myStmt.setInt(1, id);
//			myStmt.setString(2, newCar.getName());
//			myStmt.setInt(3, newCar.getAreaId());
//			myStmt.setInt(4,id);
//			
//			// execute SQL statement
//			myStmt.execute();
//		}catch (SQLException e) {
//			e.printStackTrace();
//		}
//		finally {
//			// clean up JDBC objects
//			DBUtil.closeConnection(myConn, myStmt, null);
//		}
//		
//	}
//
	
	

	

}
