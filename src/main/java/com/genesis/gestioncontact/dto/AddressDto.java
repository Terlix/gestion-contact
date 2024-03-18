package com.genesis.gestioncontact.dto;

import lombok.Builder;

@Builder
public record AddressDto(String street, String city, String zipCode, String country) {
}
