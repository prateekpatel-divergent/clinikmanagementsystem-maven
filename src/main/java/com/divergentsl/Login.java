package com.divergentsl;

import java.util.Scanner;

/**
 * This Login class contains methods for login admin and doctor
 */
public class Login {


    /**
     * This method is loginPanel which ask for that you want to login as admin or doctor
     * for admin or doctor to login, first admin have to login if username & password is correct then it will redirect admin to adminPanel if it login as admin or if it will login as doctor then it will redirect ot doctorPanel, other wise it will print message "You are not authorised"
     */
    public static void loginPanel() {

        Admin a = new Admin();
        Doctor d = new Doctor();

        Main:
        while(true) {

            System.out.println("\n----Login Panel----");
            System.out.println("1. Admin");
            System.out.println("2. Doctor");
            System.out.println("3. Exit");

            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();

            Login:
            switch (input) {

                case "1":
                    if (a.adminLogin()) {
                        Admin.adminPanel();
                    } else {
                        System.out.println("You are not Authorised");
                    }
                    break;

                case "2":

                    String did = d.doctorLogin();

                    if (did != null) {
                        String s[] = did.split(" ");
                        Doctor.doctorPanel(s[0], s[1]);
                    } else {
                        System.out.println("You are not Authorised");
                        break Login;
                    }
                    break;

                case "3":
                    break Main;

                default:
                    System.out.println("Invalid Input");
                    break;
            }
        }
    }


}
