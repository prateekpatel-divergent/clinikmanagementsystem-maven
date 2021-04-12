package com.divergentsl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PatientAppointment {


    /**
     * This method take patient and take the doctor_id that which doctor he is suitable according to patient problem.
     */
    public static void makeAppointment() {

        Map<String, String> map = takeAppointmentInput();

        Connection con = null;
        Statement st = null;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(IDatabaseManager.url, IDatabaseManager.username, IDatabaseManager.password);
            st = con.createStatement();

            st.executeUpdate("insert into patient_appointment (patient_id, doctor_id, problem, appointment_date, time) values(" + map.get("patient_id") + ", " + map.get("doctor_id") + ", '" + map.get("problem") + "', '" + map.get("appointment_date") + "', '" + map.get("time") + "')");
            st.close();
            con.close();
            System.out.println("\nAppointment data successfully inserted...");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method helper method for makeAppointment()
     * It takes input and return Map of input data.
     * @return Map
     */
    private static Map<String, String> takeAppointmentInput() {

        Map<String, String> map = new HashMap<>();
        Scanner sc = new Scanner(System.in);

        System.out.println("\n\n----Make Appointment----");
        System.out.print("Enter Patient_Id : ");
        String patient_id = sc.nextLine();
        map.put("patient_id", patient_id);

        System.out.print("\nEnter Doctor Id : ");
        String doctor_id = sc.nextLine();
        map.put("doctor_id", doctor_id);

        System.out.print("\nEnter Patient Problem : ");
        String problem = sc.nextLine();
        map.put("problem", problem);

        System.out.print("\nEnter Appointment Date : ");
        String appointment_date = sc.nextLine();
        map.put("appointment_date", appointment_date);

        System.out.print("Appointment Time : ");
        String appointment_time = sc.nextLine();
        map.put("time", appointment_time);

        return map;
    }

}
