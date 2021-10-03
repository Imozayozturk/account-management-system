package com.imozayozturk.accountmanagementsystem.config.feign;

import feign.codec.Encoder;
import feign.form.FormEncoder;
import org.springframework.context.annotation.Bean;

public class FeignFormEncoder {
    @Bean
    public Encoder encoder() {
        return new FormEncoder();
    }
}
