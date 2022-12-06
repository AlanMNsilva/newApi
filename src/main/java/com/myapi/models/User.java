/**
 * Created by : Alan Nascimento on 4/1/2022
 */
package com.myapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "user")
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id",updatable= false)
	private Long id;

	@JsonIgnore
	@Column(name = "username", updatable= false)
	private String username;

	@Column(name = "email")
	private String email;

	@JsonIgnore
	@Column(name = "password",updatable= false)
	private String password;

	@JsonIgnore
	@Column(name = "status",updatable= false)
	private String status;

	@Column(name = "first_name", updatable= false)
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "phone_number")
	private String phoneNumber;

}
