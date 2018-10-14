package com.huowolf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = "com.huowolf.mapper")
@SpringBootApplication
@EnableCaching
public class PowerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PowerApplication.class, args);
	}
}
