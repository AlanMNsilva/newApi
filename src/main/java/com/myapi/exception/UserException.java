/**
 * Created by : Alan Nascimento on 4/1/2022
 */
package com.myapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserException extends RuntimeException{

    private static final long serialVersionUID = -6221868913026910626L;

    public UserException(String mensagem ) {
        super(mensagem);
    }

}
