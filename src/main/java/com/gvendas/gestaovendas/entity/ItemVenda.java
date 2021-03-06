package com.gvendas.gestaovendas.entity;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "item_venda")
public class ItemVenda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo")
	private Long codigo;

	@Column(name = "quantidade")
	private Integer quantidade;

	@Column(name = "preco_vendido")
	private BigDecimal precoVendido;

	@ManyToOne
	@JoinColumn(name = "codigo_produto", referencedColumnName = "codigo")
	private ProdutoEntity produto;

	@ManyToOne
	@JoinColumn(name = "codigo_venda", referencedColumnName = "codigo")
	private VendaEntity venda;

	public ItemVenda(Integer quantidade, BigDecimal precoVendido, ProdutoEntity produto, VendaEntity venda) {
		this.quantidade = quantidade;
		this.precoVendido = precoVendido;
		this.produto = produto;
		this.venda = venda;
	}

	public ItemVenda(Long codigo, Integer quantidade, BigDecimal precoVendido, ProdutoEntity produto,
			VendaEntity venda) {
		this.codigo = codigo;
		this.quantidade = quantidade;
		this.precoVendido = precoVendido;
		this.produto = produto;
		this.venda = venda;
	}

	public ItemVenda() {
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPrecoVendido() {
		return precoVendido;
	}

	public void setPrecoVendido(BigDecimal precoVendido) {
		this.precoVendido = precoVendido;
	}

	public ProdutoEntity getProduto() {
		return produto;
	}

	public void setProduto(ProdutoEntity produto) {
		this.produto = produto;
	}

	public VendaEntity getVenda() {
		return venda;
	}

	public void setVenda(VendaEntity venda) {
		this.venda = venda;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo, precoVendido, produto, quantidade, venda);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemVenda other = (ItemVenda) obj;
		return Objects.equals(codigo, other.codigo) && Objects.equals(precoVendido, other.precoVendido)
				&& Objects.equals(produto, other.produto) && Objects.equals(quantidade, other.quantidade)
				&& Objects.equals(venda, other.venda);
	}

}
