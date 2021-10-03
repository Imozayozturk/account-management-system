package com.imozayozturk.accountmanagementsystem.repository;

import com.imozayozturk.accountmanagementsystem.domain.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {

    @Query("select t from Transaction t where t.createdDate  BETWEEN ?1 AND ?2")
    Optional<List<Transaction>> findTransactionByCreatedDateBetweenAndOrderByCreatedDate(Date startDate, Date endDate);


}
