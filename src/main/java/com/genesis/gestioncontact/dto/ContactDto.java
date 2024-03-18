package com.genesis.gestioncontact.dto;

import com.genesis.gestioncontact.shared.ContactType;
import lombok.Builder;

import java.util.List;

@Builder
public record ContactDto(String firstName, String lastName, AddressDto address, List<CompanyDto> companies,
                         ContactType type, String vatNumber) {
}
