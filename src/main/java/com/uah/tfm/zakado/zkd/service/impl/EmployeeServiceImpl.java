package com.uah.tfm.zakado.zkd.service.impl;

import com.uah.tfm.zakado.zkd.data.entity.Employee;
import com.uah.tfm.zakado.zkd.data.repository.EmployeeRepository;
import com.uah.tfm.zakado.zkd.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<Employee> findAllEmployees(final String strFilter){
        if(strFilter.isBlank()){
           return employeeRepository.findAll();
        }
        return employeeRepository.searchEmployees(strFilter);
    }


    public void saveEmployee(final Employee employee) {
        if (employee == null) {
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        employeeRepository.save(employee);
    }

    public void deleteEmployee(final Employee employee) {
        employeeRepository.delete(employee);
    }
}
