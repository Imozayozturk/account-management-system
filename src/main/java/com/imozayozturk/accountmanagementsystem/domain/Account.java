package com.imozayozturk.accountmanagementsystem.domain;


import com.imozayozturk.accountmanagementsystem.domain.enums.CurrencyType;
import com.imozayozturk.accountmanagementsystem.model.AccountDto;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Account extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String accountNumber;

    @ManyToOne
    private Customer customer;

    private String description;

    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;

    private BigDecimal balance;

    private Boolean status;

    @Version
    private Integer version;

    public static Account createAccount(AccountDto accountDto) {
        return Account
                .builder()
                .balance(accountDto.getBalance())
                .accountNumber(UUID.randomUUID().toString())
                .customer(accountDto.getCustomer())
                .description(accountDto.getDescription())
                .currencyType(accountDto.getCurrencyType())
                .status(true)
                .build();
    }

    public void updateDescription(String description) {
        setDescription(description);
    }

    public void updateStatus(boolean status) {
        setStatus(status);
    }

    public void checkBalance(BigDecimal amount) {
        Optional.of(amount)
                .filter(amnt -> getBalance().compareTo(amnt) >= 0)
                .orElseThrow(() -> new RuntimeException("Insufficent balance"));
    }
}
