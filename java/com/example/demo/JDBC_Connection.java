package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC_Connection {
	public static Connection JDBC_connection() throws SQLException {
		String name1="root";
		String password1="password";
		String url1="jdbc:mysql://localhost:3306/batchProcessing?useSSL=false";
		Connection con1=DriverManager.getConnection(url1,name1,password1);
		return con1;
	}

}
