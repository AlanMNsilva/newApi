/**
 * Created by : Alan Nascimento on 4/1/2022
 */
package com.myapi.security.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.myapi.models.RefreshToken;
import com.myapi.models.User;
import com.myapi.repository.RefreshTokenRepository;
import com.myapi.repository.UserRepository;
import com.myapi.services.exceptions.TokenRefreshException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RefreshTokenService {
  @Value("${jwt.RefreshExpirationMs}")
  private Long refreshTokenDurationMs;


  private final RefreshTokenRepository refreshTokenRepository;


  private final UserRepository userRepository;

  public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
    this.refreshTokenRepository = refreshTokenRepository;
    this.userRepository = userRepository;
  }

  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }

  public Optional<RefreshToken> findByUser(User user) {
    return refreshTokenRepository.findByUser(user);
  }

  public void updateRefreshToken (RefreshToken token){
      refreshTokenRepository.delete(token);
  }

  public RefreshToken createRefreshToken(Long userId) {
    RefreshToken refreshToken = new RefreshToken();

    refreshToken.setUser(userRepository.findById(userId).get());
    refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
    refreshToken.setToken(UUID.randomUUID().toString());

    refreshToken = refreshTokenRepository.save(refreshToken);
    return refreshToken;
  }

  public boolean verifyExpirationReturningBoolean(RefreshToken token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token);
      return true;
    }else {
      return false;
    }
  }
  public RefreshToken verifyExpiration(RefreshToken token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token);
      throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
    }

    return token;
  }

  @Transactional
  public int deleteByUserId(Long userId) {
    return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
  }
}
