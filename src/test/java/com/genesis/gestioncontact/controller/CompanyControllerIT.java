package com.genesis.gestioncontact.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesis.gestioncontact.dto.AddressDto;
import com.genesis.gestioncontact.dto.CompanyDto;
import com.genesis.gestioncontact.service.company.CompanyService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CompanyService companyService;

    private CompanyDto createTestCompanyDto() {
        AddressDto addressDto = AddressDto.builder()
                .city("Test")
                .street("Rue du Test")
                .zipCode("1000")
                .country("BE")
                .build();
        return CompanyDto.builder()
                .name("Test company")
                .address(addressDto)
                .vatNumber("123")
                .build();
    }

    @Test
    public void retrieveAllCompaniesTest() throws Exception {
        CompanyDto companyDto = createTestCompanyDto();

        List<CompanyDto> companyDtos = Collections.singletonList(companyDto);

        Mockito.when(companyService.getAllCompanies()).thenReturn(companyDtos);

        String expected = objectMapper.writeValueAsString(companyDtos);

        this.mockMvc.perform(get("/api/companies"))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    public void getCompanyByVATNumber_ExistingCompany_Returns200() throws Exception {
        CompanyDto companyDto = createTestCompanyDto();

        when(companyService.getCompanyByVATNumber(anyString())).thenReturn(Optional.of(companyDto));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/companies/vat/123"))
                .andExpect(status().isOk());
    }

    @Test
    public void getCompanyByVATNumber_NonExistingCompany_Returns404() throws Exception {
        when(companyService.getCompanyByVATNumber(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/companies/vat/123"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addCompanyTest() throws Exception {
        CompanyDto companyDto = createTestCompanyDto();

        Mockito.when(companyService.createCompany(Mockito.any(CompanyDto.class))).thenReturn(companyDto);

        String expected = objectMapper.writeValueAsString(companyDto);

        this.mockMvc.perform(post("/api/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expected)
                )
                .andExpect(status().isCreated())
                .andExpect(content().json(expected));
    }

    @Test
    public void modifyCompanyTest() throws Exception {
        CompanyDto companyDto = createTestCompanyDto();

        Mockito.when(companyService.updateCompany(Mockito.anyLong(), Mockito.any(CompanyDto.class))).thenReturn(Optional.of(companyDto));

        String expected = objectMapper.writeValueAsString(companyDto);

        this.mockMvc.perform(put("/api/companies/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expected)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }
}