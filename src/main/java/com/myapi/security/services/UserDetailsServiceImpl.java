/**
 * Created by : Alan Nascimento on 4/1/2022
 */
package com.myapi.security.services;

import com.myapi.exception.UserException;
import com.myapi.models.User;
import com.myapi.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Transactional
  public UserDetails loadUserByUsernameAndCompanyCode(String idUserName) throws UsernameNotFoundException {
    User user = userRepository.findById(Long.parseLong(idUserName))
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with IdUserName: " + idUserName));

    return UserDetailsImpl.build(user);
  }
  public boolean validateLogin (User user, String passwordRequest) throws UsernameNotFoundException {
    if(!user.getStatus().equals("A")){
      throw new UserException("User is Disabled");
    }
    if (passwordRequest.isEmpty()) {
      throw new UserException("Password or Username Not Found or Incorrect!");
    } else {

      if (passwordRequest.equals(user.getPassword())) {
        return true;
      }
      return false;
    }
  }

  public boolean validateLogin (String username, String passwordRequest) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User Not Found with IdUserName: " + username));
    if(!user.getStatus().equals("A")){
      throw new UserException("User is Disabled");
    }
    if (passwordRequest.isEmpty()) {
      throw new UserException("Password or Username Not Found or Incorrect!");
    } else {

      String passwd = user.getPassword();
      if (passwordRequest.equals(passwd)) {
        return true;
      }
      return false;
    }
  }


  @Override
  public UserDetails loadUserByUsername(String idUserName) throws UsernameNotFoundException {
    return null;
  }
}
