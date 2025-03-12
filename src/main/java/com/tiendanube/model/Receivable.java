package com.tiendanube.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Receivable {

    @Id
    private String id;
    private ReceivableStatus status;
    private LocalDateTime createDate;
    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal total;
}
