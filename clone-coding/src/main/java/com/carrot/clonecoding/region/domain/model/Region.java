package com.carrot.clonecoding.region.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "region")
@Getter
@Setter
public class Region
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int regionId;

    private int city;
    private String regionName;
}
