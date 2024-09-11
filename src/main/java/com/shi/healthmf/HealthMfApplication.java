package com.shi.healthmf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@MapperScan(basePackages = {"com.shi.healthmf.mapper"})
@ConfigurationPropertiesScan(basePackages = {"com.shi.healthmf.config.properties"})
@SpringBootApplication
public class HealthMfApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthMfApplication.class, args);
    }

}
