package com.example.buildingmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "buildings")
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true, name = "building_name")
    private String buildingName;

    @Column(nullable = false, name = "building_area")
    private Double buildingArea;

    @Column(length = 10, nullable = false, name = "area_unit")
    private String areaUnit;

    @Column(nullable = false, name = "start_date")
    private LocalDate startDate;

    @Column(nullable = false)
    private Integer time;

    @Column(length = 10, nullable = false, name = "time_unit")
    private String timeUnit;

    @Column(nullable = false)
    private String design;

    @Column(nullable = false)
    private String content;

    private Integer status = 1;
}
