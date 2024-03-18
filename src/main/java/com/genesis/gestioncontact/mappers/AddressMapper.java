package com.genesis.gestioncontact.mappers;

import com.genesis.gestioncontact.dto.AddressDto;
import com.genesis.gestioncontact.entities.AddressEntity;

import java.util.Objects;

public class AddressMapper {
    public static AddressDto convertToDto(AddressEntity address) {
        Objects.requireNonNull(address, "AddressEntity cannot be null");

        return AddressDto.builder()
                .city(address.getCity())
                .street(address.getStreet())
                .zipCode(address.getZipCode())
                .country(address.getCountry())
                .build();
    }

    public static AddressEntity convertToEntity(AddressDto address) {
        Objects.requireNonNull(address, "AddressDto cannot be null");

        return AddressEntity.builder()
                .city(address.city())
                .street(address.street())
                .zipCode(address.zipCode())
                .country(address.country())
                .build();
    }
}
