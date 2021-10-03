package com.imozayozturk.accountmanagementsystem.controller;

import com.imozayozturk.accountmanagementsystem.domain.Customer;
import com.imozayozturk.accountmanagementsystem.model.CustomerDto;
import com.imozayozturk.accountmanagementsystem.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("create")
    public CustomerDto create(@RequestBody @Validated CustomerDto customerDto) {
        return customerService.createCustomer(customerDto).toDto();
    }

    @GetMapping("{customerId}")
    public CustomerDto getCustomer(@PathVariable("customerId") Long customerId) {
        return customerService.getCustomer(customerId).toDto();
    }

    @GetMapping
    public List<CustomerDto> getCustomers() {
        return customerService.getCustomers()
                .stream().map(Customer::toDto)
                .collect(Collectors.toList());
    }

}
