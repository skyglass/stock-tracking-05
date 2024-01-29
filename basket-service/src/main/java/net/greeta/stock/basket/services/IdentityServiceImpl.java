package net.greeta.stock.basket.services;

import net.greeta.stock.basket.helper.JwtHelper;
import net.greeta.stock.basket.security.JwtAuthConverterProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IdentityServiceImpl implements IdentityService {

  private final JwtAuthConverterProperties jwtAuthConverterProperties;

  @Override
  public String getUserIdentity() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

  @Override
  public String getUserName() {
    var token = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return JwtHelper.getUsername(token, jwtAuthConverterProperties);
  }
}
