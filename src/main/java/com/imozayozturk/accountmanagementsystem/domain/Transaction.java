package com.imozayozturk.accountmanagementsystem.domain;

import com.imozayozturk.accountmanagementsystem.domain.enums.CurrencyType;
import com.imozayozturk.accountmanagementsystem.model.TransactionDto;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Transaction extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public String fromAccountNumber;
    @Enumerated(EnumType.STRING)
    public CurrencyType fromAccountCurrencyType;
    public BigDecimal fromAccountUpdatedBalance;

    public String toAccountNumber;
    @Enumerated(EnumType.STRING)
    public CurrencyType toAccountCurrencyType;
    public BigDecimal toAccountUpdatedBalance;

    public BigDecimal amount;
    public BigDecimal rate;
    public BigDecimal calculatedAmount;

    public static Transaction createTransaction(TransactionDto transactionDto) {
        return Transaction
                .builder()
                .fromAccountNumber(transactionDto.getFromAccountNumber())
                .fromAccountCurrencyType(transactionDto.getFromCurrencyType())
                .fromAccountUpdatedBalance(transactionDto.getFromAccountUpdatedBalance())
                .toAccountNumber(transactionDto.getToAccountNumber())
                .toAccountCurrencyType(transactionDto.getToCurrencyType())
                .toAccountUpdatedBalance((transactionDto.getToAccountUpdatedBalance()))
                .amount(transactionDto.getAmount())
                .calculatedAmount(transactionDto.getCalculatedAmount())
                .rate(transactionDto.getExchangeRate())
                .build();
    }

    public TransactionDto toDto() {
        return TransactionDto
                .builder()
                .fromAccountNumber(getFromAccountNumber())
                .fromCurrencyType(getFromAccountCurrencyType())
                .fromAccountUpdatedBalance(getFromAccountUpdatedBalance())
                .toAccountNumber(getToAccountNumber())
                .toCurrencyType(getToAccountCurrencyType())
                .toAccountUpdatedBalance(getToAccountUpdatedBalance())
                .amount(getAmount())
                .calculatedAmount(getCalculatedAmount())
                .exchangeRate(getRate())
                .build();
    }
}
