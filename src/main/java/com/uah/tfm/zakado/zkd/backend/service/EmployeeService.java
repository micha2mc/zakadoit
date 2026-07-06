package com.uah.tfm.zakado.zkd.backend.service;

import com.uah.tfm.zakado.zkd.backend.data.entity.AreaEntity;
import com.uah.tfm.zakado.zkd.backend.data.entity.CompanyEntity;
import com.uah.tfm.zakado.zkd.backend.data.entity.LanguageEntity;
import com.uah.tfm.zakado.zkd.backend.data.mapper.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> findAllEmployees(final String strFilter);

    void saveEmployee(final EmployeeDTO employee);

    void deleteEmployee(final EmployeeDTO employee);

    List<CompanyEntity> findAllCompanies();

    List<AreaEntity> findAllArea();

    List<LanguageEntity> findAllLanguages();

    EmployeeDTO getEmployeeWithRelations(Long id);
}
