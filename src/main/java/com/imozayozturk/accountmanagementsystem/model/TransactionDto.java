package com.imozayozturk.accountmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imozayozturk.accountmanagementsystem.domain.enums.CurrencyType;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data @Builder @ToString
public class TransactionDto {

    private String fromAccountNumber;
    @JsonIgnore
    private CurrencyType fromCurrencyType;
    @JsonIgnore
    private BigDecimal fromAccountUpdatedBalance;

    private String toAccountNumber;
    @JsonIgnore
    private CurrencyType toCurrencyType;
    @JsonIgnore
    public BigDecimal toAccountUpdatedBalance;


    private BigDecimal amount;
    @JsonIgnore
    private BigDecimal exchangeRate;
    @JsonIgnore
    private BigDecimal calculatedAmount;

}
