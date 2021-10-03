package com.imozayozturk.accountmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imozayozturk.accountmanagementsystem.domain.Account;
import com.imozayozturk.accountmanagementsystem.domain.Customer;
import com.imozayozturk.accountmanagementsystem.domain.enums.CurrencyType;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@Builder
@ToString
public class AccountDto {
    public Long customerId;
    public String description;
    private String accountNumber;
    public CurrencyType currencyType;
    public BigDecimal balance;
    @JsonIgnore
    public Customer customer;

    public static AccountDto fromEntity(Account account){
        return AccountDto
                .builder()
                .balance(account.getBalance())
                .accountNumber(account.getAccountNumber())
                .customerId(account.getCustomer().getId())
                .customer(account.getCustomer())
                .description(account.getDescription())
                .currencyType(account.getCurrencyType())
                .build();
    }
}
