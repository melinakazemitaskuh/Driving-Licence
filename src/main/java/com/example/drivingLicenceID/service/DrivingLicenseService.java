package com.example.drivingLicenceID.service;

import com.example.drivingLicenceID.dto.DrivingLicenseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DrivingLicenseService {

    void save(DrivingLicenseDto drivingLicenseDto);

    void update(DrivingLicenseDto drivingLicenseDto);

    void deleteById(Long id);

    DrivingLicenseDto findById(Long id);

    List<DrivingLicenseDto> findAll();

    Page<DrivingLicenseDto> findAll(Pageable pageable);

    Page<DrivingLicenseDto> findAllSoftDeleted(Pageable pageable);

    Page<DrivingLicenseDto> findAllIncludingSoftDeleted(Pageable pageable);

    void restoreSoftDeletedById(Long id);

    Page<DrivingLicenseDto> findByFamilyAndName(String family, String name, Pageable pageable);
}
