package net.greeta.stock.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import net.greeta.stock.ordering.api.application.dtos.OrderItemDTO;
import net.greeta.stock.ordering.api.application.dtos.ToOrderItemDTOMapper;
import net.greeta.stock.ordering.api.application.models.BasketItem;
import net.greeta.stock.validation.CardExpirationDate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CreateOrderCommand implements Command<Boolean> {
  private final List<OrderItemDTO> orderItems;
  private final String userId;
  private final String userName;
  @NotEmpty
  private final String city;
  @NotEmpty
  private final String street;
  @NotEmpty
  private final String state;
  @NotEmpty
  private final String country;
  @NotEmpty
  private final String zipCode;
  @NotEmpty
  @Size(min = 5, max = 19)
  private final String cardNumber;
  @NotEmpty
  private final String cardHolderName;
  @NotNull
  @CardExpirationDate
  private final LocalDate cardExpiration;
  @NotEmpty
  @Size(min = 3)
  private final String cardSecurityNumber;
  @NotNull
  private final String cardType;

  @Builder
  private CreateOrderCommand(
      List<BasketItem> basketItems,
      String userId,
      String userName,
      String city,
      String street,
      String state,
      String country,
      String zipCode,
      String cardNumber,
      String cardHolderName,
      LocalDate cardExpiration,
      String cardSecurityNumber,
      String cardType
  ) {
    orderItems = basketItems.stream()
        .map(basketItem -> new ToOrderItemDTOMapper().map(basketItem))
        .collect(Collectors.toList());
    this.userId = userId;
    this.userName = userName;
    this.city = city;
    this.street = street;
    this.state = state;
    this.country = country;
    this.zipCode = zipCode;
    this.cardNumber = cardNumber;
    this.cardHolderName = cardHolderName;
    this.cardExpiration = cardExpiration;
    this.cardSecurityNumber = cardSecurityNumber;
    this.cardType = cardType;
  }
}
