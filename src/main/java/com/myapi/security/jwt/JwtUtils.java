/**
 * Created by : Alan Nascimento on 4/1/2022
 */
package com.myapi.security.jwt;

import java.util.Date;

import com.myapi.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;

@Component
public class JwtUtils {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.ExpirationMs}")
  private int jwtExpirationMs;


  public String generateJwtToken(User user, String username ) {
  return generateTokenFromUserCodAndUserName(String.valueOf(user.getId()), username);
}

  public String generateTokenFromUserCodAndUserName(String userCod, String username) {
    return Jwts.builder().setSubject(userCod).setId(username).setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public String generateTokenFromUsername(String userCod) {
    return Jwts.builder().setSubject(userCod).setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
  }

  public Long getCompanyIdFromJwtToken (String token){
    return Long.parseLong(Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getId());
  }

  public String getUserCodByJwtToken(String token) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
//      logger.error("Invalid JWT signature: {}", e.getMessage());
      e.printStackTrace();
    } catch (MalformedJwtException e) {
  //    logger.error("Invalid JWT token: {}", e.getMessage());
      e.printStackTrace();
    } catch (ExpiredJwtException e) {
  //    logger.error("JWT token is expired: {}", e.getMessage());
      e.printStackTrace();
    } catch (UnsupportedJwtException e) {
   //   logger.error("JWT token is unsupported: {}", e.getMessage());
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
  //    logger.error("JWT claims string is empty: {}", e.getMessage());
      e.printStackTrace();
    }

    return false;
  }

}
