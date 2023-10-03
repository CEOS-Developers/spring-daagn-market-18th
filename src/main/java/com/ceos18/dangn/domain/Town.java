package com.ceos18.dangn.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Town extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "town_id")
    private Long id;

    @NotNull
    private String townName; //방배동
    @NotNull
    private String districtName; //서초구
    @NotNull
    private String stateName; //서울시

    @Builder
    public Town(String townName, String districtName, String stateName) {
        this.townName = townName;
        this.districtName = districtName;
        this.stateName = stateName;
    }
}
