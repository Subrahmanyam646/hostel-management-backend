package com.hostel.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HostelManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(HostelManagementApplication.class, args);
    }
}
