/**
 * Created by : Alan Nascimento on 4/1/2022
 */
package com.myapi.repository;

import java.util.Optional;

import com.myapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
}
