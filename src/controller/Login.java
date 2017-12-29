package controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UserManager;
import util.SiteAddresses;

public class Login extends HttpServlet implements SiteAddresses {
	private static final long serialVersionUID = 1L;
	
	private UserManager userManager;
	private String username;
	
	public Login() {
		super();
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		userManager = new UserManager();
		username = null;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		username = (String)request.getSession().getAttribute("username");
		
		if (username != null)
			response.sendRedirect(CHAT_DO);
		else response.sendRedirect(LOGIN);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		username = request.getParameter("username");
		
		if (userManager.checkLoginValid(username)) {
			request.getSession().setAttribute("username", username);
			response.sendRedirect(CHAT_DO);
		}
		else response.sendRedirect(LOGIN);
	}

}