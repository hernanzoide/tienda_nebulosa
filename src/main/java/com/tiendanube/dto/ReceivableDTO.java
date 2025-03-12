package com.tiendanube.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ReceivableDTO {

    private String id;
    private String status;
    private LocalDateTime createDate;
    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal total;
}
