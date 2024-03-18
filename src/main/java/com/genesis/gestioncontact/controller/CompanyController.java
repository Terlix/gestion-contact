package com.genesis.gestioncontact.controller;

import com.genesis.gestioncontact.dto.CompanyDto;
import com.genesis.gestioncontact.service.company.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Company Controller", description = "Endpoints for managing companies")
@RestController
@RequestMapping("/api/companies")
@Validated
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Operation(summary = "Get all companies", description = "This endpoint is used to retrieve all the companies")
    @GetMapping
    public List<CompanyDto> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @Operation(summary = "Get a company by VAT number", description = "This endpoint is used to retrieve a company by VAT number")
    @GetMapping("/vat/{vatNumber}")
    public ResponseEntity<CompanyDto> getCompanyByVATNumber(@Parameter(description = "VAT number of the company") @PathVariable String vatNumber) {
        Optional<CompanyDto> company = companyService.getCompanyByVATNumber(vatNumber);
        return company.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a company", description = "This endpoint is used to create a company")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyDto createCompany(@Valid @RequestBody CompanyDto companyDto) {
        return companyService.createCompany(companyDto);
    }

    @Operation(summary = "Update a company", description = "This endpoint is used to update a company")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCompany(
            @Parameter(description = "ID of the company to update") @PathVariable Long id,
            @Valid @RequestBody CompanyDto companyDto) {
        return companyService.updateCompany(id, companyDto).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}