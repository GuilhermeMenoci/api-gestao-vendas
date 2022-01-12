package com.gvendas.gestaovendas.dto;

import com.gvendas.gestaovendas.entity.CategoriaEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Categoria response DTO")
public class CategoriaResponseDTO {

	@ApiModelProperty(value = "Código")
	private Long codigo;
	@ApiModelProperty(value = "Nome")
	private String nome;
	
	public CategoriaResponseDTO(Long codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}
	
	public static CategoriaResponseDTO converterParaCategoriaDTO(CategoriaEntity categoria) {
		return new CategoriaResponseDTO(categoria.getCodigo(), categoria.getNome());
	}
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	
	
}
