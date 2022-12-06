/**
 * Created by : Alan Nascimento on 4/1/2022
 */
package com.myapi.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TokenRefreshRequest {
  @NotBlank
  private String refreshToken;

}
