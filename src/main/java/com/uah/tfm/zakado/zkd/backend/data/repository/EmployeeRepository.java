package com.uah.tfm.zakado.zkd.backend.data.repository;

import com.uah.tfm.zakado.zkd.backend.data.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    @Query(""" 
            select e from EmployeeEntity e 
            where e.corporateKey = :ck
            """)
    EmployeeEntity findEmployeeByCK(String ck);


    @Query("""
            SELECT DISTINCT e FROM EmployeeEntity e 
            LEFT JOIN FETCH e.company LEFT JOIN FETCH e.area
            WHERE LOWER(e.fullName) LIKE LOWER(CONCAT('%', :filter, '%'))
            OR LOWER(e.corporateKey) LIKE LOWER(CONCAT('%', :filter, '%'))
            """)
    List<EmployeeEntity> searchEmployees(@Param("filter") String searchTerm);

    @EntityGraph(attributePaths = {"company", "area"})
    @Override
    List<EmployeeEntity> findAll();

    @EntityGraph(attributePaths = {"company", "area", "languages"})
    Optional<EmployeeEntity> findWithAllRelationsById(Long id);
}
