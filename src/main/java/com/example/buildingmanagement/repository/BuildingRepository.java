package com.example.buildingmanagement.repository;

import com.example.buildingmanagement.entity.Building;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BuildingRepository extends JpaRepository<Building, Long> {
    @Query("""
    select b
    from Building b
    where
        (:buildingName is null or lower(b.buildingName) like lower(concat('%', :buildingName, '%') ) ) or
        (:status is null or b.status = :status)
    """)
    Page<Building> findAllByBuildingNameOrStatus(
            @Param("buildingName") String buildingName,
            @Param("status") Integer status,
            Pageable pageable
    );

    boolean existsByBuildingName(String buildingName);
}
