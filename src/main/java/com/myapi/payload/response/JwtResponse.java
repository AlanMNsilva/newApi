/**
 * Created by : Alan Nascimento on  4/1/2022
 */
package com.myapi.payload.response;

import lombok.Data;

@Data
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String refreshToken;
	private Long id;
	private String username;
	private String email;

	public JwtResponse(String accessToken, String refreshToken, Long id, String username, String email) {
		this.token = accessToken;
		this.refreshToken = refreshToken;
		this.id = id;
		this.username = username;
		this.email = email;

	}

}
