package com.genesis.gestioncontact.repository;

import com.genesis.gestioncontact.entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    CompanyEntity findByVatNumber(String vatNumber);
}
