package controller;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.RevenueDAO;
import model.Revenue;


public class RevenueController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public RevenueController() {
        super();

    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String json = null;
		Gson gson= new Gson();
		json = gson.toJson(RevenueDAO.getDetails());
		response.setContentType("application/json");
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().println(json);

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}


	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] path = req.getPathInfo().split("/");
		int id;
		
		Gson gson= new Gson();
		if(path.length==2) {
			InputStreamReader reader = new InputStreamReader(req.getInputStream());
			id = Integer.parseInt(path[1]);
			Revenue revenue = gson.fromJson(reader, Revenue.class);
			RevenueDAO.updateRevenue(revenue,id);
			
			
		}
		
	}

}
