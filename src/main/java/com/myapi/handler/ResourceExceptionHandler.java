/**
 * Created by : Alan Nascimento on 4/1/2022
 */
package com.myapi.handler;

import com.myapi.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.myapi.services.exceptions.UserBadRequestException;
import com.myapi.services.exceptions.UserNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<?> handleUserNaoEncontradoException(UserException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundException (UserNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(UserBadRequestException.class)
    public ResponseEntity<?> userBadRequestException (UserBadRequestException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }


}