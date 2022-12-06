/**
 * Created by : Alan Nascimento on 4/1/2022
 */
package com.myapi.services.exceptions;


public class UserBadRequestException extends RuntimeException {

    public UserBadRequestException(String msg) {
        super(msg);
    }
}
