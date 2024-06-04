package com.example.dalia.exceptions;

public class ProductAlreadyExistException extends RuntimeException{
    public ProductAlreadyExistException(String msg){
        super(msg);
    }
}
