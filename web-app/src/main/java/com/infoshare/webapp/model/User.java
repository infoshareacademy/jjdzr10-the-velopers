package com.infoshare.webapp.model;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class User {

    @NotEmpty(message = "Nazwa użytkownika nie może być pusta")
    @Size(min=2, max=20, message = "Nazwa użytkownika powinna zawierać od 2 do 20 znaków")
    private String userName;
    private Score userScore;

    private String password;
    private String email;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Score getUserScore() {
        return userScore;
    }

    public void setUserScore(Score userScore) {
        this.userScore = userScore;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userScore=" + userScore +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
