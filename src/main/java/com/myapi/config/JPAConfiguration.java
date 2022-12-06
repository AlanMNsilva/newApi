/**
 * Created by : Alan Nascimento on 12/6/2022
 * inside the package - com.myapi.config
 */
package com.myapi.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
@ComponentScan(basePackageClasses = UserDetails.class)
public class JPAConfiguration {
}
