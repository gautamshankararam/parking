package controller;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.AreaDAO;
import dao.CarDAO;
import dao.RevenueDAO;
import model.Car;
import model.ParkingArea;



public class AreaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public AreaController() {
        super();
    
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] path = request.getPathInfo().split("/");
		String json = null;
		int id;
		Gson gson= new Gson();
		if(path.length==0) {
			json = gson.toJson(AreaDAO.getParkingAreas());
		}
		if(path.length==2) {
	        id = Integer.parseInt(path[1]);
			json = gson.toJson(AreaDAO.getArea(id));	
		}
		if(path.length==3) {
			id = Integer.parseInt(path[1]);
			json = gson.toJson(CarDAO.getCars(id));
		}
		response.setContentType("application/json");
		response.setHeader("Access-Control-Allow-Origin", "*");
//      response.setHeader("Access-Control-Allow-Credentials","true");
//		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
//		response.setHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
//      response.setHeader("Access-Control-Max-Age", "1728000");
			 
		response.getWriter().println(json);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] path = request.getPathInfo().split("/");
		String json = null;
		int id;
		Gson gson= new Gson();
		if(path.length==0) {
			InputStreamReader reader = new InputStreamReader(request.getInputStream());
			ParkingArea area = gson.fromJson(reader, ParkingArea.class);
			json = gson.toJson(area);
			AreaDAO.addParkingArea(area);
			RevenueDAO.updateRevenue(1);
		}
		if(path.length==3) {
			id = Integer.parseInt(path[1]);
			InputStreamReader reader = new InputStreamReader(request.getInputStream());
			Car newCar = gson.fromJson(reader, Car.class);
			json = gson.toJson(newCar);
			CarDAO.addCar(newCar,id);	
			
		}
		response.setContentType("application/json");
		response.getWriter().println(json);
		
	
		
	}


	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] path = req.getPathInfo().split("/");
		int id;
		if(path.length==2) {
			int areaId = Integer.parseInt(path[1]);
			CarDAO.deleteCars(areaId);
			AreaDAO.deleteArea(areaId);
			RevenueDAO.updateRevenue(1);
		}
		if(path.length==4) {
			id = Integer.parseInt(path[3]);
			CarDAO.deleteCar(id);
			
		}
		
	}


	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String[] path = req.getPathInfo().split("/");
		int id;
		Gson gson= new Gson();
		if(path.length==2) {
			InputStreamReader reader = new InputStreamReader(req.getInputStream());
			id = Integer.parseInt(path[1]);
			ParkingArea area=gson.fromJson(reader, ParkingArea.class);
			AreaDAO.updateArea(area,id);
			RevenueDAO.updateRevenue(1);
		}
		

	}

}
