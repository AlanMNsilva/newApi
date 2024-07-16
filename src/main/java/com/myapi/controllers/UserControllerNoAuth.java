/**
 * Created by : Alan Nascimento on 4/1/2022
 */
package com.myapi.controllers;

import com.myapi.dto.UserDTO;
import com.myapi.services.UserService;
import com.myapi.services.exceptions.UserBadRequestException;
import com.myapi.services.exceptions.UserNotFoundException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * This controller is bypassing JWT token authentication.
 * To use It, you just need to call the endpoint
 */

@RestController
@RequestMapping("/api/noauth")
public class UserControllerNoAuth {


    private final UserService userService;

    public UserControllerNoAuth(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "URI: /api/noauth/user",
            produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "Find an user by passing user id at body")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User is found"),
            @ApiResponse(code = 404, message = "User not found", response = UserNotFoundException.class),
            @ApiResponse(code = 500, message = "Server side exception"),
    })
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<?> findUser(@RequestBody UserDTO userDTO){
        userDTO = userService.findUserById(userDTO.getId());
        if(userDTO == null){
            throw new UserNotFoundException("User not found");
        }else{ return ResponseEntity.status(HttpStatus.OK).body(userDTO); }
    }

    @ApiOperation(value = "URI: /api/noauth/user/save",
            produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "Save an user into DB")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User created"),
            @ApiResponse(code = 400, message = "First name or Last name cannot be null", response = UserBadRequestException.class),
            @ApiResponse(code = 500, message = "Server side exception"),
    })
    @RequestMapping (method = RequestMethod.POST, value = "/user/save")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO){
        userService.saveUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "URI: /api/noauth/user/update",
            produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "Update an user")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "User updated "),
            @ApiResponse(code = 404, message = "User not Found", response = UserNotFoundException.class),
            @ApiResponse(code = 500, message = "Server side exception"),
    })
    @RequestMapping (method = RequestMethod.PUT, value = "/user/update")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO){
        userService.updateUser(userDTO);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "URI: /api/noauth/users",
            produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "Return list of users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return a list of users "),
            @ApiResponse(code = 500, message = "Server side exception"),
    })
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<?> findUsers(){
        return ResponseEntity.ok().body(userService.findAllUsers());
    }


    @ApiOperation(value = "URI: /api/noauth/user/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "Update an user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User found and returned on body "),
            @ApiResponse(code = 404, message = "User not found or ID null", response = UserNotFoundException.class),
            @ApiResponse(code = 500, message = "Server side exception"),
    })
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findUserById(@PathVariable Long id){
        if(id == null )  throw new UserNotFoundException("User not found");
        UserDTO userDTO = userService.findUserById(id);
        if(userDTO == null){
            throw new UserNotFoundException("User not found");
        }else{ return ResponseEntity.status(HttpStatus.OK).body(userDTO); }

    }

    @ApiOperation(value = "URI: /api/noauth/healthcheck",
            produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "Health Check Status")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "API is working "),
            @ApiResponse(code = 500, message = "Server side exception"),
    })
    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity<?> findApiVersion(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body("API V1 - Health status");

    }


}
