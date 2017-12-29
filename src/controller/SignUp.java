package controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MessageManager;
import model.UserManager;
import util.ConstMessages;
import util.SiteAddresses;

public class SignUp extends HttpServlet implements SiteAddresses, ConstMessages {
	private static final long serialVersionUID = 1L;
	
	private UserManager userManager;
	private MessageManager msgManager;
	private String username;

	public SignUp() {
		super();
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		userManager = new UserManager();
		msgManager = new MessageManager();
		username = null;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		username = (String)request.getSession().getAttribute("username");
		
		if (username != null)
			response.sendRedirect(CHAT_DO);
		else response.sendRedirect(SIGN_UP);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		username = request.getParameter("username");
		
		if (userManager.checkSignUpValid(username)) {
			userManager.addUser(username);
			long userId = userManager.getUserId(username);
			msgManager.saveMessage(userId, USER_FIRST_MESSAGE, BOT_FIRST_MESSAGE);
			
			request.getSession().setAttribute("username", username);
			response.sendRedirect(CHAT_DO);
		}
		else response.sendRedirect(SIGN_UP);
	}
	
}
