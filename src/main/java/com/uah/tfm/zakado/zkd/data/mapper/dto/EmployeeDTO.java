package com.uah.tfm.zakado.zkd.data.mapper.dto;

import com.uah.tfm.zakado.zkd.data.entity.Area;
import com.uah.tfm.zakado.zkd.data.entity.Company;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
}
