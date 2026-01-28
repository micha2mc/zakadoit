package com.uah.tfm.zakado.zkd.data.repository;

import com.uah.tfm.zakado.zkd.data.entity.Employee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(""" 
            select e from Employee e 
            where e.corporateKey = :ck
            """)
    Employee findEmployeeByCK(String ck);


    @Query("SELECT DISTINCT e FROM Employee e " +
            "LEFT JOIN FETCH e.company " +
            "LEFT JOIN FETCH e.area " +
            "WHERE LOWER(e.firstName) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR e.corporateKey LIKE CONCAT('%', :filter, '%')")
    List<Employee> searchEmployees(@Param("filter") String searchTerm);

    @EntityGraph(attributePaths = {"company", "area"})
    @Override
    List<Employee> findAll();

    @EntityGraph(attributePaths = {"company", "area", "languages"})
    Optional<Employee> findWithAllRelationsById(Long id);
}
