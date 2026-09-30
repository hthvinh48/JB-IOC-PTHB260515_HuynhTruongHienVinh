package com.example.buildingmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BuildingResponseDTO {
    private Long id;
    private String buildingName;
    private Double buildingArea;
    private String areaUnit;
    private LocalDate startDate;
    private Integer time;
    private String timeUnit;
    private String design;
    private String content;
    private Integer status;
}
