package com.imozayozturk.accountmanagementsystem.controller;

import com.imozayozturk.accountmanagementsystem.domain.Customer;
import com.imozayozturk.accountmanagementsystem.domain.Transaction;
import com.imozayozturk.accountmanagementsystem.model.TransactionDto;
import com.imozayozturk.accountmanagementsystem.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("send")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionDto sendMoney(@RequestBody @Validated TransactionDto transactionDto) {
        return transactionService.sendMoney(transactionDto).toDto();
    }

    @GetMapping("filter/{startDate}/{endDate}")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionDto> filter(@PathVariable("startDate") Long startDate, @PathVariable("endDate") Long endDate){
        return transactionService.getTransactionsBetweenDates(new Date(startDate), new Date(endDate)).stream().map(Transaction::toDto)
                .collect(Collectors.toList());
    }

}
