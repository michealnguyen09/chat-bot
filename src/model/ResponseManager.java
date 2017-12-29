package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class ResponseManager extends SQLAccess {
	
	private final String table_responses = "responses";
	private final String col_id = "id"; // max: 8 digits
	private final String col_response = "response"; // max: 16MiB
	
	private Random rand;
	private int responsesNum = 0;
	
	public ResponseManager() {
		super();
		
		rand = new Random();
		responsesNum = getRowsNum(table_responses);
	}
	
	public void addResponse(String response) {
		response = response.replaceAll("'", "\\\\" + "'");
		String query = "INSERT INTO " + table_responses
				+ " (" + col_response + ")"
				+ " VALUES ('" + response + "');";
		
		executeUpdate(query);
	}
	
	public void editResponse(int responseId, String response) {
		response = response.replaceAll("'", "\\\\" + "'");
		String query = "UPDATE " + table_responses
				+ " SET " + col_response + " = '" + response + "'"
				+ " WHERE " + col_id + " = " + responseId + ";";
		
		executeUpdate(query);
	}
	
	public String getResponse(int responseId) {
		String query = "SELECT " + col_response + " FROM " + table_responses
				+ " WHERE " + col_id + " = " + responseId + ";";
		
		String response = null;
		ResultSet result = executeQuery(query);
		try {
			result.next();
			response = result.getString(col_response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	public String getResponse() {
		int max = responsesNum;
		int min = 1;
		int responseId = rand.nextInt((max - min) + 1) + min;
		
		return getResponse(responseId);
	}
	
}
