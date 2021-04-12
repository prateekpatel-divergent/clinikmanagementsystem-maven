package com.divergentsl;

import java.sql.*;
import java.util.Scanner;

public class Invoice {

    public static void generateInvoice() {

        System.out.print("\nEnter Patient Id: ");
        String patient_id = new Scanner(System.in).nextLine();
        createInvoice(patient_id);

    }

    private static void createInvoice(String patient_id) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(IDatabaseManager.url, IDatabaseManager.username, IDatabaseManager.password);
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("select pa.patient_id, p.patient_name, d.dname, d.fee,pa.problem, pa.appointment_date from prescription pp join patient_appointment pa on pa.appointment_number = pp.appointment_number join patient p on p.patient_id = pa.patient_id join doctor d on d.did = pa.doctor_id where pa.patient_id = " + patient_id + " order by pa.appointment_date desc");

            if (rs.next()) {
                System.out.println("\n--------------------Invoice--------------------");
                System.out.printf("%12s : %10s \t %10s : %4s\n", "Patient Name", rs.getString(2),"Patient Id", rs.getString(1));
                System.out.printf("%12s : %10s \t %10s : %10s\n", "Doctor Name", rs.getString(3),"Problem", rs.getString(5));
                System.out.printf("%12s : %10s \t %10s : %4s rs\n", "Date", rs.getString(6),"Total", rs.getString(4));
                System.out.println("-----------------------------------------------");
            } else {
                System.out.println("\nPatient not found!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}
