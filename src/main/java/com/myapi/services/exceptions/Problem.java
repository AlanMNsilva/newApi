/**
 * Created by : Alan Nascimento on 12/6/2022
 * inside the package - com.myapi.services.exceptions
 */
package com.myapi.services.exceptions;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class Problem {

    public static final URI DEFAULT_TYPE = URI.create(ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUriString());
}
