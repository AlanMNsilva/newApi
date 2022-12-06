/**
 * Created by : Alan Nascimento on 4/1/2022
 */
package com.myapi.models;

import lombok.Data;

import java.time.Instant;

import javax.persistence.*;

@Entity(name = "refreshtoken")
@Data
public class RefreshToken {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "refreshtoken_id")
  private long id;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  private User user;

  @Column(nullable = false, unique = true,name = "token")
  private String token;

  @Column(nullable = false, name = "expiry_date")
  private Instant expiryDate;

  @Column(nullable = true, name = "company_cod")
  private String companyCod;



}
