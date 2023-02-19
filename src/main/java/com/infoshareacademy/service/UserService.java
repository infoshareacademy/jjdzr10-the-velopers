package com.infoshareacademy.service;

import com.infoshareacademy.model.User;

import java.util.Scanner;

public class UserService {

    public static User createUser() {
        Scanner scanner = new Scanner(System.in);
        boolean userNameEmpty = true;
        while (userNameEmpty) {
            System.out.println("Podaj nazwę użytkownika");
            String userName = scanner.nextLine();
            if (userName.length() > 0) {
                User newUser = new User(userName);
                userNameEmpty = false;
                return newUser;
            }
        }
        return null;
    }
}
