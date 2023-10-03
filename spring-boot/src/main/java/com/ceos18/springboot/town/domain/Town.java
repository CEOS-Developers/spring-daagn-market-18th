package com.ceos18.springboot.town.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Town {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "town_id")
    private Long id;

    @NotNull
    @Column(name = "town_name")
    private String name;

    @NotNull
    @Column(name = "town_x")
    private Double x;

    @NotNull
    @Column(name = "town_y")
    private Double y;
}
