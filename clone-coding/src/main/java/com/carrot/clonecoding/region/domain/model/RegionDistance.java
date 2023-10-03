package com.carrot.clonecoding.region.domain.model;


import jakarta.persistence.*;

@Entity
@Table(name = "region_distance")
public class RegionDistance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int distanceId;

    @ManyToOne
    @JoinColumn(name = "base_region_id")
    private Region baseRegion;

    @ManyToOne
    @JoinColumn(name = "nearby_region_id")
    private Region nearRegion;

    private double distance;

}
