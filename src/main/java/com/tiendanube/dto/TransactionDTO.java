package com.tiendanube.dto;

import com.tiendanube.model.PaymentMethod;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransactionDTO {

    private String id;
    private BigDecimal value;
    private String description;
    private String method;
    private String cardNumber;
    private String cardHolderName;
    private String cardExpirationDate;
    private String cardCvv;
}
