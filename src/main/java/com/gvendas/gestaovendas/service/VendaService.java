package com.gvendas.gestaovendas.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gvendas.gestaovendas.dto.venda.ClienteVendaResponseDTO;
import com.gvendas.gestaovendas.dto.venda.ItemVendaResponseDTO;
import com.gvendas.gestaovendas.dto.venda.VendaResponseDTO;
import com.gvendas.gestaovendas.entity.ClienteEntity;
import com.gvendas.gestaovendas.entity.ItemVenda;
import com.gvendas.gestaovendas.entity.VendaEntity;
import com.gvendas.gestaovendas.exception.RegraNegocioException;
import com.gvendas.gestaovendas.repository.ItemVendaRepository;
import com.gvendas.gestaovendas.repository.VendaRepository;

@Service
public class VendaService {
	
	@Autowired
	private VendaRepository vendaRepository;

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ItemVendaRepository itemVendaRepository;
	
	public ClienteVendaResponseDTO listarVendaPorCliente(Long codigoCliente) {
		ClienteEntity cliente = validarClienteVendaExiste(codigoCliente);
		List<VendaResponseDTO> vendaResponseDtoList = vendaRepository.findByClienteCodigo(codigoCliente).stream()
		.map(this::criandoVendaResponseDTO).collect(Collectors.toList());
		return new ClienteVendaResponseDTO(cliente.getNome(), vendaResponseDtoList);
	}

	private ClienteEntity validarClienteVendaExiste(Long codigoCliente) {
		Optional<ClienteEntity> cliente = clienteService.listarPorCodigo(codigoCliente);
		if(cliente.isEmpty()) {
			throw new RegraNegocioException(String.format("O cliente de código %s informado não existe no cadastro!", 
					codigoCliente));
		}
		return cliente.get();
	}
	
	private VendaResponseDTO criandoVendaResponseDTO(VendaEntity venda) {
		List<ItemVendaResponseDTO> itensVenda = itemVendaRepository.findByVendaCodigo(venda.getCodigo())
				.stream().map(this::criandoItensVendaResponseDTO).collect(Collectors.toList());
		return new VendaResponseDTO(venda.getCodigo(), venda.getData(), itensVenda);
	}
	
	private ItemVendaResponseDTO criandoItensVendaResponseDTO(ItemVenda venda) {
		return new ItemVendaResponseDTO(venda.getCodigo(), venda.getQuantidade(), venda.getPrecoVendido(), 
				venda.getProduto().getCodigo(), venda.getProduto().getDescricao());
	}
	
}
