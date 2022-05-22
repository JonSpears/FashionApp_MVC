package com.spears.fashionblogapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BlogExceptionHandler {

    @ExceptionHandler({
            InvalidRequestException.class,
            UserDoesNotExistException.class,
            PostDoesNotExistException.class,
            CategoryDoesNotExist.class
    })
    public ResponseEntity<Object> handleCustomExceptions (Exception ex){

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
