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
    private Long town_id;

    @NotNull
    private String town_name;

    @NotNull
    private Double town_x;

    @NotNull
    private Double town_y;
}
