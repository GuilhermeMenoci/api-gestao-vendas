package com.gvendas.gestaovendas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	@Bean
	public Docket configuration() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis((Predicate<RequestHandler>) RequestHandlerSelectors.basePackage("com.gvendas"))
				.build()
				.apiInfo(informacaoApi());
	}

	private ApiInfo informacaoApi() {
		return new ApiInfoBuilder()
				.title("Gestão de Vendas")
				.description("Sistema de Gestão de vendas")
				.version("1.0.0")
				.build();
	}
	
}
