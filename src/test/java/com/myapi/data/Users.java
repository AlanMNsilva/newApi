/**
 * Created by : Alan Nascimento on 11/21/2022
 * inside the package - com.myapi.data
 */
package com.myapi.data;

import com.myapi.dto.UserDTO;
import com.myapi.models.User;

import java.util.Random;

public class Users {

    public static long anId() {return 9999;}

    public static User aUser() {
      User user = new User();
      user.setFirstName("Test user");
      user.setUsername("username");
      user.setId(anId());
      user.setPassword("password");
      user.setEmail("randomemail@randomemail.com");
      return user;
    }

    public static UserDTO aBuildUserDTO(){
        UserDTO user = new UserDTO();
        user.setId(new Random().nextLong());
        user.setFirstName("Test user");
        user.setUsername("username");
        user.setId(anId());
        user.setPassword("password");
        user.setEmail("randomemail@randomemail.com");
        return user;
    }

}
