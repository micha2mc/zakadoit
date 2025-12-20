package com.uah.tfm.zakado.zkd.data.entity;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Area extends AbstractEntity {

    private String name;
}
