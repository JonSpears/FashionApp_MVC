package com.spears.fashionblogapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserDoesNotExistException extends RuntimeException{
    public UserDoesNotExistException (String s){
        super(s);
    }
}
