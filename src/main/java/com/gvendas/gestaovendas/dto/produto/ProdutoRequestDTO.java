package com.gvendas.gestaovendas.dto.produto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.gvendas.gestaovendas.entity.CategoriaEntity;
import com.gvendas.gestaovendas.entity.ProdutoEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Produto request DTO")
public class ProdutoRequestDTO {

	@ApiModelProperty(value = "Descrição")
	@NotBlank(message = "Descricao")
	@Length(min = 3, max = 50, message = "Descricao")
	private String descricao;

	@ApiModelProperty(value = "Quantidade")
	@NotNull(message = "Quantidade")
	private Integer quantidade;

	@ApiModelProperty(value = "Preço custo")
	@NotNull(message = "Preço custo")
	private BigDecimal precoCusto;

	@ApiModelProperty(value = "Preço venda")
	@NotNull(message = "Preço venda")
	private BigDecimal precoVenda;

	@ApiModelProperty(value = "Observação")
	@Length(max = 500, message = "Observação")
	private String observacao;

	public ProdutoEntity converterParaEntidade(Long codigoCategoria) {
		return new ProdutoEntity(descricao, quantidade, precoCusto, precoVenda, observacao,
				new CategoriaEntity(codigoCategoria));
	}

	public ProdutoEntity converterParaEntidade(Long codigoCategoria, Long codigoProduto) {
		return new ProdutoEntity(codigoProduto, descricao, quantidade, precoCusto, precoVenda, observacao,
				new CategoriaEntity(codigoCategoria));
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPrecoCusto() {
		return precoCusto;
	}

	public void setPrecoCusto(BigDecimal precoCusto) {
		this.precoCusto = precoCusto;
	}

	public BigDecimal getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(BigDecimal precoVenda) {
		this.precoVenda = precoVenda;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

}
