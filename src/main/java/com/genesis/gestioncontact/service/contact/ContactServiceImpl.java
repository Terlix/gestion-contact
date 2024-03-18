package com.genesis.gestioncontact.service.contact;

import com.genesis.gestioncontact.dto.ContactDto;
import com.genesis.gestioncontact.entities.CompanyEntity;
import com.genesis.gestioncontact.entities.ContactEntity;
import com.genesis.gestioncontact.mappers.ContactMapper;
import com.genesis.gestioncontact.repository.CompanyRepository;
import com.genesis.gestioncontact.repository.ContactRepository;
import com.genesis.gestioncontact.shared.ContactType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository, CompanyRepository companyRepository) {
        this.contactRepository = contactRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public List<ContactDto> getAllContacts() {
        return contactRepository.findAll().stream().map(ContactMapper::convertToDto).toList();
    }

    @Override
    public Optional<ContactDto> createContact(ContactDto contactDto) {
        if (isFreelanceWithInvalidVatNumber(contactDto))
            return Optional.empty();

        return Optional.of(ContactMapper.convertToDto(contactRepository.save(ContactMapper.convertToEntity(contactDto))));
    }

    private boolean isFreelanceWithInvalidVatNumber(ContactDto contactDto) {
        return contactDto.type() == ContactType.FREELANCE
                && (contactDto.vatNumber() == null || contactDto.vatNumber().isEmpty());
    }

    @Override
    public Optional<ContactDto> addContactToCompany(Long contactId, Long companyId) {
        Optional<ContactEntity> contactOptional = contactRepository.findById(contactId);
        Optional<CompanyEntity> companyOptional = companyRepository.findById(companyId);

        if (contactOptional.isPresent() && companyOptional.isPresent()) {
            ContactEntity contact = contactOptional.get();
            CompanyEntity company = companyOptional.get();
            if (!contact.getCompanies().contains(company)) {
                contact.getCompanies().add(company);
                return Optional.of(ContactMapper.convertToDto(contactRepository.save(contact)));
            }
            return Optional.of(ContactMapper.convertToDto(contact));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ContactDto> updateContact(Long id, ContactDto contactDto) {
        if (contactRepository.existsById(id)) {
            ContactEntity contactEntity = ContactMapper.convertToEntity(contactDto);
            contactEntity.setId(id);
            return Optional.of(ContactMapper.convertToDto(contactRepository.save(contactEntity)));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }
}