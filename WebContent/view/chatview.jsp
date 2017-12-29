<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title>ChatBot</title>
</head>
<body>
	<h1>A Little ChatBot</h1>
	<p><%
		Queue messages = (LinkedList)request.getAttribute("messages");
		while (!messages.isEmpty()) {
			out.println(messages.poll() + "<br>");
		}
	%></p>
	<form method="post" action="chat.do">
	<div>
		<input type="text" name="userInput"></input>
		<input type="submit"></input>
	</div>
	</form>
	<p><a href="/ChatBot/addresponse">Add response</a> | <a href="logout.do">Logout</a></p>
</body>
</html>