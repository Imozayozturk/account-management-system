package com.imozayozturk.accountmanagementsystem.service;

import com.imozayozturk.accountmanagementsystem.domain.Customer;
import com.imozayozturk.accountmanagementsystem.model.CustomerDto;
import com.imozayozturk.accountmanagementsystem.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public List<Customer> getCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    public Customer createCustomer(CustomerDto customerDto) {
        return customerRepository.save(Customer.createCustomer(customerDto));
    }

    public void updateCustomer(Long customerId, CustomerDto customer) {
        customerRepository.findById(customerId)
                .ifPresentOrElse(customerEntity -> {
                            customerEntity.updateCustomer(customer);
                            customerRepository.save(customerEntity);
                        },
                        () -> {
                            throw new RuntimeException("Customer not found");
                        });
    }

    public void deleteCustomer(Long id){
        customerRepository.deleteById(id);
    }
}
