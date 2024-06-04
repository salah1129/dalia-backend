package com.example.dalia.exceptions;

public class CategoryAlreadyExistException extends RuntimeException{
    public CategoryAlreadyExistException(String message){
        super(message);
    }
}
