package com.infoshareacademy;

import com.infoshareacademy.model.User;
import com.infoshareacademy.service.MenuService;
import com.infoshareacademy.service.UserService;

public class App
{
    public static void main( String[] args ) throws Exception{
        System.out.println( "The Velopers" );
        User currentUser = UserService.createUser();
        MenuService.mainMenu();
    }
}
