package controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ResponseManager;
import util.SiteAddresses;

public class AddResponse extends HttpServlet implements SiteAddresses {
	private static final long serialVersionUID = 1L;
	
	private ResponseManager respManager;

	public AddResponse() {
		super();
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		respManager = new ResponseManager();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String)request.getSession().getAttribute("username");
		
		if (username != null)
			response.sendRedirect(ADD_RESPONSE);
		else response.sendRedirect(LOGIN);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String newResponse = request.getParameter("newResponse");
		
		if (!newResponse.trim().isEmpty()) {
			respManager.addResponse(newResponse);
			// notify: response added
			
			response.sendRedirect(ADD_RESPONSE);
		}
		else response.sendRedirect(ADD_RESPONSE);
	}

}
