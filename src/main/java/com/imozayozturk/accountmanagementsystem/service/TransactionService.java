package com.imozayozturk.accountmanagementsystem.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.imozayozturk.accountmanagementsystem.client.FixerIoClient;
import com.imozayozturk.accountmanagementsystem.domain.Account;
import com.imozayozturk.accountmanagementsystem.domain.Transaction;
import com.imozayozturk.accountmanagementsystem.domain.enums.CurrencyType;
import com.imozayozturk.accountmanagementsystem.model.TransactionDto;
import com.imozayozturk.accountmanagementsystem.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final FixerIoClient fixerIoClient;
    private LoadingCache<String, BigDecimal> exchangeRates;

    @PostConstruct
    public void init() {
        exchangeRates = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.DAYS)
                .maximumSize(5)
                .build(new CacheLoader<String, BigDecimal>() {
                    @Override
                    public BigDecimal load(String currencyPair) throws Exception {
                        Map<String, BigDecimal> rateMap = fixerIoClient.getRates(currencyPair);
                        log.info("Fixer.io exchange rates : {} ", rateMap);
                        return rateMap.get(currencyPair);
                    }
                });
    }

    public BigDecimal getRate(CurrencyType from, CurrencyType to) {
        return exchangeRates.getUnchecked(from.name().toUpperCase().concat("_").concat(to.name().toUpperCase()));
    }

    @Transactional
    public Transaction sendMoney(TransactionDto transactionDto) {
        Account fromAccount = accountService.getAccount(transactionDto.getFromAccountNumber());
        Account toAccount = accountService.getAccount(transactionDto.getToAccountNumber());

        Pair<BigDecimal, BigDecimal> calculateAmountWithExchangeRatePair = calculateAmountWithExchangeRate(fromAccount.getCurrencyType(), toAccount.getCurrencyType(), transactionDto.getAmount());

        Account fromUpdatedAccount = accountService.withDrawal(fromAccount.getId(), transactionDto.getAmount());
        Account toUpdatedAccount = accountService.deposit(toAccount.getId(), calculateAmountWithExchangeRatePair.getFirst());

        TransactionDto newTransaction = TransactionDto
                .builder()
                .fromAccountNumber(transactionDto.getFromAccountNumber())
                .fromCurrencyType(fromAccount.getCurrencyType())
                .fromAccountUpdatedBalance(fromUpdatedAccount.getBalance())
                .toAccountNumber(transactionDto.getToAccountNumber())
                .toCurrencyType(toAccount.getCurrencyType())
                .toAccountUpdatedBalance(toUpdatedAccount.getBalance())
                .amount(transactionDto.getAmount())
                .calculatedAmount(calculateAmountWithExchangeRatePair.getFirst())
                .exchangeRate(calculateAmountWithExchangeRatePair.getSecond())
                .build();
        return transactionRepository.save(Transaction.createTransaction(newTransaction));
    }


    public Pair<BigDecimal, BigDecimal> calculateAmountWithExchangeRate(CurrencyType base, CurrencyType to, BigDecimal amount) {
        BigDecimal currentRate = getRate(base, to);
        return Pair.of(currentRate.multiply(amount).setScale(2, RoundingMode.HALF_EVEN), currentRate);
    }

    public List<Transaction> getTransactionsBetweenDates(Date startDate, Date endDate) {
        return transactionRepository.findTransactionByCreatedDateBetweenAndOrderByCreatedDate(startDate, endDate).orElse(List.of());
    }

}
