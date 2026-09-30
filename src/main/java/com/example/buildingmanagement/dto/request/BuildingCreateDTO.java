package com.example.buildingmanagement.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BuildingCreateDTO {
    @NotBlank
    @Size(max = 100)
    private String buildingName;

    @NotNull
    @Min(0)
    private Double buildingArea;

    @NotBlank
    @Size(max = 10)
    private String areaUnit;

    @NotNull
    private LocalDate startDate;

    @NotNull
    @Min(0)
    private Integer time;

    @NotBlank
    @Size(max = 10)
    private String timeUnit;

    @NotBlank
    private String design;

    @NotBlank
    private String content;

    @NotNull
    @Min(0)
    private Integer status = 1;
}
