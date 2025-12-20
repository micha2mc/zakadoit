package com.uah.tfm.zakado.zkd.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@EqualsAndHashCode(callSuper = true, exclude = {"company", "area"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"company", "area"})
public class Employee extends AbstractEntity {

    private String corporateKey;
    private String firstName;
    private String lastName;

    @Email
    @NotEmpty
    private String email;

    @Column(name = "date_of_birth")
    private LocalDate dob;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @NotNull
    @JsonIgnoreProperties({"employees"})
    private Company company;


    @NotNull
    @ManyToOne
    private Area area;

}
