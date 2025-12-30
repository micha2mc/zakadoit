package com.uah.tfm.zakado.zkd.data.repository;

import com.uah.tfm.zakado.zkd.data.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(""" 
            select e from Employee e 
            where lower(e.firstName) like lower(concat('%', :searchTerm, '%')) 
            or lower(e.lastName) like lower(concat('%', :searchTerm, '%'))
            or lower(e.corporateKey) like lower(concat('%', :searchTerm, '%'))
            """)
    List<Employee> searchEmployees(@Param("searchTerm") String searchTerm);

    @Query(""" 
            select e from Employee e 
            where e.corporateKey = :ck
            """)
    Employee findEmployeeByCK(String ck);
}
