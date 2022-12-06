/**
 * Created by : Alan Nascimento on 4/1/2022
 */
package com.myapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequerenteException extends RuntimeException{

    private static final long serialVersionUID = -6221868913026910626L;

    public RequerenteException(String mensagem ) {
        super(mensagem);
    }
    public RequerenteException(String mensagem, HttpStatus httpStatus) {
        super(mensagem);
    }
}
