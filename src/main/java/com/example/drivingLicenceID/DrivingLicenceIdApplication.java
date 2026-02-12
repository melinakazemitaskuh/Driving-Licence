package com.example.drivingLicenceID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DrivingLicenceIdApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrivingLicenceIdApplication.class, args);
    }

}
