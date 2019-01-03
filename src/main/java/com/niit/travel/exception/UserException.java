package com.niit.travel.exception;

public class UserException extends RuntimeException{
    private static final long serialVersionUID = 8014107998164340463L;

    public UserException(String msg){
        super(msg);
    }
}
