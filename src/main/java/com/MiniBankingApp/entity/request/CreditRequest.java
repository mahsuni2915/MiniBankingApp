package com.MiniBankingApp.entity.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreditRequest {
    private Long userId;
    private BigDecimal amount;
    private int installmentCount;
    private BigDecimal interestRate;
}