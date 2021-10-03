package com.imozayozturk.accountmanagementsystem.client;

import com.imozayozturk.accountmanagementsystem.config.feign.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Map;

@FeignClient(name = "fixerIoClient", url = "${feign.fixer-io.url}", configuration = FeignConfig.class)
public interface FixerIoClient {

    @GetMapping("/convert?apiKey=${feign.fixer-io.api-key}&compact=ultra")
    Map<String, BigDecimal> getRates(@RequestParam("q") String query);
}