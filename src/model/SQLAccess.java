package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

class SQLAccess {

	private final String username = "root";
	private final String password = "";
	
	private Connection connection;
	
	SQLAccess() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		connection = getConnection();
	}
	
	private Connection getConnection() {
		Properties connectionProps = new Properties();
		connectionProps.put("user", username);
		connectionProps.put("password", password);
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chatbot", connectionProps);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	ResultSet executeQuery(String query) {
		Statement statement = null;
		
		ResultSet result = null;
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	void executeUpdate(String query) {
		Statement statement = null;
		
		try {
			statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	int getRowsNum(String table) {
		final String col_rows_num = "rows_num";
		String query = "SELECT COUNT(*) AS " + col_rows_num + " FROM " + table + ";";
		
		int rowsNum = 0;
		ResultSet result = executeQuery(query);
		try {
			result.next();
			rowsNum = result.getInt(col_rows_num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowsNum;
	}
	
}
