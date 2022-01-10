package com.gvendas.gestaovendas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EntityScan(basePackages = {"com.gvendas.gestaovendas.entity"})
@EnableJpaRepositories(basePackages = {"com.gvendas.gestaovendas.repository"})
@ComponentScan(basePackages = {"com.gvendas.gestaovendas.service", "com.gvendas.gestaovendas.controller", "com.gvendas.gestaovendas.exception"})
@SpringBootApplication
@EnableSwagger2
public class GestaoVendasApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoVendasApplication.class, args);
	}

}
