package com.imozayozturk.accountmanagementsystem.repository;

import com.imozayozturk.accountmanagementsystem.domain.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);

    @Query("select c from Account c where c.customer.id = ?1")
    Optional<List<Account>> findAllByCustomer(Long customerId);
}
