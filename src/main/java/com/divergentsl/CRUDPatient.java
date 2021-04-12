package com.divergentsl;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CRUDPatient {


    public static void updatePatient() {

        System.out.println("\n----Update Patient Data----");
        System.out.print("Enter Patient Id: ");
        Scanner sc = new Scanner(System.in);
        String patient_id = sc.nextLine();

        Map<String, String> map = getPatientData(patient_id);

        if(map.size() == 0) {
            System.out.println("\nPatient Not Found!");
            return;
        }

        System.out.println("\nPatient Id: " + patient_id);
        System.out.println("Patient Name: " + map.get("patient_name"));
        System.out.println("Patient Age: " + map.get("age"));
        System.out.println("Patient Weight: " + map.get("weight"));
        System.out.println("Patient Gender: " + map.get("gender"));
        System.out.println("Patient Contact Number: " + map.get("contact_number"));
        System.out.println("Patient Address: " + map.get("address"));

        System.out.print("\nEnter New Name: ");
        map.put("patient_name", sc.nextLine());
        System.out.print("\nEnter New Age: ");
        map.put("age", sc.nextLine());
        System.out.print("\nEnter New Weight: ");
        map.put("weight", sc.nextLine());
        System.out.print("\nEnter New Gender: ");
        map.put("gender", sc.nextLine());
        System.out.print("\nEnter New Contact Number: ");
        map.put("contact_number", sc.nextLine());
        System.out.print("\nEnter New Address: ");
        map.put("address", sc.nextLine());
        map.put("patient_id", patient_id);

        updateUtil(map);
    }

    private static void updateUtil(Map <String, String> map) {

        Connection con = null;
        Statement st = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(IDatabaseManager.url, IDatabaseManager.username, IDatabaseManager.password);
            st = con.createStatement();

            st.executeUpdate("update patient set patient_name = '" + map.get("patient_name") + "', age = " + map.get("age") + ", weight = " + map.get("weight") + ", gender = '" + map.get("gender") + "', contact_number = '" + map.get("contact_number") + "', address = '" + map.get("address") + "' where patient_id = " + map.get("patient_id"));

            System.out.println("\nData Update Successfully...");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> getPatientData(String patient_id) {

        Map<String, String> map = new HashMap<>();

        Connection con = null;
        Statement st = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(IDatabaseManager.url, IDatabaseManager.username, IDatabaseManager.password);
            st = con.createStatement();

            ResultSet rs = st.executeQuery("select * from patient where patient_id = " + patient_id);

            if(rs.next()) {
                map.put("patient_name", rs.getString(2));
                map.put("age", rs.getString(3));
                map.put("weight", rs.getString(4));
                map.put("gender", rs.getString(5));
                map.put("contact_number", rs.getString(6));
                map.put("address", rs.getString(7));
            }

            st.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static void deletePatient() {

        System.out.println("\n----Delete Patient Data----");
        System.out.print("Enter Patient Id: ");
        Scanner sc = new Scanner(System.in);
        String patient_id = sc.nextLine();

        Connection con = null;
        Statement st = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(IDatabaseManager.url, IDatabaseManager.username, IDatabaseManager.password);
            st = con.createStatement();

            st.executeUpdate("delete from patient where patient_id = " + patient_id);

            System.out.println("\nData Deleted Successfully...");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }


    public static void printPatientOptions() {
        System.out.println("\n\n----Patient Panel----");
        System.out.println("1. Add new patient");
        System.out.println("2. Update patient data");
        System.out.println("3. List all patient data");
        System.out.println("4. Search patient");
        System.out.println("5. Delete patient");
        System.out.println("6. Back");
        System.out.print("Enter your choice: ");
    }


    public static void CRUDOperation() {
        CRUD:
        while(true) {
            printPatientOptions();

            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();

            switch (input) {

                case "1":
                    addPatient();
                    break;

                case "2":
                    updatePatient();
                    break;

                case "3":
                    listPatient();
                    break;

                case "4":
                    searchPatient();
                    break;

                case "5":
                    deletePatient();
                    break;

                case "6":
                    break CRUD;

                default:
                    System.out.println("\nOperation not available");
                    break;
            }
        }
    }


    public static void checkPatientHistory() {

        Connection con = null;
        Statement st = null;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(IDatabaseManager.url, IDatabaseManager.username, IDatabaseManager.password);
            st = con.createStatement();

            System.out.println("\n----Check Patient History----");
            System.out.print("\nEnter Patient Id: ");
            String patient_id = new Scanner(System.in).nextLine();

            ResultSet rs = st.executeQuery("select p.patient_id, p.patient_name, pa.problem, pa.appointment_number, pa.appointment_date, pa.doctor_id, pp.prescription, pp.notes from patient_appointment pa join patient p on p.patient_id = pa.patient_id left join prescription pp on pp.appointment_number = pa.appointment_number where pa.patient_id = " + patient_id + " order by appointment_date desc");

            System.out.printf("\n%10s | %15s | %10s | %18s | %16s | %9s | %20s | %25s", "Patient Id", "Patient Name", "Problem", "Appointment Number", "Appointment Date", "Doctor Id", "Prescription", "Notes");
            System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------------------------");

            while(rs.next()) {
                System.out.printf("\n%10s | %15s | %10s | %18s | %16s | %9s | %20s | %25s", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
            }

            System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------------------------");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }


    public static Map inputPatientData() {
        Scanner sc = new Scanner(System.in);

        Map<String, String> map = new HashMap<>();

        System.out.println("\n----Add Patient----");
        System.out.print("Enter Patient Name: ");
        String patient_name = sc.nextLine();
        map.put("patient_name", patient_name);

        System.out.print("\nEnter Age: ");
        String age = sc.nextLine();
        map.put("age", age);

        System.out.print("\nEnter Weight: ");
        String weight = sc.nextLine();
        map.put("weight", weight);

        System.out.print("\nEnter Gender: ");
        String gender = sc.nextLine();
        map.put("gender", gender);

        System.out.print("\nEnter Contact Number: ");
        String contact_number = sc.nextLine();
        map.put("contact_number", contact_number);

        System.out.print("\nEnter Address: ");
        String address = sc.nextLine();
        map.put("address", address);

        return map;
    }


    public static void addPatient() {

        Scanner sc = new Scanner(System.in);

        Map<String, String> map = inputPatientData();

        Connection con = null;
        Statement st = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(IDatabaseManager.url, IDatabaseManager.username, IDatabaseManager.password);
            st = con.createStatement();

            st.executeUpdate("insert into patient (patient_name, age, weight, gender, contact_number, address) values " +
                    "('" + map.get("patient_name") + "', " + map.get("age") + ", " + map.get("weight") + ", '" + map.get("gender") + "', " + map.get("contact_number") + ", '" + map.get("address") + "')");

            System.out.println("\nData Successfully inserted...");
            System.out.println("Press Enter to Continue...");
            sc.nextLine();

            st.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    public static void searchPatient() {

        Connection con = null;
        Statement st = null;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(IDatabaseManager.url, IDatabaseManager.username, IDatabaseManager.password);
            st = con.createStatement();

            Scanner sc = new Scanner(System.in);

            System.out.println("\n\n----Search Patient----");
            System.out.print("\nEnter Patient Id: ");
            String pid = sc.nextLine();

            ResultSet rs = st.executeQuery("select * from patient where patient_id = " + pid);

            if (rs.next()) {
                System.out.println("\nPatient Id : " + rs.getString(1));
                System.out.println("Patient Name : " + rs.getString(2));
                System.out.println("Age : " + rs.getString(3));
                System.out.println("Weight : " + rs.getString(4));
                System.out.println("Gender : " + rs.getString(5));
                System.out.println("Contact Number : " + rs.getString(6));
                System.out.println("Address : " + rs.getString(7));
            } else {
                System.out.println("\n\nPatient data not found!");
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    public static void listPatient() {

        Connection con = null;
        Statement st = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(IDatabaseManager.url, IDatabaseManager.username, IDatabaseManager.password);
            st = con.createStatement();

            ResultSet rs = st.executeQuery("select * from patient");

            System.out.println("\n\n----Patient List----\n");
            System.out.printf("%10s  %15s     %3s     %6s     %6s   %14s  %15s\n","Patient_Id", "Patient_Name", "Age", "Weight", "Gender", "Contact_Number", "Address");
            System.out.println("----------------------------------------------------------------------------------------------------------------");
            while(rs.next()) {
                String pid = rs.getString(1);
                String pname = rs.getString(2);
                String page = rs.getString(3);
                String pweight = rs.getString(4);
                String pgender = rs.getString(5);
                String pcontact = rs.getString(6);
                String paddress = rs.getString(7);
                System.out.printf("%10s  %15s     %3s     %6s     %6s   %14s  %15s\n", pid, pname, page, pweight, pgender, pcontact, paddress);
            }
            System.out.println("----------------------------------------------------------------------------------------------------------------");

            System.out.println("Press enter to back...");
            new Scanner(System.in).nextLine();

            st.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


}
