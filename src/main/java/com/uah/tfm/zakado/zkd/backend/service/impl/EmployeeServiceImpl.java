package com.uah.tfm.zakado.zkd.backend.service.impl;

import com.uah.tfm.zakado.zkd.backend.data.entity.Area;
import com.uah.tfm.zakado.zkd.backend.data.entity.Company;
import com.uah.tfm.zakado.zkd.backend.data.entity.Employee;
import com.uah.tfm.zakado.zkd.backend.data.entity.Language;
import com.uah.tfm.zakado.zkd.backend.data.mapper.EmployeeMapper;
import com.uah.tfm.zakado.zkd.backend.data.mapper.dto.EmployeeDTO;
import com.uah.tfm.zakado.zkd.backend.data.repository.AreaRepository;
import com.uah.tfm.zakado.zkd.backend.data.repository.CompanyRepository;
import com.uah.tfm.zakado.zkd.backend.data.repository.EmployeeRepository;
import com.uah.tfm.zakado.zkd.backend.data.repository.LanguageRepository;
import com.uah.tfm.zakado.zkd.backend.exception.EmployeeEmptyException;
import com.uah.tfm.zakado.zkd.backend.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AreaRepository areaRepository;
    private final CompanyRepository companyRepository;
    private final LanguageRepository languageRepository;
    private final EmployeeMapper mapper;

    @Transactional
    public List<EmployeeDTO> findAllEmployees(final String strFilter) {

        List<Employee> employees;
        if (strFilter.isBlank()) {
            employees = employeeRepository.findAll();
        } else {
            employees = employeeRepository.searchEmployees(strFilter);
        }
        return employees.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }


    @Transactional
    public void saveEmployee(final EmployeeDTO employeeDTO) {
        if (employeeDTO == null) {
            String message = "You're trying to save an empty employee";
            log.error(message);
            throw new EmployeeEmptyException(message);
        }

        if (Objects.isNull(employeeDTO.getId())) {
            employeeDTO.setCorporateKey(generateCorporateKey());
        }
        employeeRepository.save(mapper.toEntity(employeeDTO));
    }

    @Transactional
    public void deleteEmployee(final EmployeeDTO employeeDTO) {

        Employee employee = employeeRepository
                .findById(employeeDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        for (Language language : employee.getLanguages()) {
            language.getEmployees().remove(employee);
        }
        employeeRepository.delete(employee);
    }

    @Override
    @Transactional
    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    @Transactional
    public List<Area> findAllArea() {
        return areaRepository.findAll();
    }

    @Override
    @Transactional
    public List<Language> findAllLanguages() {
        return languageRepository.findAll();
    }

    @Override
    public EmployeeDTO getEmployeeWithRelations(Long id) {
        return employeeRepository.findWithAllRelationsById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
    }


    private String generateCorporateKey() {

        String ck = getCorporateKeys();
        Employee employee = employeeRepository.findEmployeeByCK(ck);
        return employee == null ? ck : generateCorporateKey();
    }


    private static String getCorporateKeys() {
        Random random = new SecureRandom();
        final String LETTERS_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String DIGITS = "0123456789";
        return "" + LETTERS_UPPERCASE.charAt(random.nextInt(LETTERS_UPPERCASE.length()))
                + LETTERS_UPPERCASE.charAt(random.nextInt(LETTERS_UPPERCASE.length()))
                + DIGITS.charAt(random.nextInt(DIGITS.length()))
                + DIGITS.charAt(random.nextInt(DIGITS.length()))
                + LETTERS_UPPERCASE.charAt(random.nextInt(LETTERS_UPPERCASE.length()))
                + LETTERS_UPPERCASE.charAt(random.nextInt(LETTERS_UPPERCASE.length()));
    }
}
