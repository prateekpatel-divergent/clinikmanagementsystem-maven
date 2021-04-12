package com.divergentsl;

import java.sql.*;
import java.util.Scanner;

public class Doctor {

    public static void printDoctorOptions(String dname) {

        System.out.println("\n----Login as : " + dname + "----");
        System.out.println("----Doctor Panel----");
        System.out.println("1. List of patients");
        System.out.println("2. Add prescription and notes for a patient");
        System.out.println("3. See booked appointments for him");
        System.out.println("4. Check patient history and his prescription");
        System.out.println("5. Create Invoice of patient");
        System.out.println("6. Logout");
        System.out.print("Enter Your Choice: ");

    }


    private static boolean checkAppointment(String patient_id, String doctor_id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(IDatabaseManager.url, IDatabaseManager.username, IDatabaseManager.password);
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("select * from patient_appointment where patient_id = " + patient_id + " && doctor_id = " + doctor_id);
            st.close();
            con.close();
            if (rs.next()) {
                return true;
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void addPrescription() {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n----Add Prescription----");

        System.out.print("Enter patient_id: ");
        String patient_id = sc.nextLine();

        System.out.print("\nEnter Doctor_Id: ");
        String doctor_id = sc.nextLine();

        if (!checkAppointment(patient_id, doctor_id)) {
            System.out.println("Patient Appointment not found!");
        } else {

            System.out.print("\nEnter prescription: ");
            String prescription = sc.nextLine();

            System.out.print("\nEnter Notes: ");
            String notes = sc.nextLine();

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(IDatabaseManager.url, IDatabaseManager.username, IDatabaseManager.password);
                Statement st = con.createStatement();

                st.executeUpdate("insert into prescription (patient_id, prescription, notes, doctor_id) values (" + patient_id + ", '" + prescription + "', '" + notes + "', " + doctor_id + ")");

                st.close();
                con.close();

                System.out.println("\nPrescription added successfully...");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void allPatientList(String dname) {

        System.out.println("\n----Login as: " + dname + "----");
        System.out.println("----All patients----");

        Connection con = null;
        Statement st = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(IDatabaseManager.url, IDatabaseManager.username, IDatabaseManager.password);
            st = con.createStatement();

            ResultSet rs = st.executeQuery("select pa.appointment_number, pa.appointment_date, pa.time, p.patient_id, p.patient_name, p.gender, pa.problem, d.did, d.dname from patient p join patient_appointment pa on p.patient_id = pa.patient_id join doctor d on d.did = pa.doctor_id");

            System.out.println("----Login as " + dname + "----");
            System.out.println("----List of all patient----");
            System.out.printf("%18s   | %16s   | %8s   | %10s   | %12s   | %6s   | %20s   | %9s   | %15s   |","Appointment_Number", "Appointment_Date", "Time", "Patient_Id", "Patient_Name", "Gender", "Problem", "Doctor_Id", "Doctor_Name");
            System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------------------------------------");

            while(rs.next()) {
                System.out.printf("%18s   | %16s   | %8s   | %10s   | %12s   | %6s   | %20s   | %9s   | %15s   |\n",rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
            }

            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------");


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }


    public static void patientList(String did, String dname) {

        Connection con = null;
        Statement st = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(IDatabaseManager.url, IDatabaseManager.username, IDatabaseManager.password);
            st = con.createStatement();

            ResultSet rs = st.executeQuery("select pa.appointment_number, pa.patient_id, p.patient_name, pa.appointment_date, pa.time, pa.problem from doctor d join patient_appointment pa on pa.doctor_id = d.did join patient p on pa.patient_id = p.patient_id where d.did = " + did);
            System.out.println("----Login as " + dname + "----");
            System.out.println("----Patients Appoints to You----");
            System.out.printf("\n%18s   | %10s   | %12s   | %16s   | %9s   | %20s", "Appointment_Number", "Patient_Id", "Patient_Name","Appointment_Date","Time", "Problem");
            System.out.println("\n---------------------------------------------------------------------------------------------------------------");
            while(rs.next()) {

                System.out.printf("%18s   | %10s   | %12s   | %16s   | %9s   | %20s\n", rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));

            }
            System.out.println("---------------------------------------------------------------------------------------------------------------");


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }


    public static void doctorPanel(String did, String dname) {

        Logout:
        while(true) {

            printDoctorOptions(dname);

            Scanner sc = new Scanner(System.in);

            String input = sc.nextLine();

            switch (input) {

                case "1":allPatientList(dname);
                    break;

                case "2":
                    addPrescription();
                    break;

                case "3":
                    patientList(did, dname);
                    break;

                case "4":
                    CRUDPatient.checkPatientHistory();
                    break;

                case "5":
                    Invoice.generateInvoice();
                    break;

                case "6":
                    break Logout;

            }
        }
    }


    public static String doctorLogin() {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n----Doctor Login----");
        System.out.print("Enter Username: ");
        String username = sc.nextLine();
        System.out.print("\nEnter Password: ");
        String password = sc.nextLine();

        Connection con = null;
        Statement st = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(IDatabaseManager.url, IDatabaseManager.username, IDatabaseManager.password);
            st = con.createStatement();

            ResultSet rs = st.executeQuery("select * from doctor where username = '" + username + "' && password = '" + password + "'");

            if(rs.next()) {
                System.out.println("\nDoctor Login Successful");
                return rs.getString(1) + " " + rs.getString(2);
            } else {
                System.out.println("\nIncorrect Username & Password");
                return null;
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
