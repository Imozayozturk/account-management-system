package com.imozayozturk.accountmanagementsystem.controller;

import com.imozayozturk.accountmanagementsystem.model.AccountDto;
import com.imozayozturk.accountmanagementsystem.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("create")
    public void createAccount(@RequestBody @Validated AccountDto accountDto) {
        accountService.createAccount(accountDto);
    }

    @GetMapping("{accountId}")
    public AccountDto getAccount(@PathVariable("accountId") Long accountId) {
        return AccountDto.fromEntity(accountService.getAccount(accountId));
    }

    @GetMapping("all/customer/{customerId}")
    public List<AccountDto> getAccounts(@PathVariable("customerId") Long customerId) {
        return accountService.getAccounts(customerId)
                .stream().map(AccountDto::fromEntity)
                .collect(Collectors.toList());
    }
}
