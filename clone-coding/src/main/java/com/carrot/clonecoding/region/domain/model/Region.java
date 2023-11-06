package com.carrot.clonecoding.region.domain.model;

import com.carrot.clonecoding.common.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "region")
public class Region extends BaseTimeEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long regionId;

    private int city;
    @Column(name="region_name")
    private String regionName;
}
