package controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MessageManager;
import model.ResponseManager;
import model.UserManager;
import util.SiteAddresses;

public class Chat extends HttpServlet implements SiteAddresses {
	private static final long serialVersionUID = 1L;
	
	private UserManager userManager;
	private ResponseManager respManager;
	private MessageManager msgManager;
	
	private String username;
	private String botName;
	private Queue<String> messages;
	
	public Chat() {
		super();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		userManager = new UserManager();
		respManager = new ResponseManager();
		msgManager = new MessageManager();
		
		username = null;
		botName = "BOT";
		messages = new LinkedList<>();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		username = (String)request.getSession().getAttribute("username");
		
		if (username != null) {
			messages = msgManager.getMessage(username);
			request.setAttribute("messages", messages);
			
			RequestDispatcher chatView = request.getRequestDispatcher(CHAT_VIEW);
			chatView.forward(request, response);
		}
		else response.sendRedirect(LOGIN);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		username = (String)request.getSession().getAttribute("username");
		String userInput = request.getParameter("userInput");
		
		String userMsg = msgManager.formatMessage(username, userInput);
		String botMsg = msgManager.formatMessage(botName, respManager.getResponse());
		
		long userId = userManager.getUserId(username);
		msgManager.saveMessage(userId, userMsg, botMsg);
		
		messages = msgManager.getMessage(username);
		request.setAttribute("messages", messages);
		
		RequestDispatcher chatView = request.getRequestDispatcher(CHAT_VIEW);
		chatView.forward(request, response);
	}
	
}
