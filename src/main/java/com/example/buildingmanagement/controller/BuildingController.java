package com.example.buildingmanagement.controller;

import com.example.buildingmanagement.dto.request.BuildingCreateDTO;
import com.example.buildingmanagement.dto.request.BuildingStatusUpdateDTO;
import com.example.buildingmanagement.dto.request.BuildingUpdateDTO;
import com.example.buildingmanagement.dto.response.ApiResponse;
import com.example.buildingmanagement.dto.response.BuildingResponseDTO;
import com.example.buildingmanagement.dto.response.PageResponse;
import com.example.buildingmanagement.service.BuildingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/buildings")
@RequiredArgsConstructor
public class BuildingController {
    private final BuildingService buildingService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<BuildingResponseDTO>>> getAllBuildings(
            @RequestParam(required = false) String buildingName,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDir
    ) {
        if (buildingName == null) buildingName = "";
        if (status == null) status = 1;
        if (page < 0) page = 0;
        if (size <= 0) size = 5;
        Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        PageResponse<BuildingResponseDTO> response = buildingService.getAllBuildings(buildingName, status, pageable);

        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "fetched data successfully",
                response,
                null,
                200
        ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createBuilding(
            @Valid @RequestBody BuildingCreateDTO buildingCreateDTO
    ) {
        buildingService.addBuilding(buildingCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(
                        true,
                        "building created successfully",
                        null,
                        null,
                        201
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateBuilding(
            @PathVariable Long id,
            @Valid @RequestBody BuildingUpdateDTO buildingUpdateDTO
    ) {
        buildingService.updateBuilding(id, buildingUpdateDTO);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "building updated successfully",
                null,
                null,
                200
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBuilding(
            @PathVariable Long id
    ) {
        buildingService.deleteBuilding(id);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "building deleted successfully",
                null,
                null,
                200
        ));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Void>> patchBuilding(
            @PathVariable Long id,
            @Valid @RequestBody BuildingStatusUpdateDTO buildingStatusUpdateDTO
    ) {
        buildingService.updateBuildingStatus(id, buildingStatusUpdateDTO);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "building status updated successfully",
                null,
                null,
                200
        ));
    }
}
