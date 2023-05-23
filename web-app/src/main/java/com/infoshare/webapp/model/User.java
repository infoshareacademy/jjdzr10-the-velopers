package com.infoshare.webapp.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


public class User {

    @NotEmpty(message = "The username cannot be empty")
    @Size(min=2, max=20, message = "The username should contain between 2 and 20 characters")
    private String userName;
    private Score userScore;
    @NotEmpty(message = "The password cannot be empty")
    @Size(min=2, max=10, message = "The password should contain between 2 and 10 characters")
    private String password;
    @NotEmpty(message = "The email cannot be empty")
    @Email(message = "Email should be valid")
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
