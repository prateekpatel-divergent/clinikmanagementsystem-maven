package com.divergentsl;

import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Admin {


    /**
     * This method takes two input username and password from console.
     * @return It return true if username and password is correct otherwise return false.
     */
    public static boolean adminLogin() {

        Scanner sc = new Scanner(System.in);

        Console cons = System.console();
        System.out.println("\n-----Admin Login------");
        System.out.print("\nEnter Username: ");
        String username = sc.nextLine();

        System.out.print("\nEnter Password: ");
        String password = sc.nextLine();

        Connection con = null;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(IDatabaseManager.url, IDatabaseManager.username, IDatabaseManager.password);

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("select * from admin where username = '" + username + "' && password = '" + password + "'");

            if(rs.next()) {
                System.out.println("Admin Login Successful");
                return true;
            } else {
                System.out.println("Incorrect Username & Password");
                return false;
            }

        } catch(Exception e) {
            System.out.println("error with database");
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Connection problem");
            }
        }
        return false;
    }


    /**
     * This method print all the operation that admin can perform, and ask for input for which operation admin want to perform, then after it redirect to that specific operation panel according to input.
     */
    public static void adminPanel() {
        LOGOUT:
        while(true) {

            printAdminOptions();
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();

            switch (input) {

                case "1":
                    CRUDPatient.CRUDOperation();
                    break;

                case "2":
                    CRUDDoctor.CRUDOperation();
                    break;

                case "3":
                    CRUDDrugs.CRUDOperations();
                    break;

                case "4":
                    LabTest.labTestPanel();
                    break;

                case "5":
                    PatientAppointment.makeAppointment();
                    break;

                case "6":
                    break LOGOUT;

                default:
                    System.out.println("\nInvalid Choice...");
                    System.out.println("Press enter to back");
                    sc.nextLine();
                    break;
            }
        }
    }


    /**
     * This method print operations that admin can perform
     */
    public static void printAdminOptions() {

        System.out.println("\n----Admin Panel-----");
        System.out.println("1. Patient");
        System.out.println("2. Doctor");
        System.out.println("3. Drug");
        System.out.println("4. Lab Test");
        System.out.println("5. Make appointment");
        System.out.println("6. Logout");
        System.out.print("Enter Your Choice: ");

    }






}
