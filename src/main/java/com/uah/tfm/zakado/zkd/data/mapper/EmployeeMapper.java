package com.uah.tfm.zakado.zkd.data.mapper;

import com.uah.tfm.zakado.zkd.data.entity.Area;
import com.uah.tfm.zakado.zkd.data.entity.Company;
import com.uah.tfm.zakado.zkd.data.entity.Employee;
import com.uah.tfm.zakado.zkd.data.mapper.dto.EmployeeDTO;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public EmployeeDTO toDTO (Employee employee){
        Area area = new Area();
        area.setId(employee.getArea().getId());
        area.setName(employee.getArea().getName());

        Company company = new Company();
        company.setId(employee.getCompany().getId());
        company.setName(employee.getCompany().getName());
        return EmployeeDTO.builder()
                .id(employee.getId())
                .corporateKey(employee.getCorporateKey())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .dob(employee.getDob())
                .area(area)
                .company(company)
                .build();
    }

    public Employee toEntity (EmployeeDTO employeeDTO){
        Area area = new Area();
        area.setId(employeeDTO.getArea().getId());
        area.setName(employeeDTO.getArea().getName());

        Company company = new Company();
        company.setId(employeeDTO.getCompany().getId());
        company.setName(employeeDTO.getCompany().getName());
        return Employee.builder()
                .id(employeeDTO.getId())
                .corporateKey(employeeDTO.getCorporateKey())
                .firstName(employeeDTO.getFirstName())
                .lastName(employeeDTO.getLastName())
                .email(employeeDTO.getEmail())
                .dob(employeeDTO.getDob())
                .area(area)
                .company(company)
                .build();
    }
}
