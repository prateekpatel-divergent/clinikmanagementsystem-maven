package com.divergentsl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager implements IDatabaseManager{

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			System.exit(100);
		}
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(IDatabaseManager.url, IDatabaseManager.username, IDatabaseManager.password);

	}
}
