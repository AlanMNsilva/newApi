/**
 * Created by : Alan Nascimento on 4/1/2022
 */
package com.myapi.controllers;

import com.myapi.services.exceptions.UserBadRequestException;
import com.myapi.services.exceptions.UserNotFoundException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.myapi.dto.UserDTO;
import com.myapi.services.UserService;
/**
 * To use this controller fully authentication is required.
 * On Header must have an Authorization param with a Bearer token valid and not expired.
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api")
public class UserController {

    public UserController(UserService userService) {
        this.userService = userService;
    }
    private final UserService userService;

    @ApiOperation(value = "URI: /api/user",
            produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "Find an user by passing user id at body")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User is found", response = UserNotFoundException.class),
            @ApiResponse(code = 404, message = "User not found", response = UserNotFoundException.class),
            @ApiResponse(code = 500, message = "Server side exception"),
    })
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<?> findUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok().body(userService.findUserById(userDTO.getId()));
    }

    @ApiOperation(value = "URI: /api/user/save",
            produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "Save an user into DB")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User created", response = UserBadRequestException.class),
            @ApiResponse(code = 400, message = "First name or Last name cannot be null", response = UserBadRequestException.class),
            @ApiResponse(code = 500, message = "Server side exception"),
    })
    @RequestMapping (method = RequestMethod.POST, value = "/user/save")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO){
        userService.saveUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "URI: /api/user/update",
            produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "Update an user")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "User updated ", response = UserNotFoundException.class),
            @ApiResponse(code = 400, message = "First name or Last name cannot be null", response = UserNotFoundException.class),
            @ApiResponse(code = 500, message = "Server side exception"),
    })
    @RequestMapping (method = RequestMethod.PUT, value = "/user/update")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO){
        userService.updateUser(userDTO);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.findUserById(id));
    }



}
