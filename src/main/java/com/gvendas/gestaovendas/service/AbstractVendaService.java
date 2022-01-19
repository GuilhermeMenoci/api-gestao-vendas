package com.gvendas.gestaovendas.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.gvendas.gestaovendas.dto.venda.ClienteVendaResponseDTO;
import com.gvendas.gestaovendas.dto.venda.ItemVendaRequestDTO;
import com.gvendas.gestaovendas.dto.venda.ItemVendaResponseDTO;
import com.gvendas.gestaovendas.dto.venda.VendaResponseDTO;
import com.gvendas.gestaovendas.entity.ItemVenda;
import com.gvendas.gestaovendas.entity.ProdutoEntity;
import com.gvendas.gestaovendas.entity.VendaEntity;

public abstract class AbstractVendaService {
	
	protected VendaResponseDTO criandoVendaResponseDTO(VendaEntity venda, List<ItemVenda> itensVendaList) {
		List<ItemVendaResponseDTO> itensVenda = itensVendaList
				.stream().map(this::criandoItensVendaResponseDTO).collect(Collectors.toList());
		return new VendaResponseDTO(venda.getCodigo(), venda.getData(), itensVenda);
	}
	
	protected ItemVendaResponseDTO criandoItensVendaResponseDTO(ItemVenda venda) {
		return new ItemVendaResponseDTO(venda.getCodigo(), venda.getQuantidade(), venda.getPrecoVendido(), 
				venda.getProduto().getCodigo(), venda.getProduto().getDescricao());
	}
	
	protected ClienteVendaResponseDTO retornandoClienteVendaResponseDto(VendaEntity venda, List<ItemVenda> itemVendaList) {
		return new ClienteVendaResponseDTO(venda.getCliente().getNome(), Arrays
				.asList(criandoVendaResponseDTO(venda, itemVendaList)));
	}
	
	protected ItemVenda criandoItemVenda(ItemVendaRequestDTO itemVendaDto, VendaEntity venda) {
		return new ItemVenda(itemVendaDto.getQuantidade(), itemVendaDto.getPrecoVendido(),
				new ProdutoEntity(itemVendaDto.getCodigoProduto()), venda);
	}
	
}
