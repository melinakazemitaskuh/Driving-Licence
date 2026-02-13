package com.example.drivingLicenceID.repository;

import com.example.drivingLicenceID.model.DrivingLicense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface DrivingLicenseRepository extends JpaRepository<DrivingLicense, Long> {

    @Query(
            value = "SELECT * FROM driving_licenses WHERE deleted = true",
            countQuery = "SELECT count(*) FROM driving_licenses WHERE deleted = true",
            nativeQuery = true
    )
    Page<DrivingLicense> findAllSoftDeletedLicenses(Pageable pageable);

    @Query(
            value = "SELECT * FROM driving_licenses",
            countQuery = "SELECT count(*) FROM driving_licenses",
            nativeQuery = true
    )
    Page<DrivingLicense> findAllIncludingSoftDeleted(Pageable pageable);

    // Restore soft-deleted record
    @Transactional
    @Modifying
    @Query(value = "UPDATE driving_licenses SET deleted = false WHERE id = :id", nativeQuery = true)
    void restoreSoftDeletedLicenseById(@Param("id") Long id);

    // others

    Page<DrivingLicense> findActiveLicensesByFamilyAndName(String family, String name, Pageable pageable);

    boolean existsByDrivingLicenseNumber(String drivingLicenseNumber);

}
