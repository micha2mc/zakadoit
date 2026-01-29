package com.uah.tfm.zakado.zkd.backend.service;

import com.uah.tfm.zakado.zkd.backend.data.entity.Area;
import com.uah.tfm.zakado.zkd.backend.data.entity.Company;
import com.uah.tfm.zakado.zkd.backend.data.entity.Language;
import com.uah.tfm.zakado.zkd.backend.data.mapper.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> findAllEmployees(final String strFilter);

    void saveEmployee(final EmployeeDTO employee);

    void deleteEmployee(final EmployeeDTO employee);

    List<Company> findAllCompanies();

    List<Area> findAllArea();

    List<Language> findAllLanguages();

    EmployeeDTO getEmployeeWithRelations(Long id);
}
