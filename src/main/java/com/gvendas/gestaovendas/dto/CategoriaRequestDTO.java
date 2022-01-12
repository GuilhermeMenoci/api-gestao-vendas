package com.gvendas.gestaovendas.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.gvendas.gestaovendas.entity.CategoriaEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Categoria request DTO")
public class CategoriaRequestDTO {

	@ApiModelProperty(value = "Nome")
	@NotBlank(message = "Nome")
	@Length(min = 3, max = 50, message = "Nome")
	private String nome;
	
	public CategoriaEntity converterParaEntidade() {
		return new CategoriaEntity(nome);
	}
	
	public CategoriaEntity converterParaEntidade(Long codigo) {
		return new CategoriaEntity(codigo, nome);
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
}
