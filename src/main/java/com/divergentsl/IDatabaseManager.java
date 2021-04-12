package com.divergentsl;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDatabaseManager {

	
	String username = "cms";

	String password = "Div@1234";

	String url = "jdbc:mysql://localhost:3306/cms_db";

	public Connection getConnection() throws SQLException ;

}
