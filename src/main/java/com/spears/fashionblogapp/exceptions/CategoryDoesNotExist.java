package com.spears.fashionblogapp.exceptions;

public class CategoryDoesNotExist extends RuntimeException {
    public CategoryDoesNotExist(String s) {
        super(s);
    }
}
