/**
 * Created by : Alan Nascimento on 4/1/2022
 */
package com.myapi.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.myapi.security.services.UserDetailsServiceImpl;

public class AuthTokenFilter extends OncePerRequestFilter {

  private JwtUtils jwtUtils;


  private UserDetailsServiceImpl userDetailsService;

  public AuthTokenFilter() {
  }

  public AuthTokenFilter(JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsService) {
    this.jwtUtils = jwtUtils;
    this.userDetailsService = userDetailsService;
  }

  public AuthTokenFilter(JwtUtils jwtUtils) {
    this.jwtUtils = jwtUtils;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String jwt = parseJwt(request);
      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
        String userId = jwtUtils.getUserCodByJwtToken(jwt);

        UserDetails userDetails = userDetailsService.loadUserByUsernameAndCompanyCode(userId);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");

    if (StringUtils.hasText(headerAuth)) {
      return headerAuth;
    }

    return null;
  }
}
