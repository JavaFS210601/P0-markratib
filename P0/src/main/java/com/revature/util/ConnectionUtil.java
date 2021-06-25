package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil 
{
	public static Connection getConnection() throws SQLException
	{
		try 
		{
			Class.forName("org.postgresql.Driver");
			
		}catch(ClassNotFoundException e) {
			System.out.println("Class wasn't found :(");
			e.printStackTrace();//print the exception message to the console
		}
		//We need to provide our database credentials
		String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=project0";
		String username = "postgres";
		String password = "password";
		
		return DriverManager.getConnection(url, username, password);
	}
}
