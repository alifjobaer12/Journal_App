package com.journal.journalApp.encription;

import java.util.Base64;
import java.util.Scanner;

public class encript {

    public static void encript_u_p(String username, String password) {
        String username_password = username + ":" + password;
        String encoded = Base64.getEncoder().encodeToString(username_password.getBytes());
        System.out.println(encoded);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Username : ");
            String username = sc.nextLine();

            if(username.equals("0") || username.equals("q")) break;

            System.out.print("Password : ");
            String password = sc.nextLine();

            System.out.println("-------------------------");
            encript_u_p(username, password);
            System.out.println("-------------------------\n");

        }
    }
}
