package com.uah.tfm.zakado.zkd.service.impl;

import com.uah.tfm.zakado.zkd.data.entity.Area;
import com.uah.tfm.zakado.zkd.data.entity.Company;
import com.uah.tfm.zakado.zkd.data.entity.Employee;
import com.uah.tfm.zakado.zkd.data.mapper.EmployeeMapper;
import com.uah.tfm.zakado.zkd.data.mapper.dto.EmployeeDTO;
import com.uah.tfm.zakado.zkd.data.repository.AreaRepository;
import com.uah.tfm.zakado.zkd.data.repository.CompanyRepository;
import com.uah.tfm.zakado.zkd.data.repository.EmployeeRepository;
import com.uah.tfm.zakado.zkd.exception.EmployeeEmptyException;
import com.uah.tfm.zakado.zkd.service.EmployeeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AreaRepository areaRepository;
    private final CompanyRepository companyRepository;
    private final EmployeeMapper mapper;

    @Transactional
    public List<EmployeeDTO> findAllEmployees(final String strFilter) {

        if (strFilter.isBlank()) {
            return mapperAllEmployee(employeeRepository.findAll());
        }
        return mapperAllEmployee(employeeRepository.searchEmployees(strFilter));
    }


    public void saveEmployee(final EmployeeDTO employeeDTO) {
        if (employeeDTO == null) {
            String message = "You're trying to save an empty employee";
            log.error(message);
            throw new EmployeeEmptyException(message);
        }

        if(Objects.isNull(employeeDTO.getId())){
            employeeDTO.setCorporateKey(generateCorporateKey());
        }
        employeeDTO.setEmail(generateEmail(employeeDTO));
        employeeRepository.save(mapper.toEntity(employeeDTO));
    }

    @Transactional
    public void deleteEmployee(final EmployeeDTO employeeDTO) {
        employeeRepository.delete(mapper.toEntity(employeeDTO));
    }

    @Override
    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public List<Area> findAllArea() {
        return areaRepository.findAll();
    }

    private List<EmployeeDTO> mapperAllEmployee(final List<Employee> allEmployee) {
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        for (Employee employee : allEmployee) {
            employeeDTOList.add(mapper.toDTO(employee));
        }
        return employeeDTOList;
    }

    private String generateEmail(final EmployeeDTO employeeDTO) {
        String firstName = employeeDTO.getFirstName().trim().toLowerCase().replaceAll("\\s", "");
        //String lastName = employeeDTO.getLastName().trim().toLowerCase().replaceAll("\\s", "");
        return firstName + "@" + "zkdit.com";
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
