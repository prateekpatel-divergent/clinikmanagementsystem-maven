package com.divergentsl;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CRUDDrugs {

    /**
     * This method takes input from user and redirect it to the operation that he wants to perform.
     */
    public static void CRUDOperations() {
        CRUD:
        while(true) {

            System.out.println("\n\n----Drugs----");
            System.out.println("1. Add new drugs");
            System.out.println("2. Search drugs");
            System.out.println("3. Delete drugs");
            System.out.println("4. Update drugs");
            System.out.println("5. Back");
            System.out.print("Enter your choice: ");

            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();

            switch (input) {
                case "1":
                    addDrug();
                    break;

                case "2":
                    searchDrug();
                    break;

                case "3":
                    deleteDrug();
                    break;

                case "4":
                    updateDrug();
                    break;

                case "5":
                    break CRUD;

                default:
                    System.out.println("\nOperation not available, Press enter to continue...");
                    sc.nextLine();
                    break;
            }
        }
    }


    /**
     * This method update the existing data of drugs
     */
    public static void updateDrug() {

        System.out.println("\n----Update Drug Data----");
        System.out.print("Enter Drug Id: ");
        Scanner sc = new Scanner(System.in);
        String drug_id = sc.nextLine();
        Map<String, String> map = getDrugData(drug_id);

        if (map.size() == 0) {
            System.out.println("\nDrug not found!");
        } else {
            System.out.println("\nDrug Id: " + drug_id);
            System.out.println("Previous Drug Name: " + map.get("drug_name"));
            System.out.println("Previous Drug Description: " + map.get("description"));

            System.out.println("\nEnter New Drug Name: ");
            String drug_name = sc.nextLine();

            System.out.print("\nEnter New Description: ");
            String description = sc.nextLine();

            updateDrugUtil(drug_id, drug_name, description);
        }
    }

    /**
     * This method is helper method for updating drug data.
     * It takes three input.
     * @param drug_id
     * @param drug_name
     * @param description
     * Update the specific row according to input.
     */
    private static void updateDrugUtil(String drug_id, String drug_name, String description) {

        Connection con = null;
        Statement st = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(IDatabaseManager.url, IDatabaseManager.username, IDatabaseManager.password);
            st = con.createStatement();

            st.executeUpdate("update drug set drug_name = '" + drug_name + "', description = '" + description + "' where drug_id = " + drug_id);
            System.out.println("\nData update successfully...");
            st.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is helper method for update data,
     * It takes single input `drug_id` and return data of Map of input drug_id.
     * @param drug_id
     * @return Map
     */
    public static Map<String, String> getDrugData(String drug_id) {

        Map<String, String> map = new HashMap<>();

        Connection con = null;
        Statement st = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(IDatabaseManager.url, IDatabaseManager.username, IDatabaseManager.password);
            st = con.createStatement();

            ResultSet rs = st.executeQuery("select * from drug where drug_id = " + drug_id);

            if(rs.next()) {
                map.put("drug_id", drug_id);
                map.put("drug_name", rs.getString(2));
                map.put("description", rs.getString(3));
                return map;
            }

            st.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * This method takes drug_id and remove it from database.
     */
    
    public static void deleteDrug() {
    	System.out.println("\n----Delete Drug----");
        System.out.print("Enter Drug Id: ");
        Scanner sc = new Scanner(System.in);
        String drug_id = sc.nextLine();
        deleteDrugUtil(drug_id);
    }
    
    public static boolean deleteDrugUtil(String drug_id) {

        Map <String, String> map = getDrugData(drug_id);

        if(map.size() == 0) {
            System.out.println("\nDrug not found!");
            return false;
        }

        Connection con = null;
        Statement st = null;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(IDatabaseManager.url, IDatabaseManager.username, IDatabaseManager.password);
            st = con.createStatement();

            st.executeUpdate("delete from drug where drug_id = " + drug_id);

            System.out.println("data deleted successfully...");
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * This method print the information about input drug_id
     */
    
    public static void searchDrug() {
    	System.out.println("\n----Search Drug----");
        System.out.println("Enter Drug Id: ");
        Scanner sc = new Scanner(System.in);

        String drug_id = sc.nextLine();
        Map <String, String> map = searchDrugUtil(drug_id);

        if(map.size() != 0) {
        	System.out.println("\nDrug Id: " + map.get("drug_id"));
            System.out.println("Drug Name: " + map.get("drug_name"));
            System.out.println("Description: " + map.get("description"));
        } else {
        	System.out.println("\nDrug Not Found!");
        }
    }
    
    public static Map<String, String> searchDrugUtil(String drug_id) {

        Map<String, String> map = new HashMap<>();
        Connection con = null;
        Statement st = null;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(IDatabaseManager.url, IDatabaseManager.username, IDatabaseManager.password);
            st = con.createStatement();

            ResultSet rs = st.executeQuery("select * from drug where drug_id = " + drug_id);
            map.put("drug_id", rs.getString(1));
            map.put("drug_name", rs.getString(2));
            map.put("description", rs.getString(3));


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return map;

    }


    /**
     * This method takes drug data from user that he wants to add
     */
    public static void addDrug() {

        System.out.println("\n----Add new drug----");
        System.out.println("Enter Drug Name: ");

        Scanner sc = new Scanner(System.in);
        String drug_name = sc.nextLine();

        System.out.print("\nEnter Drug Description: ");
        String description = sc.nextLine();

        Connection con = null;
        Statement st = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(IDatabaseManager.url, IDatabaseManager.username, IDatabaseManager.password);
            st = con.createStatement();

            st.executeUpdate("insert into drug (drug_name, description) values ('" + drug_name + "','" + description + "')");
            System.out.println("\nDrug added successfully...");
            st.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }


}
