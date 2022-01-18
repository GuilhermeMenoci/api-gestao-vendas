package com.gvendas.gestaovendas.dto.cliente;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.gvendas.gestaovendas.entity.ClienteEntity;
import com.gvendas.gestaovendas.entity.Endereco;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Cliente request DTO")
public class ClienteRequestDTO {

	@ApiModelProperty(value = "Nome")
	@NotBlank(message = "Nome")
	@Length(min = 3, max = 50, message = "Nome") 
	private String nome;

	@ApiModelProperty(value = "Telefone")
	@NotBlank(message = "Telefone")
	@Pattern(regexp = "\\([\\d]{2}\\)[\\d]{5}[- .][\\d]{4}", message = "Telefone")
	private String telefone;

	@ApiModelProperty(value = "Ativo")
	@NotNull(message = "Ativo")
	private Boolean ativo;

	@ApiModelProperty(value = "Endereco")
	@NotNull(message = "endereco")
	@Valid
	private EnderecoRequestDTO endereco;

	public ClienteEntity converterParaEntidade() {
		Endereco enderecoEntity = new Endereco(endereco.getLogradouro(), endereco.getNumero(),
				endereco.getComplemento(), endereco.getBairro(), endereco.getCep(), endereco.getCidade(),
				endereco.getEstado());
		return new ClienteEntity(nome, telefone, ativo, enderecoEntity);
	}
	
	public ClienteEntity converterParaEntidade(Long codigo) {
		Endereco enderecoEntity = new Endereco(endereco.getLogradouro(), endereco.getNumero(),
				endereco.getComplemento(), endereco.getBairro(), endereco.getCep(), endereco.getCidade(),
				endereco.getEstado());
		return new ClienteEntity(codigo, nome, telefone, ativo, enderecoEntity);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public EnderecoRequestDTO getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoRequestDTO endereco) {
		this.endereco = endereco;
	}

	public EnderecoRequestDTO getEnderecoDto() {
		return endereco;
	}

	public void setEnderecoDto(EnderecoRequestDTO enderecoDto) {
		this.endereco = enderecoDto;
	}
	
	

}
