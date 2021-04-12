package com.divergentsl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.divergentsl.dao.DoctorDao;

public class CRUDDoctor {

	public static void CRUDOperation() {
		CRUD: while (true) {

			Scanner sc = new Scanner(System.in);

			System.out.println("\n\n----Edit DOCTOR----");
			System.out.println("1. Insert a new doctor");
			System.out.println("2. List all doctors");
			System.out.println("3. Update doctor data");
			System.out.println("4. Remove doctor");
			System.out.println("5. Back");
			System.out.println("Enter your choice: ");
			String input = sc.nextLine();

			switch (input) {

			case "1":
				insertDoctor();
				break;

			case "2":
				listDoctors();
				break;

			case "3":
				updateDoctor();
				break;

			case "4":
				deleteDoctor();
				break;

			case "5":
				break CRUD;

			default:
				System.out.println("\nOperation not available");
				break;
			}
		}
	}

	public static void deleteDoctor() {

		System.out.println("\n----Remove Doctor----");
		System.out.print("Enter Doctor Id: ");
		Scanner sc = new Scanner(System.in);
		String doctor_id = sc.nextLine();

		DoctorDao doctorDao = new DoctorDao(new DatabaseManager());

		try {
			if (doctorDao.searchById(doctor_id).size() == 0) {
				System.out.println("\nDoctor not found!");
			} else {

				try {

					doctorDao.delete(doctor_id);
					System.out.println("\nData Deleted Successfully...");

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateDoctor() {

		System.out.println("\n----Update Doctor----");
		System.out.print("\nEnter Doctor Id : ");

		Scanner sc = new Scanner(System.in);

		String did = sc.nextLine();

		DoctorDao doctorDao = new DoctorDao(new DatabaseManager());
		
		Map<String, String> map = null;
		try {
			map = doctorDao.searchById(did);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (map==null || map.size() == 0) {
			System.out.println("\nDoctor not found!");
		} else {

			System.out.println("\n----Update Doctor Data----");
			System.out.println("Doctor Id : " + map.get("did"));
			System.out.println("Previous Doctor Name : " + map.get("dname"));
			System.out.println("Previous Doctor Speciality : " + map.get("speciality"));

			System.out.print("\nEnter New Doctor Name : ");
			map.put("dname", sc.nextLine());
			System.out.print("\nEnter New Doctor Speciality : ");
			map.put("speciality", sc.nextLine());

			try {
				doctorDao.update(map);
				System.out.println("Data Updated Successfully...");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	public static void listDoctors() {

		
		try {

			DoctorDao doctorDao = new DoctorDao(new DatabaseManager());
			
			List<Map<String, String>> doctorList = doctorDao.list();
			
			String did = "DoctorId";
			String dname = "Doctor Name";
			String speciality = "Speciality";

			System.out.printf("%10s%15s%15s\n", did, dname, speciality);
			System.out.println("------------------------------------------------");
			
			for (Map<String, String> aDoctor : doctorList) {
				System.out.printf("%10s%15s%15s\n", aDoctor.get(DoctorDao.ID), aDoctor.get(DoctorDao.NAME), aDoctor.get(DoctorDao.SPECIALITY));
			}
			
			System.out.println("-------------------------------------------------");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void insertDoctor() {
		Scanner sc = new Scanner(System.in);

		System.out.println("\n\n--Insert doctor details--");
		System.out.print("Enter Name: ");
		String dname = sc.nextLine();

		System.out.print("\nEnter Username: ");
		String username = sc.nextLine();

		System.out.print("\nEnter Password: ");
		String password = sc.nextLine();

		System.out.print("\nEnter Speciality: ");
		String speciality = sc.nextLine();

		DoctorDao doctorDao = new DoctorDao(new DatabaseManager());
		try {
			doctorDao.insert(dname, username, password, speciality);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	

}
