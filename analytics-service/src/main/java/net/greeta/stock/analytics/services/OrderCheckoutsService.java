package net.greeta.stock.analytics.services;

public interface OrderCheckoutsService {
  Long totalCheckoutsNumber();

  Long checkoutsForUser(String userId);
}
