package com.imozayozturk.accountmanagementsystem.model.exchange;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Map;

@Data @Builder @ToString
public class ExchangeRateDto {
    private Boolean success;
    private String base;
    private Map<String, BigDecimal> rates;
}
