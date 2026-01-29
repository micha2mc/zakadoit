package com.uah.tfm.zakado.zkd.backend.data.repository;

import com.uah.tfm.zakado.zkd.backend.data.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
