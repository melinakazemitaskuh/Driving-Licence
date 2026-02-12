package com.example.drivingLicenceID.mapper;

import com.example.drivingLicenceID.dto.DrivingLicenseDto;
import com.example.drivingLicenceID.model.DrivingLicense;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DrivingLicenseMapper {

    DrivingLicenseDto toDto(DrivingLicense drivingLicense);
    DrivingLicense toEntity(DrivingLicenseDto drivingLicenseDto);
}
