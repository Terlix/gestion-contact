package com.genesis.gestioncontact.service.company;

import com.genesis.gestioncontact.dto.CompanyDto;
import com.genesis.gestioncontact.entities.CompanyEntity;
import com.genesis.gestioncontact.mappers.CompanyMapper;
import com.genesis.gestioncontact.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<CompanyDto> getAllCompanies() {
        return companyRepository.findAll().stream().map(CompanyMapper::convertToDto).toList();
    }

    @Override
    public Optional<CompanyDto> getCompanyByVATNumber(String vatNumber) {
        return Optional.ofNullable(companyRepository.findByVatNumber(vatNumber))
                .map(CompanyMapper::convertToDto);
    }

    @Override
    public CompanyDto createCompany(CompanyDto companyDto) {
        return CompanyMapper.convertToDto(companyRepository.save(CompanyMapper.convertToEntity(companyDto)));
    }

    @Override
    public Optional<CompanyDto> updateCompany(Long id, CompanyDto companyDto) {
        if (companyRepository.existsById(id)) {
            CompanyEntity companyEntity = CompanyMapper.convertToEntity(companyDto);
            companyEntity.setId(id);
            return Optional.of(CompanyMapper.convertToDto(companyRepository.save(companyEntity)));
        } else {
            return Optional.empty();
        }
    }
}