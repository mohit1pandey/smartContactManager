package com.scm2.SmartContactManager.Helper;

public class ResousceNotFoundException extends RuntimeException{

    public ResousceNotFoundException(String message){
        super(message);
    }

    public ResousceNotFoundException(){
        super("resource not found");
    };
}
