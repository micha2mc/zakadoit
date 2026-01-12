package com.uah.tfm.zakado.zkd.data.repository;

import com.uah.tfm.zakado.zkd.data.entity.Employee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /*@Query("""
            select e from Employee e 
            where lower(e.firstName) like lower(concat('%', :searchTerm, '%')) 
            or lower(e.lastName) like lower(concat('%', :searchTerm, '%'))
            or lower(e.corporateKey) like lower(concat('%', :searchTerm, '%'))
            """)
    List<Employee> searchEmployees(@Param("searchTerm") String searchTerm);*/

    @Query(""" 
            select e from Employee e 
            where e.corporateKey = :ck
            """)
    Employee findEmployeeByCK(String ck);

    // Usar JOIN FETCH para cargar relaciones en una sola query
    @Query("SELECT DISTINCT e FROM Employee e " +
            "LEFT JOIN FETCH e.company " +
            "LEFT JOIN FETCH e.area " +
            "WHERE LOWER(e.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR e.corporateKey LIKE CONCAT('%', :searchTerm, '%')")
    List<Employee> searchEmployees(@Param("filter") String searchTerm);
    @Override
    @EntityGraph(attributePaths = {"company", "area"})
    List<Employee> findAll();

    @EntityGraph(attributePaths = {"company", "area", "languages"})
    Optional<Employee> findWithAllRelationsById(Long id);
}
