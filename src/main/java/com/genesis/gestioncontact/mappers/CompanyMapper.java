package com.genesis.gestioncontact.mappers;

import com.genesis.gestioncontact.dto.CompanyDto;
import com.genesis.gestioncontact.entities.CompanyEntity;

import java.util.Objects;

public class CompanyMapper {
    public static CompanyDto convertToDto(CompanyEntity company) {
        Objects.requireNonNull(company, "CompanyEntity cannot be null");

        return CompanyDto.builder()
                .name(company.getName())
                .vatNumber(company.getVatNumber())
                .address(AddressMapper.convertToDto(company.getAddress()))
                .build();
    }

    public static CompanyEntity convertToEntity(CompanyDto company) {
        Objects.requireNonNull(company, "CompanyDto cannot be null");

        return CompanyEntity.builder()
                .name(company.name())
                .vatNumber(company.vatNumber())
                .address(AddressMapper.convertToEntity(company.address()))
                .build();
    }
}
