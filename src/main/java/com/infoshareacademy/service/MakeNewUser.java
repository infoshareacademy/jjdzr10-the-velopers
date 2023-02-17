package com.infoshareacademy.service;

import com.infoshareacademy.model.User;

import java.util.Scanner;

public class MakeNewUser {

    public static User createUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj nazwę użytkownika");
        String userName = scanner.nextLine();
        User newUser = new User(userName);
        System.out.println("Witaj " + userName + "!");
        return newUser;
    }
}
