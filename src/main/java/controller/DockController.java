package controller;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.DockDAO;
import dao.RevenueDAO;
import dao.ShipDAO;
import model.Dock;
import model.Ship;

/**
 * Servlet implementation class DockController
 */
//@WebServlet("/DockController")
public class DockController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DockController() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] path = request.getPathInfo().split("/");
		String json = null;
	    int id;
		Gson gson= new Gson();
		if(path.length==0) {
			json = gson.toJson(DockDAO.getDocks());
		}
		if(path.length==2) {
	        id = Integer.parseInt(path[1]);
			json = gson.toJson(DockDAO.getDock(id));	
		}
		if(path.length==3) {
			id = Integer.parseInt(path[1]);
			json = gson.toJson(ShipDAO.getShips(id));
		}
		
		response.setContentType("application/json");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.getWriter().println(json);

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] path = request.getPathInfo().split("/");
		String json = null;
		int id;
		Gson gson= new Gson();
		if(path.length==0) {
			InputStreamReader reader = new InputStreamReader(request.getInputStream());
			Dock dock = gson.fromJson(reader, Dock.class);
			json = gson.toJson(dock);
			DockDAO.addDock(dock);
			RevenueDAO.updateRevenue(2);
		}
		if(path.length==3) {
			id = Integer.parseInt(path[1]);
			InputStreamReader reader = new InputStreamReader(request.getInputStream());
			Ship newShip = gson.fromJson(reader, Ship.class);
			json = gson.toJson(newShip);
			ShipDAO.addShip(newShip,id);	
			
		}
		response.setContentType("application/json");
		response.getWriter().println(json);

	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] path = req.getPathInfo().split("/");
		int id;
		if(path.length==2) {
			int dockId = Integer.parseInt(path[1]);
			ShipDAO.deleteShips(dockId);
			DockDAO.deleteDock(dockId);
			RevenueDAO.updateRevenue(2);
		}
		if(path.length==4) {
			id = Integer.parseInt(path[3]);
			ShipDAO.deleteShip(id);
			
		}
		
	}


	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String[] path = req.getPathInfo().split("/");
		int id;
		Gson gson= new Gson();
		
		if(path.length==2) {
			InputStreamReader reader = new InputStreamReader(req.getInputStream());
			
			id = Integer.parseInt(path[1]);
			Dock dock = gson.fromJson(reader, Dock.class);
			DockDAO.updateDock(dock,id);
			RevenueDAO.updateRevenue(2);	
		}
	}

}
