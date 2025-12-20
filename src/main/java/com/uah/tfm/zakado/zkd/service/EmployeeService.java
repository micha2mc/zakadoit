package com.uah.tfm.zakado.zkd.service;

import com.uah.tfm.zakado.zkd.data.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAllEmployees(final String strFilter);

    void saveEmployee(final Employee employee);

    void deleteEmployee(final Employee employee);
}
