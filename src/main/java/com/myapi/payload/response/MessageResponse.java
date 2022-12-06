/**
 * Created by : Alan Nascimento on  4/1/2022
 */
package com.myapi.payload.response;

import lombok.Data;

@Data
public class MessageResponse {
	private String message;

	public MessageResponse(String message) {
	    this.message = message;
	  }

}
