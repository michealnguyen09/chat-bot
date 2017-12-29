package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import util.ConstMessages;

public class UserManager extends SQLAccess implements ConstMessages {

	private final String table_users_data = "users_data";
	private final String col_id = "id"; // max: 5 digits
	private final String col_username = "username"; // max: 50 digits
	private final String col_msg_count = "msg_count"; // max: 7 digits
	
	public UserManager() {
		super();
	}
	
	public void addUser(String username) {
		username = username.replaceAll("'", "\\\\" + "'");
		String query = "INSERT INTO " + table_users_data
				+ " (" + col_username + ")"
				+ " VALUES ('" + username + "');";
		
		executeUpdate(query);
	}
	
	public void setUsername(long userId, String username) {
		username = username.replaceAll("'", "\\\\" + "'");
		String query = "UPDATE " + table_users_data
				+ " SET " + col_username + " = '" + username + "'"
				+ " WHERE " + col_id + " = " + userId + ";";
		
		executeUpdate(query);
	}
	
	public String getUsername(long userId) {
		String query = "SELECT " + col_username + " FROM " + table_users_data
				+ " WHERE " + col_id + " = " + userId + ";";
		
		String username = null;
		ResultSet result = executeQuery(query);
		try {
			result.next();
			username = result.getString(col_username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return username;
	}
	
	public int getUserId(String username) {
		username = username.replaceAll("'", "\\\\" + "'");
		String query = "SELECT " + col_id + " FROM " + table_users_data
				+ " WHERE " + col_username + " = '" + username + "';";
		
		int userId = -1;
		ResultSet result = executeQuery(query);
		try {
			result.next();
			userId = result.getInt(col_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userId;
	}
	
	public void setMsgCount(long userId, int msgCount) {
		String query = "UPDATE " + table_users_data
				+ " SET " + col_msg_count + " = " + msgCount
				+ " WHERE " + col_id + " = " + userId + ";";
		
		executeUpdate(query);
	}
	
	public int getMsgCount(long userId) {
		String query = "SELECT " + col_msg_count + " FROM " + table_users_data
				+ " WHERE " + col_id + " = " + userId + ";";
		
		int msgCount = -1;
		ResultSet result = executeQuery(query);
		try {
			result.next();
			msgCount = result.getInt(col_msg_count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return msgCount;
	}
	
	public boolean checkSignUpValid(String username) {
		if (username == null || username.trim().isEmpty()) {
			// notify: username cannot be empty
			return false;
		}
		if (getUserId(username) != -1) {
			// notify: username already exists
			return false;
		}
		return true;
	}
	
	public boolean checkLoginValid(String username) {
		if (username == null || username.trim().isEmpty()) {
			// notify: username cannot be empty
			return false;
		}
		if (getUserId(username) == -1) {
			// notify: username doesn't exist
			return false;
		}
		return true;
	}
	
}
