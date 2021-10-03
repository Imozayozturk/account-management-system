package com.imozayozturk.accountmanagementsystem.model;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data @Builder @ToString
public class CustomerDto {
    private Long id;
    @NotNull
    public String name;
    @NotNull
    public String surname;
    @NotNull
    public Integer identityNumber;
    @NotNull
    public String countryIso;
    private Boolean status;
}
