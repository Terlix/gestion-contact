package com.genesis.gestioncontact.service.company;

import com.genesis.gestioncontact.dto.CompanyDto;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    List<CompanyDto> getAllCompanies();

    Optional<CompanyDto> getCompanyByVATNumber(String vatNumber);

    CompanyDto createCompany(CompanyDto companyEntity);

    Optional<CompanyDto> updateCompany(Long id, CompanyDto companyEntity);
}
