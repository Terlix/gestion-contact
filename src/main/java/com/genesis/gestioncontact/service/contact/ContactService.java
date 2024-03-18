package com.genesis.gestioncontact.service.contact;

import com.genesis.gestioncontact.dto.ContactDto;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    List<ContactDto> getAllContacts();

    Optional<ContactDto> createContact(ContactDto contactEntity);

    Optional<ContactDto> addContactToCompany(Long contactId, Long enterpriseId);

    Optional<ContactDto> updateContact(Long id, ContactDto contactEntity);

    void deleteContact(Long id);
}