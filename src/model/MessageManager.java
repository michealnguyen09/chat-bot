package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Queue;

public class MessageManager extends SQLAccess {

	private final String table_messages = "messages";
	private final String col_id = "id"; // max: 19 digits
	private final String col_username = "username"; // max: 50 digits
	private final String col_user_message = "user_message"; // max: 16MiB
	private final String col_bot_response = "bot_response"; // max: 16MiB
	
	private UserManager userManager;
	
	public MessageManager() {
		super();
		userManager = new UserManager();
	}
	
	public void saveMessage(long userId, String message, String response) {
		String username = userManager.getUsername(userId);
		message = message.replaceAll("'", "\\\\" + "'");
		response = response.replaceAll("'", "\\\\" + "'");
		
		String query = "INSERT INTO " + table_messages
				+ " (" + col_id + ", " + col_username + ", " + col_user_message + ", " + col_bot_response + ")"
				+ " VALUES (" + generateId(userId) + ", '" + username + "', '" + message + "', '" + response + "');";
		
		executeUpdate(query);
		increaseMsgCount(userId);
	}
	
	public Queue<String> getMessage(String username) {
		username = username.replaceAll("'", "\\\\" + "'");
		String query = "SELECT " + col_user_message + ", " + col_bot_response + " FROM " + table_messages
				+ " WHERE " + col_username + " = '" + username + "';";
		
		Queue<String> messages = new LinkedList<>();
		ResultSet result = executeQuery(query);
		try {
			while(result.next()) {
				messages.add(result.getString(col_user_message));
				messages.add(result.getString(col_bot_response));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return messages;
	}
	
	public String formatMessage(String sender, String message) {
		LocalTime now = LocalTime.now();
        String time = now.getHour() + ":" + now.getMinute();
        
        return "- " + sender + " (" + time + "): " + message;
	}
	
	private void increaseMsgCount(long userId) {
		userManager.setMsgCount(userId, userManager.getMsgCount(userId) + 1);
	}
	
	private String generateId(long userId) {
		String strDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		String strTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
		
		return userId + strDate + strTime;
	}
	
}
