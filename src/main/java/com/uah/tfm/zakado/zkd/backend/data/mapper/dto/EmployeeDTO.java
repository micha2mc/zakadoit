package com.uah.tfm.zakado.zkd.backend.data.mapper.dto;

import com.uah.tfm.zakado.zkd.backend.data.entity.Area;
import com.uah.tfm.zakado.zkd.backend.data.entity.Company;
import com.uah.tfm.zakado.zkd.backend.data.entity.Language;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String firstName;

    @NotEmpty
    private String lastName;

    private String email;

    @NotNull
    private LocalDate dob;

    @NotNull
    private Company company;

    @NotNull
    private Area area;

    private String career;

    private Set<Language> languages = new HashSet<>();
}
