package com.divergentsl.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.divergentsl.IDatabaseManager;

public class DoctorDao {

	public static final String SPECIALITY = "speciality";
	public static final String NAME = "name";
	public static final String ID = "id";
	
	IDatabaseManager databaseManager;

	public DoctorDao(IDatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}

	public void delete(String doctor_id) throws SQLException {
		Connection con = null;
		Statement st = null;

		con = databaseManager.getConnection();
		st = con.createStatement();

		st.executeUpdate("delete from doctor where did = " + doctor_id);

		st.close();
		con.close();

	}

	public void update(Map<String, String> map) throws SQLException {
		Connection con;
		Statement st;
		con = databaseManager.getConnection();
		st = con.createStatement();

		st.executeUpdate("update doctor set dname = '" + map.get("dname") + "', speciality = '" + map.get(SPECIALITY)
				+ "' where did = " + map.get("did"));

		st.close();
		con.close();
	}

	public Map searchById(String did) throws SQLException {

		Connection con = null;
		Statement st = null;
		Map<String, String> map = new HashMap<>();

		con = databaseManager.getConnection();
		st = con.createStatement();

		ResultSet rs = st.executeQuery("select did, dname, speciality from doctor where did = '" + did + "'");

		if (rs.next()) {
			map.put("did", rs.getString(1));
			map.put("dname", rs.getString(2));
			map.put(SPECIALITY, rs.getString(3));
			st.close();
			con.close();

			return map;
		}

		return map;

	}

	/**
	 * It is a helper method for inserting doctor data
	 * 
	 * @param dname
	 * @param username
	 * @param password
	 * @param speciality
	 * @return true if successfully inserted otherwise it return false
	 * @throws SQLException
	 */
	public void insert(String dname, String username, String password, String speciality) throws SQLException {

		Connection con = null;
		Statement st = null;

		con = databaseManager.getConnection();
		st = con.createStatement();

		st.executeUpdate("insert into doctor (dname, username, password, speciality) " + "values ('" + dname + "', '"
				+ username + "', '" + password + "', '" + speciality + "')");
		System.out.println("\nData inserted successfully...");

		st.close();
		con.close();

	}

	public List<Map<String, String>> list() throws SQLException {

		Connection con = null;
		Statement st = null;

		con = databaseManager.getConnection();
		st = con.createStatement();

		ResultSet rs = st.executeQuery("select did, dname, speciality from doctor");

		List<Map<String, String>> doctorList = new ArrayList<>();

		while (rs.next()) {
			Map<String, String> aDoctorRecord = new HashMap<>();
			aDoctorRecord.put(ID, rs.getString(1));
			aDoctorRecord.put(NAME, rs.getString(2));
			aDoctorRecord.put(SPECIALITY, rs.getString(3));
			doctorList.add(aDoctorRecord);
		}
		
		return doctorList;

	}

}
