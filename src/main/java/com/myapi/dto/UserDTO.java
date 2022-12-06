/**
 * Created by : Alan Nascimento on 4/1/2022
 */
package com.myapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


@Data
@Getter
@Builder
@NoArgsConstructor

public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    @JsonIgnore
    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String status;

    public UserDTO(Long id, String firstName, String lastName, String email, String phoneNumber, String username, String password, String status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.status = status;
    }
    public UserDTO(Long id, String firstName, String lastName, String email, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;

    }
    public UserDTO( String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;

    }
}
