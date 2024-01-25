package net.greeta.stock.ordering.api.infrastructure.services;

import net.greeta.stock.ordering.api.application.services.IdentityService;
import net.greeta.stock.ordering.shared.ApplicationService;
import net.greeta.stock.security.EshopRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import static net.greeta.stock.security.GrantedAuthoritiesUtils.role;

@ApplicationService
public class IdentityServiceImpl implements IdentityService {

  private final GrantedAuthority ADMIN = new SimpleGrantedAuthority(role(EshopRole.Admin));

  @Override
  public String getUserIdentity() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

  @Override
  public boolean isAdmin() {
    return authentication().getAuthorities().stream()
      .anyMatch(grantedAuthority -> grantedAuthority.equals(ADMIN));
  }

  private Jwt currentJwt() {
    return (Jwt) authentication().getPrincipal();
  }

  private Authentication authentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }
}
