package com.genesis.gestioncontact.dto;

import lombok.Builder;

@Builder
public record CompanyDto(String name, String vatNumber, AddressDto address) {
}
