package com.imozayozturk.accountmanagementsystem.service;

import com.imozayozturk.accountmanagementsystem.domain.Account;
import com.imozayozturk.accountmanagementsystem.domain.Customer;
import com.imozayozturk.accountmanagementsystem.model.AccountDto;
import com.imozayozturk.accountmanagementsystem.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerService customerService;

    public Account getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public Account getAccount(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public List<Account> getAccounts(Long customerId) {
        return accountRepository.findAllByCustomer(customerId).orElse(List.of());
    }

    public void createAccount(AccountDto accountDto) {
        Customer customer = customerService.getCustomer(accountDto.getCustomerId());
        accountDto.setCustomer(customer);
        accountRepository.save(Account.createAccount(accountDto));
    }

    public void updateAccount(Long accountId, String description) {
        accountRepository.findById(accountId)
                .ifPresentOrElse(
                        account -> {
                            account.updateDescription(description);
                            accountRepository.save(account);
                        },
                        () -> {
                            throw new RuntimeException("Account not found");
                        }
                );
    }

    public void deleteAccount(Long accountId) {
        accountRepository
                .findById(accountId)
                .ifPresentOrElse(account -> {
                    account.updateStatus(false);
                    accountRepository.save(account);
                }, () -> {
                    throw new RuntimeException("Account not found");
                });
    }

    @Transactional
    public Account withDrawal(Long accountId, BigDecimal amount) {
        Account account = getAccount(accountId);
        account.checkBalance(amount);
        account.setBalance(account.getBalance().subtract(amount));
        return accountRepository.save(account);
    }

    @Transactional
    public Account deposit(Long accountId, BigDecimal amount) {
        Account account = getAccount(accountId);
        account.setBalance(account.getBalance().add(amount));
        return accountRepository.save(account);
    }
}
