package com.infoshare.webapp.exception;

public class QuestionsNotFoundException extends RuntimeException{
    public QuestionsNotFoundException(String message) {
        System.out.println(message);
    }
}
