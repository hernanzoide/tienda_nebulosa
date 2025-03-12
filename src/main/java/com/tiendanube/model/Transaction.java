package com.tiendanube.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "merchant_tx")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @Column(name = "transaction_id")
    private String id;

    @Column(name = "transaction_value")
    private BigDecimal value;

    private String description;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    private String cardNumber;
    private String cardHolderName;
    private String cardExpirationDate;
    private String cardCvv;

    @OneToOne
    @JoinColumn(name = "receivable_id", referencedColumnName = "id")
    private Receivable receivable;
}

