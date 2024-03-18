package com.genesis.gestioncontact.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesis.gestioncontact.dto.AddressDto;
import com.genesis.gestioncontact.dto.CompanyDto;
import com.genesis.gestioncontact.dto.ContactDto;
import com.genesis.gestioncontact.service.contact.ContactService;
import com.genesis.gestioncontact.shared.ContactType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ContactService contactService;

    private ContactDto createContactDto(boolean includeVatNumber) {
        AddressDto addressDto =
                AddressDto.builder()
                        .city("Test")
                        .street("Rue du Test")
                        .country("BE")
                        .build();
        CompanyDto companyDto = CompanyDto.builder()
                .name("Test company")
                .address(addressDto)
                .vatNumber("123")
                .build();
        return ContactDto.builder()
                .address(addressDto)
                .lastName("Romain")
                .firstName("Simon")
                .companies(Collections.singletonList(companyDto))
                .type(ContactType.FREELANCE)
                .vatNumber(includeVatNumber ? "123" : null)
                .build();
    }

    @Test
    public void retrieveAllContactsTest() throws Exception {
        ContactDto contactDto = createContactDto(true);

        List<ContactDto> contactDtos = Collections.singletonList(contactDto);

        Mockito.when(contactService.getAllContacts()).thenReturn(contactDtos);

        String expected = objectMapper.writeValueAsString(contactDtos);

        this.mockMvc.perform(get("/api/contacts"))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    public void addContactTest() throws Exception {
        ContactDto contactDto = createContactDto(true);

        Mockito.when(contactService.createContact(Mockito.any(ContactDto.class))).thenReturn(Optional.of(contactDto));

        String expected = objectMapper.writeValueAsString(contactDto);

        this.mockMvc.perform(post("/api/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expected)
                )
                .andExpect(status().isCreated());
    }

    @Test
    public void addContactTest_BadRequest() throws Exception {
        ContactDto contactDto = createContactDto(false);

        Mockito.when(contactService.createContact(Mockito.any(ContactDto.class))).thenReturn(Optional.empty());

        String contactBody = objectMapper.writeValueAsString(contactDto);

        this.mockMvc.perform(post("/api/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contactBody)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldAddContactToCompany() throws Exception {
        Long contactId = 1L;
        Long companyId = 1L;

        ContactDto contactDto = createContactDto(true);

        Mockito.when(this.contactService.addContactToCompany(contactId, companyId))
                .thenReturn(Optional.of(contactDto));

        String expected = objectMapper.writeValueAsString(contactDto);

        this.mockMvc.perform(post("/api/contacts/add-to-company")
                        .param("contactId", contactId.toString())
                        .param("companyId", companyId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    public void modifyContactTest() throws Exception {
        ContactDto contactDto = createContactDto(true);

        Mockito.when(contactService.updateContact(Mockito.anyLong(), Mockito.any(ContactDto.class))).thenReturn(Optional.of(contactDto));

        String expected = objectMapper.writeValueAsString(contactDto);

        this.mockMvc.perform(put("/api/contacts/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expected)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    public void deleteContactTest() throws Exception {
        Mockito.doNothing().when(contactService).deleteContact(Mockito.anyLong());

        this.mockMvc.perform(delete("/api/contacts/123"))
                .andExpect(status().isNoContent());
    }
}
