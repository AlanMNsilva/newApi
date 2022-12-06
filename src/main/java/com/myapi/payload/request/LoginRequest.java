/**
 * Created by : Alan Nascimento on 4/1/2022
 */
package com.myapi.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
	@NotBlank
	private String employeeLogin;

	@NotBlank
	private String employeePassword;

	@JsonIgnore
	private String employeeCod;

}
