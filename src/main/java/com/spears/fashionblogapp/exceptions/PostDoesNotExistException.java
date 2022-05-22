package com.spears.fashionblogapp.exceptions;

public class PostDoesNotExistException extends RuntimeException {
    public PostDoesNotExistException(String s) {
        super(s);
    }
}
