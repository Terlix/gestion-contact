package com.genesis.gestioncontact.mappers;

import com.genesis.gestioncontact.dto.ContactDto;
import com.genesis.gestioncontact.entities.ContactEntity;

import java.util.Objects;

public class ContactMapper {
    public static ContactDto convertToDto(ContactEntity contact) {
        Objects.requireNonNull(contact, "ContactEntity cannot be null");

        return ContactDto.builder()
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .address(AddressMapper.convertToDto(contact.getAddress()))
                .companies(contact.getCompanies().stream().map(CompanyMapper::convertToDto).toList())
                .type(contact.getType())
                .vatNumber(contact.getVatNumber())
                .build();
    }

    public static ContactEntity convertToEntity(ContactDto contact) {
        Objects.requireNonNull(contact, "ContactDto cannot be null");

        return ContactEntity.builder()
                .firstName(contact.firstName())
                .lastName(contact.lastName())
                .address(AddressMapper.convertToEntity(contact.address()))
                .companies(contact.companies().stream().map(CompanyMapper::convertToEntity).toList())
                .type(contact.type())
                .vatNumber(contact.vatNumber())
                .build();
    }
}
