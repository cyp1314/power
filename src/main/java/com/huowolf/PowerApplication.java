package com.huowolf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = "com.huowolf.mapper")
@SpringBootApplication
@EnableCaching
public class PowerApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PowerApplication.class);
    }

    public static void main(String[] args) {
		SpringApplication.run(PowerApplication.class, args);
	}
}
