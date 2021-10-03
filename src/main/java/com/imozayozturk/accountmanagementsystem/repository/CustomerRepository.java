package com.imozayozturk.accountmanagementsystem.repository;
import com.imozayozturk.accountmanagementsystem.domain.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
}
