/**
 * Created by : Alan Nascimento on 4/2/2022
 */
package com.myapi.services;

import com.myapi.ApplicationConfigTest;
import com.myapi.dto.UserDTO;
import com.myapi.models.User;
import com.myapi.repository.UserRepository;
import com.myapi.services.exceptions.UserBadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.util.Optional;

import static org.mockito.Mockito.when;

@DisplayName("UserServiceTest")
public class UserServiceTest extends ApplicationConfigTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Test
    public void callBadRequestWhenFirstNameorLastNameIsEmpty()  {
        UserDTO userDTO = Mockito.mock(UserDTO.class);
        when(userDTO.getFirstName()).thenReturn("");
        when(userDTO.getLastName()).thenReturn("");
        assertThrows(UserBadRequestException.class, () -> userService.saveUser(userDTO), "User first name and last name cannot be null or empty");
    }

    @Test
    public void callBadRequestWhenUserIdIsNull()  {
        assertThrows(UserBadRequestException.class, () -> userService.findUserById(null), "Id cannot be null or empty");
    }

    @Test
    public void mustSaveAnUser()  {
        UserDTO userDTO = Mockito.mock(UserDTO.class);
        when(userDTO.getFirstName()).thenReturn("Name test");
        when(userDTO.getLastName()).thenReturn("Last Name test");
        userService.saveUser(userDTO);
        Mockito.verify(this.userRepository, Mockito.times(1)).save(ArgumentMatchers.any(User.class));
    }

    @Test
    public void mustUpdateAnUserButOnlyChangeLastNamePhoneAndEmail(){
        UserDTO userDTO = Mockito.mock(UserDTO.class);
        User user = Mockito.mock(User.class);
        when(userDTO.getId()).thenReturn(1L);
        when(userRepository.findById(ArgumentMatchers.eq(1L))).thenReturn(Optional.of(user));
        userService.updateUser(userDTO);
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(this.userRepository, Mockito.times(1)).save(ArgumentMatchers.any(User.class));
    }
}
