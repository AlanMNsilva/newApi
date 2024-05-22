/**
 * Created by : Alan Nascimento on 4/1/2022
 */
package com.myapi.controllers;

import javax.validation.Valid;

import com.myapi.exception.UserException;
import com.myapi.models.RefreshToken;
import com.myapi.models.User;
import com.myapi.payload.request.LoginRequest;
import com.myapi.payload.request.TokenRefreshRequest;
import com.myapi.services.exceptions.TokenRefreshException;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.myapi.payload.response.JwtResponse;
import com.myapi.payload.response.MessageResponse;
import com.myapi.payload.response.TokenRefreshResponse;

import com.myapi.repository.UserRepository;
import com.myapi.security.jwt.JwtUtils;
import com.myapi.security.services.RefreshTokenService;
import com.myapi.security.services.UserDetailsServiceImpl;

/**
 * To use this controller is to login, control users and generate valid tokens and refresh tokens.
 * When a token expires, you can only use a refresh token no make a new token.
 * When a refresh token expires, you must do login again with a valid username and password, a new refresh token will be persisted on DB and
 * a new Bearer token will be generated on application context.
 * For swagger docs and tests try localhost:8085/swagger-ui.html, it is bypassing the auth process.
 * */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;

  private final PasswordEncoder encoder;

  private final UserDetailsServiceImpl userDetailsService;
    private User user;
  private RefreshToken refreshToken;
  private final JwtUtils jwtUtils;
  private final RefreshTokenService refreshTokenService;


    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder encoder, UserDetailsServiceImpl userDetailsService, RefreshToken refreshToken, JwtUtils jwtUtils, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.userDetailsService = userDetailsService;
        this.refreshToken = refreshToken;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
    }

    @ApiOperation(value = "URI: /api/auth/signin",
            produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "A JSON object representing the User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = JwtResponse.class),
            @ApiResponse(code = 401, message = "Password or Username Not Found or Incorrect", response = UserException.class),
            @ApiResponse(code = 500, message = "Server side exception")
    })
  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {


      user = userRepository.findByUsername(loginRequest.getEmployeeLogin())
              .orElseThrow(() -> new UsernameNotFoundException("Password or Username Not Found or Incorrect: " + loginRequest.getEmployeeLogin()));

      if (userDetailsService.validateLogin(user, loginRequest.getEmployeePassword())) {

        String jwt = jwtUtils.generateJwtToken(user, loginRequest.getEmployeeLogin());

        RefreshToken refreshTokenAlreadyExist = refreshTokenService.findByUser(user).orElse(null);
        if (refreshTokenAlreadyExist == null) {
          refreshToken = refreshTokenService.createRefreshToken(user.getId());
        } else {
          refreshTokenService.updateRefreshToken(refreshTokenAlreadyExist);
          refreshToken = refreshTokenService.createRefreshToken(user.getId());
        }
        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), user.getId(),
                user.getUsername(), user.getEmail()));

      } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new UserException("Password or Username Not Found or Incorrect"));
      }

    }
    @ApiOperation(value = "URI: /api/auth/refreshtoken",
            produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "Valid token on body")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Refresh token valido", response = TokenRefreshResponse.class),
            @ApiResponse(code = 401, message = "Refresh token is not in database!", response = TokenRefreshException.class),
            @ApiResponse(code = 500, message = "Server side exception"),
    })
  @PostMapping("/refreshtoken")
  public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
    String requestRefreshToken = request.getRefreshToken();
    RefreshToken refreshToken1 =  refreshTokenService.findByToken(requestRefreshToken).orElse(null);

    return refreshTokenService.findByToken(requestRefreshToken)
        .map(refreshTokenService::verifyExpiration)
        .map(RefreshToken::getUser)
        .map(user -> {
          String token = jwtUtils.generateTokenFromUserCodAndUserName(String.valueOf(user.getId()),refreshToken1.getCompanyCod());
          return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
        })
        .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
            "Refresh token is not in database!"));
  }

    @ApiOperation(value = "URI: /api/auth/logout",
            produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "Receive a valid token of an user and delete the user refresh token from DB.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Log out successful!", response = MessageResponse.class),
            @ApiResponse(code = 401, message = "User id cannot be null or empty", response = MessageResponse.class),
            @ApiResponse(code = 500, message = "Server side exception"),
    })
  @PostMapping("/logout")
  public ResponseEntity<?> logoutUser(@Valid @RequestHeader("Authorization") String token) {
        if(jwtUtils.validateJwtToken(token)) {
            refreshTokenService.deleteByUserId(Long.parseLong(jwtUtils.getUserCodByJwtToken(token)));
            return ResponseEntity.ok(new MessageResponse("Log out successful!"));
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("User id cannot be null or empty"));
        }
  }
  }

