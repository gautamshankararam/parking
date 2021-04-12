package controller;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.AeroplaneDAO;
import dao.ApronDAO;
import dao.RevenueDAO;
import model.Aeroplane;
import model.Apron;

/**
 * Servlet implementation class ApronController
 */
//@WebServlet("/ApronController")
public class ApronController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ApronController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] path = request.getPathInfo().split("/");
		String json = null;
		int id;
		Gson gson= new Gson();
		if(path.length==0) {
			json = gson.toJson(ApronDAO.getAprons());
		}
		if(path.length==2) {
	        id = Integer.parseInt(path[1]);
			json = gson.toJson(ApronDAO.getApron(id));	
		}
		if(path.length==3) {
			id = Integer.parseInt(path[1]);
			json = gson.toJson(AeroplaneDAO.getAeroplanes(id));
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
			Apron apron = gson.fromJson(reader, Apron.class);
			json = gson.toJson(apron);
			ApronDAO.addApron(apron);
			RevenueDAO.updateRevenue(3);
		}
		
		if(path.length==3) {
			id = Integer.parseInt(path[1]);
			InputStreamReader reader = new InputStreamReader(request.getInputStream());
			Aeroplane newAeroplane = gson.fromJson(reader, Aeroplane.class);
			json = gson.toJson(newAeroplane);
			AeroplaneDAO.addAeroplane(newAeroplane,id);	
			
		}
		response.setContentType("application/json");
		response.getWriter().println(json);


	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] path = req.getPathInfo().split("/");
		int id;
		if(path.length==2) {
			int apronId = Integer.parseInt(path[1]);
			AeroplaneDAO.deleteAeroplanes(apronId);
			ApronDAO.deleteApron(apronId);
			RevenueDAO.updateRevenue(3);
		}
		if(path.length==4) {
			id = Integer.parseInt(path[3]);
			AeroplaneDAO.deleteAeroplane(id);
			
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
			Apron apron = gson.fromJson(reader, Apron.class);
			ApronDAO.updateApron(apron,id);
			RevenueDAO.updateRevenue(3);
		}
	}

	

}
