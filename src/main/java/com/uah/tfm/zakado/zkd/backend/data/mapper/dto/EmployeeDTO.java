package com.uah.tfm.zakado.zkd.backend.data.mapper.dto;

import com.uah.tfm.zakado.zkd.backend.data.entity.AreaEntity;
import com.uah.tfm.zakado.zkd.backend.data.entity.CompanyEntity;
import com.uah.tfm.zakado.zkd.backend.data.entity.LanguageEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;
    private String corporateKey;
    @NotEmpty
    private String fullName;
    private int yearOfExperience;
    private BigDecimal annualSalary;
    private String email;
    @NotNull
    private LocalDate dob;
    @NotNull
    private CompanyEntity company;
    @NotNull
    private AreaEntity area;
    private String career;
    private Set<LanguageEntity> languages = new HashSet<>();
}
