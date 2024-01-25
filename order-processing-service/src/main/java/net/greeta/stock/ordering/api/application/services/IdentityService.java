package net.greeta.stock.ordering.api.application.services;

public interface IdentityService {
  String getUserIdentity();

  boolean isAdmin();
}
