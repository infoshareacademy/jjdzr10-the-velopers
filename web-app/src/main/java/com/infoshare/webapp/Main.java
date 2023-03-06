package com.infoshare.webapp;

import com.infoshare.webapp.service.MenuService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        MenuService.mainMenu();
    }

}