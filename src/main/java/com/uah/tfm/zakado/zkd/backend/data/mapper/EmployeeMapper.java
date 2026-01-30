package com.uah.tfm.zakado.zkd.backend.data.mapper;

import com.uah.tfm.zakado.zkd.backend.data.entity.Area;
import com.uah.tfm.zakado.zkd.backend.data.entity.Company;
import com.uah.tfm.zakado.zkd.backend.data.entity.Employee;
import com.uah.tfm.zakado.zkd.backend.data.entity.Language;
import com.uah.tfm.zakado.zkd.backend.data.mapper.dto.EmployeeDTO;
import com.uah.tfm.zakado.zkd.backend.data.repository.AreaRepository;
import com.uah.tfm.zakado.zkd.backend.data.repository.CompanyRepository;
import com.uah.tfm.zakado.zkd.backend.data.repository.EmployeeRepository;
import com.uah.tfm.zakado.zkd.backend.data.repository.LanguageRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.LazyInitializationException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EmployeeMapper {
    private final CompanyRepository companyRepository;
    private final AreaRepository areaRepository;
    private final LanguageRepository languageRepository;
    private final EmployeeRepository employeeRepository;


    public EmployeeDTO toDTO(Employee employee) {
        Set<Language> languages =  safelyLoadLanguages(employee);
        return EmployeeDTO.builder()
                .id(employee.getId())
                .corporateKey(employee.getCorporateKey())
                .fullName(employee.getFullName())
                .yearOfExperience(employee.getYearOfExperience())
                .annualSalary(employee.getAnnualSalary())
                .email(employee.getEmail())
                .dob(employee.getDob())
                .area(employee.getArea())
                .company(employee.getCompany())
                .career(employee.getCareer())
                .languages(languages)
                .build();
    }


    public Employee toEntity(EmployeeDTO employeeDTO) {
        Employee employee;

        if (employeeDTO.getId() != null) {
            employee = employeeRepository.findById(employeeDTO.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

            // Actualizar campos básicos
            employee.setCorporateKey(employeeDTO.getCorporateKey());
            employee.setFullName(employeeDTO.getFullName());
            employee.setYearOfExperience(employeeDTO.getYearOfExperience());
            employee.setAnnualSalary(employeeDTO.getAnnualSalary());
            employee.setEmail(employeeDTO.getEmail().toUpperCase());
            employee.setDob(employeeDTO.getDob());
            employee.setCareer(employeeDTO.getCareer());
        } else {
            // Si es nuevo, crear entidad
            employee = Employee.builder()
                    .corporateKey(employeeDTO.getCorporateKey())
                    .fullName(employeeDTO.getFullName())
                    .yearOfExperience(employeeDTO.getYearOfExperience())
                    .annualSalary(employeeDTO.getAnnualSalary())
                    .email(employeeDTO.getEmail())
                    .dob(employeeDTO.getDob())
                    .career(employeeDTO.getCareer())
                    .build();
        }

        // 1. Company - cargar por ID
        if (employeeDTO.getCompany() != null && employeeDTO.getCompany().getId() != null) {
            Company company = companyRepository.findById(employeeDTO.getCompany().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Company not found"));
            employee.setCompany(company);
        } else {
            employee.setCompany(null);
        }

        if (employeeDTO.getArea() != null && employeeDTO.getArea().getId() != null) {
            Area area = areaRepository.findById(employeeDTO.getArea().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Area not found"));
            employee.setArea(area);
        }

        if (employeeDTO.getLanguages() != null) {
            Set<Long> languageIds = employeeDTO.getLanguages().stream()
                    .map(Language::getId)
                    .collect(Collectors.toSet());

            if (!languageIds.isEmpty()) {
                List<Language> languages = languageRepository.findAllById(languageIds);

                // Verificar que todas las lenguas existen
                if (languages.size() != languageIds.size()) {
                    throw new EntityNotFoundException("Some languages not found");
                }

                // Si es actualización, manejar relación bidireccional
                if (employee.getId() != null) {
                    for (Language oldLang : employee.getLanguages()) {
                        oldLang.getEmployees().remove(employee);
                    }
                    employee.getLanguages().clear();
                }

                // Establecer nuevas relaciones
                employee.getLanguages().addAll(languages);

                // Actualizar lado inverso
                for (Language lang : languages) {
                    lang.getEmployees().add(employee);
                }
            } else {
                // Si no hay languages, limpiar
                if (employee.getId() != null) {
                    for (Language oldLang : employee.getLanguages()) {
                        oldLang.getEmployees().remove(employee);
                    }
                    employee.getLanguages().clear();
                }
            }
        }

        return employee;
    }

    private Set<Language> safelyLoadLanguages(Employee employee) {
        // Opción 1: Si ya está inicializado, usarlo
        try {
            Set<Language> langs = employee.getLanguages();
            // Verificar si Hibernate devuelve una colección proxy no inicializada
            if (!Hibernate.isInitialized(langs)) {
                return new HashSet<>(); // Colección lazy no cargada
            }
            return langs != null ? langs : new HashSet<>();

        } catch (LazyInitializationException e) {
            log.debug("Lazy collection not loaded for employee {}", employee.getId());
            return new HashSet<>();
        }
    }

}
