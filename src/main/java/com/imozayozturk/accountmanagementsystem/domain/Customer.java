package com.imozayozturk.accountmanagementsystem.domain;

import com.imozayozturk.accountmanagementsystem.model.CustomerDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "customer", uniqueConstraints = @UniqueConstraint(name = "identity_number_unique_constraint", columnNames = {"identityNumber"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Customer extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public String name;
    public String surname;
    public Integer identityNumber;
    public String countryIso;
    public Boolean status;

    public static Customer createCustomer(CustomerDto customerDto) {
        return Customer.builder()
                .countryIso(customerDto.getCountryIso())
                .identityNumber(customerDto.getIdentityNumber())
                .name(customerDto.getName())
                .surname(customerDto.getSurname())
                .status(true)
                .build();
    }

    public void updateCustomer(CustomerDto customerDto) {
        setCountryIso(customerDto.getCountryIso());
        setName(customerDto.getName());
        setIdentityNumber(customerDto.getIdentityNumber());
        setSurname(customerDto.getSurname());
    }

    public CustomerDto toDto() {
        return CustomerDto.builder()
                .id(getId())
                .countryIso(getCountryIso())
                .identityNumber(getIdentityNumber())
                .name(getName())
                .surname(getSurname())
                .status(getStatus())
                .build();
    }
}
