package com.example.shoprunner_be.exceptions;

public class InappropriateDataException extends RuntimeException{
    public InappropriateDataException(String message){
        super(message);
    }
}
