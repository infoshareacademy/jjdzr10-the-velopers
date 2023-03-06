package com.infoshare.webapp.service;

import com.infoshare.webapp.model.User;

import java.util.Scanner;

public class UserService {

    private static Scanner scanner;
    public static User createUser() {
        scanner = new Scanner(System.in);
        User newUser;
        while (true) {
            System.out.println("Podaj nazwę użytkownika");
            String userName = scanner.nextLine();
            if (userName.length() > 0) {
                newUser = new User(userName);
                return newUser;
            }
        }
    }
}
