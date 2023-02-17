package com.infoshareacademy.service;

import java.util.Scanner;

public class MenuSelection {

    public static void mainMenu(){
        int menuOption;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wybierz opcje:");
        System.out.println("1 - nowa gra");
        System.out.println("2 - zarządzaj kategoriami");
        System.out.println("3 - wyjście");
        menuOption = scanner.nextInt();
        //todo walidacja i wyjatki
        switch(menuOption){
            case 1:
                //nowa gra
                break;
            case 2:
                //menu kategorii
                categoryMenu();
                break;
            case 3:
                //wyjście
                System.out.println("Dzięki za wspólną zabawę!");
                break;
            default:
        }
    }

    public static void categoryMenu(){
        int menuOption;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wybierz opcje:");
        System.out.println("1 - dodaj kategorię");
        System.out.println("2 - usuń kategorię");
        System.out.println("3 - powrót do menu");
        menuOption = scanner.nextInt();
        //todo walidacja i wyjatki
        switch(menuOption){
            case 1:
                //dodawanie kategorii
                break;
            case 2:
                //usuń kategorie
                break;
            case 3:
                //wróc do menu głównego
                mainMenu();
                break;
            default:
        }
    }

}
