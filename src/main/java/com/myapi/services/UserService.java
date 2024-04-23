/**
 * Created by : Alan Nascimento on 4/1/2022
 */
package com.myapi.services;


import com.myapi.dto.UserDTO;
import com.myapi.models.User;
import com.myapi.repository.UserRepository;
import com.myapi.services.exceptions.UserBadRequestException;
import com.myapi.services.exceptions.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserDTO findUserById(Long userId)  {
        UserDTO userDTO = null;
        if(userId == null){ throw new UserBadRequestException("Id cannot be null or empty");}
        User user = userRepository.findById(userId).orElseThrow(() ->new UserNotFoundException("User id " + userId +  " Not found"));
        if(user != null) {
            ModelMapper modelMapper = new ModelMapper();
            userDTO = modelMapper.map(user, UserDTO.class);

        }
        return userDTO;

    }

    public List<User> findAllUsers(){
        List<User> users =  userRepository.findAll();
        return users;
    }


    public void updateUser(UserDTO userDTO){
              User user = userRepository.findById(userDTO.getId()).orElseThrow( () -> new UserNotFoundException("User not found"));
              ModelMapper modelMapper = new ModelMapper();
              user = modelMapper.map(userDTO, User.class);
              userRepository.save(user);

    }

    public User saveUser(UserDTO userDTO){
      if(userDTO.getFirstName().isEmpty() && userDTO.getLastName().isEmpty()) {
          throw new UserBadRequestException("User first name and last name cannot be null or empty"); }
      else {
          ModelMapper modelMapper = new ModelMapper();
          User user = modelMapper.map(userDTO, User.class);
          return userRepository.save(user);
      }
    }
}
