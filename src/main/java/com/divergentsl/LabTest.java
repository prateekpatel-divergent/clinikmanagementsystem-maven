package com.divergentsl;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LabTest {


    public static void labTestPanel() {

        Exit:
        while(true) {

            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();

            switch (input) {

                case "1":
                    addNewTest();
                    break;

                case "2":
                    readTest();
                    break;

                case "3":
                    listTest();
                    break;

                case "4":
                    deleteTest();
                    break;

                case "5":
                    updateTest();
                    break;

                case "6":
                    break Exit;

            }
        }
    }

    private static void updateTest() {

        System.out.println("\n----Update Test Data----");
        System.out.print("Enter Test Id; ");
        Scanner sc = new Scanner(System .in);

        String test_id = sc.nextLine();

        if(checkTest(test_id).size() != 0) {

            Map<String, String> map = checkTest(test_id);

            System.out.println("\n----Previous Data----");
            System.out.println("Previous Test Name: " + map.get("test_name"));
            System.out.println("Previous Patient Id: " + map.get("patient_id"));
            System.out.println("Previous Patient Name: " + map.get("patient_name"));
            System.out.println("Previous Contact Number: " + map.get("contact_number"));
            System.out.println("Previous Test Fee: " + map.get("test_fee"));


            System.out.print("\nEnter New Test Name: ");
            String test_name = sc.nextLine();

            System.out.print("\nEnter New Patient Id: ");
            String patient_id = sc.nextLine();

            System.out.print("\nEnter New Patient Name: ");
            String patient_name = sc.nextLine();

            System.out.print("\nEnter New Contact Number: ");
            String contact_number = sc.nextLine();

            System.out.print("\nEnter New Test Fee: ");
            String test_fee = sc.nextLine();

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(IDatabaseManager.url, IDatabaseManager.username, IDatabaseManager.password);
                Statement st = con.createStatement();

                st.executeUpdate("update lab_test set test_name = '" + test_name + "', patient_id = " + patient_id + ", contact_number = " + contact_number + ", test_fee = " + test_fee + " where test_id = " + test_id);
                st.close();
                con.close();
                System.out.println("\nTest Data Successfully Updated...");

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("\nTest Not Found!");
        }
    }

    private static void deleteTest(){

        System.out.print("\nEnter Test Id: ");
        Scanner sc = new Scanner(System.in);

        String test_id = sc.nextLine();

        if (checkTest(test_id).size() != 0) {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(IDatabaseManager.url, IDatabaseManager.username, IDatabaseManager.password);
                Statement st = con.createStatement();

                st.executeUpdate("delete from lab_test where test_id = " + test_id);
                st.close();
                con.close();
                System.out.println("\nTest Deleted Successfully...");

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        } else {
            System.out.println("\nTest Not Found!");
        }
    }

    private static Map<String, String> checkTest(String test_id) {

        Map<String, String> map = new HashMap<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(IDatabaseManager.url, IDatabaseManager.username, IDatabaseManager.password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from lab_test where test_id = " + test_id);

            if (rs.next()) {
                map.put("test_id", rs.getString(1));
                map.put("test_name", rs.getString(2));
                map.put("patient_id", rs.getString(3));
                map.put("patient_name", rs.getString(4));
                map.put("contact_number", rs.getString(5));
                map.put("test_fee", rs.getString(6));
            }

            st.close();
            con.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return map;
    }

    private static void listTest() {

        System.out.println("\n----List Tests----");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(IDatabaseManager.url, IDatabaseManager.username, IDatabaseManager.password);
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("select test_id, test_name, p.patient_id, p.patient_name, p.contact_number, test_fee from lab_test l join patient p on p.patient_id = l.patient_id");

            System.out.printf("%7s | %12s | %10s | %12s | %14s | %8s |", "Test Id", "Test Name", "Patient Id", "Patient Name", "Contact Number", "Test Fee");
            System.out.println("\n--------------------------------------------------------------------------------------------");
            while(rs.next()) {
                System.out.printf("%7s | %12s | %10s | %12s | %14s | %8s |\n", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
            }
            System.out.println("--------------------------------------------------------------------------------------------");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }


    private static void printTestOptions() {

        System.out.println("\n----Lab Test Panel----");
        System.out.println("1. Add New Test");
        System.out.println("2. Read Test");
        System.out.println("3. List All Tests");
        System.out.println("4. Delete Test");
        System.out.println("5. Update Test");
        System.out.println("6. Exit");
        System.out.print("Enter Your Choice: ");
    }


    private static void readTest() {

        System.out.println("\n----Read Lab Test----");
        System.out.print("Enter Test Id: ");

        Scanner sc = new Scanner(System.in);
        String test_id = sc.nextLine();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(IDatabaseManager.url, IDatabaseManager.username, IDatabaseManager.password);
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("select * from lab_test where test_id = " + test_id);

            System.out.println("Test id: " + test_id);
            System.out.println("Test Name: " + rs.getString(2));
            System.out.println("Patient Id: " + rs.getString(3));
            System.out.println("Test Fee: " + rs.getString(4));

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    private static void addNewTest() {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n----Adding Lab Test Data----");
        System.out.print("Enter Patient Id: ");
        String patient_id = sc.nextLine();

        System.out.print("\nEnter Test Name: ");
        String test_name = sc.nextLine();

        System.out.print("\nEnter Test Fee: ");
        String fee = sc.nextLine();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(IDatabaseManager.url, IDatabaseManager.username, IDatabaseManager.password);
            Statement st = con.createStatement();

            st.executeUpdate("insert into lab_test (test_name, patient_id, test-fee) values ('" + test_name + "', " + patient_id + ", " + fee + ")");
            System.out.println("Test Added Successfully");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}
