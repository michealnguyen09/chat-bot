package util;

public interface SiteAddresses {
	
	// HTML Addresses
	final String INDEX = "/ChatBot";
	final String SIGN_UP = INDEX + "/signup";
	final String LOGIN = INDEX + "/login";
	final String ADD_RESPONSE = INDEX + "/addresponse";
	
	// Servlet Addresses
	final String CHAT_DO = INDEX + "/chat.do";
	
	// JSP Addresses
	final String CHAT_VIEW = "view/chatview.jsp";
	
}
