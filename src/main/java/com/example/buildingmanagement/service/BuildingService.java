package com.example.buildingmanagement.service;

import com.example.buildingmanagement.dto.request.BuildingCreateDTO;
import com.example.buildingmanagement.dto.request.BuildingStatusUpdateDTO;
import com.example.buildingmanagement.dto.request.BuildingUpdateDTO;
import com.example.buildingmanagement.dto.response.BuildingResponseDTO;
import com.example.buildingmanagement.dto.response.PageResponse;
import com.example.buildingmanagement.entity.Building;
import com.example.buildingmanagement.exception.DuplicatedArgumentException;
import com.example.buildingmanagement.exception.FinishedBuildingStatusException;
import com.example.buildingmanagement.exception.ResourceNotFoundException;
import com.example.buildingmanagement.repository.BuildingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingRepository buildingRepository;

    private BuildingResponseDTO mapToDTO(Building building) {
        return new BuildingResponseDTO(
                building.getId(),
                building.getBuildingName(),
                building.getBuildingArea(),
                building.getAreaUnit(),
                building.getStartDate(),
                building.getTime(),
                building.getTimeUnit(),
                building.getDesign(),
                building.getContent(),
                building.getStatus()
        );
    }

    private PageResponse<BuildingResponseDTO> mapToPageResponse(Page<Building> page) {
        return new PageResponse<>(
                page.getContent().stream().map(this::mapToDTO).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    public PageResponse<BuildingResponseDTO> getAllBuildings(
            String buildingName,
            Integer status,
            Pageable pageable
    ) {
        Page<Building> buildings = buildingRepository.findAllByBuildingNameOrStatus(buildingName, status, pageable);

        return mapToPageResponse(buildings);
    }

    public void addBuilding(BuildingCreateDTO buildingCreateDTO) {
        if (buildingRepository.existsByBuildingName(buildingCreateDTO.getBuildingName())) {
            throw new DuplicatedArgumentException("Building name already exists");
        }

        Building building = new Building();
        building.setBuildingName(buildingCreateDTO.getBuildingName());
        building.setBuildingArea(buildingCreateDTO.getBuildingArea());
        building.setAreaUnit(buildingCreateDTO.getAreaUnit());
        building.setStartDate(buildingCreateDTO.getStartDate());
        building.setTime(buildingCreateDTO.getTime());
        building.setTimeUnit(buildingCreateDTO.getTimeUnit());
        building.setDesign(buildingCreateDTO.getDesign());
        building.setContent(buildingCreateDTO.getContent());
        building.setStatus(buildingCreateDTO.getStatus());
        buildingRepository.save(building);
    }

    public void updateBuilding(Long id, BuildingUpdateDTO buildingUpdateDTO) {
        Building building = buildingRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Building not found")
        );

        if (buildingRepository.existsByBuildingName(buildingUpdateDTO.getBuildingName())) {
            throw new DuplicatedArgumentException("Building name couldn't be duplicated with other building name");
        }

        building.setBuildingName(buildingUpdateDTO.getBuildingName());
        building.setBuildingArea(buildingUpdateDTO.getBuildingArea());
        building.setAreaUnit(buildingUpdateDTO.getAreaUnit());
        building.setStartDate(buildingUpdateDTO.getStartDate());
        building.setTime(buildingUpdateDTO.getTime());
        building.setTimeUnit(buildingUpdateDTO.getTimeUnit());
        building.setDesign(buildingUpdateDTO.getDesign());
        building.setContent(buildingUpdateDTO.getContent());
        building.setStatus(buildingUpdateDTO.getStatus());
        buildingRepository.save(building);
    }

    public void deleteBuilding(Long id) {
        Building building = buildingRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Building not found")
        );

        buildingRepository.delete(building);
    }

    public void updateBuildingStatus(Long id, BuildingStatusUpdateDTO buildingStatusUpdateDTO) {
        Building building = buildingRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Building not found")
        );

        if (building.getStatus() == 2) {
            throw new FinishedBuildingStatusException("Building is already finished");
        }

        if (buildingStatusUpdateDTO.getStatus() > 2) {
            throw new IllegalArgumentException("Building status must be 1 or 2 or 0");
        }

        building.setStatus(buildingStatusUpdateDTO.getStatus());
        buildingRepository.save(building);
    }
}
