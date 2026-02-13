package com.example.drivingLicenceID.service;

import com.example.drivingLicenceID.dto.DrivingLicenseDto;
import com.example.drivingLicenceID.mapper.DrivingLicenseMapper;
import com.example.drivingLicenceID.model.DrivingLicense;
import com.example.drivingLicenceID.repository.DrivingLicenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DrivingLicenseServiceImpl implements DrivingLicenseService {

    private final DrivingLicenseRepository drivingLicenseRepository;
    private final DrivingLicenseMapper drivingLicenseMapper;

    @Transactional
    @Override
    public void save(DrivingLicenseDto drivingLicenseDto) {
        DrivingLicense entity = drivingLicenseMapper.toEntity(drivingLicenseDto);
        drivingLicenseRepository.save(entity);
    }

    @Transactional
    @Override
    public void update(DrivingLicenseDto drivingLicenseDto) {
        if (drivingLicenseDto.getId() == null) {
            throw new IllegalArgumentException("ID cannot be null for update");
        }

        DrivingLicense entity = drivingLicenseMapper.toEntity(drivingLicenseDto);
        drivingLicenseRepository.save(entity);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        drivingLicenseRepository.deleteById(id); // soft delete
    }

    @Override
    public DrivingLicenseDto findById(Long id) {
        return drivingLicenseRepository.findById(id)
                .map(drivingLicenseMapper::toDto)
                .orElse(null);
    }

    @Override
    public List<DrivingLicenseDto> findAll() {
        return drivingLicenseRepository.findAll()
                .stream()
                .map(drivingLicenseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<DrivingLicenseDto> findAll(Pageable pageable) {
        return drivingLicenseRepository.findAll(pageable)
                .map(drivingLicenseMapper::toDto);
    }

    @Override
    public Page<DrivingLicenseDto> findAllSoftDeleted(Pageable pageable) {
        return drivingLicenseRepository.findAllSoftDeletedLicenses(pageable)
                .map(drivingLicenseMapper::toDto);
    }

    @Override
    public Page<DrivingLicenseDto> findAllIncludingSoftDeleted(Pageable pageable) {
        return drivingLicenseRepository.findAllIncludingSoftDeleted(pageable)
                .map(drivingLicenseMapper::toDto);
    }

    @Transactional
    @Override
    public void restoreSoftDeletedById(Long id) {
        drivingLicenseRepository.restoreSoftDeletedLicenseById(id);
    }

    @Override
    public Page<DrivingLicenseDto> findByFamilyAndName(String family, String name, Pageable pageable) {
        return drivingLicenseRepository.findActiveLicensesByFamilyAndName(family, name, pageable)
                .map(drivingLicenseMapper::toDto);
    }

    @Override
    public boolean existsByDrivingLicenseNumber(String drivingLicenseNumber) {
        return drivingLicenseRepository.existsByDrivingLicenseNumber(drivingLicenseNumber);
    }

}
