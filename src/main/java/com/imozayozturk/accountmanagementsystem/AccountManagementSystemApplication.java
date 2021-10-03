package com.imozayozturk.accountmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableFeignClients
@EnableTransactionManagement
@EnableJpaAuditing
public class AccountManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountManagementSystemApplication.class, args);
    }

}
