package com.carrot.clonecoding.region.domain.model;


import com.carrot.clonecoding.common.base.BaseTimeEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "region_distance")
public class RegionDistance extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "distance_id")
    private Long distanceId;

    @ManyToOne
    @JoinColumn(name = "base_region_id")
    private Region baseRegion;

    @ManyToOne
    @JoinColumn(name = "nearby_region_id")
    private Region nearRegion;

    private double distance;

}
