package com.gvendas.gestaovendas.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gvendas.gestaovendas.dto.venda.ClienteVendaResponseDTO;
import com.gvendas.gestaovendas.dto.venda.ItemVendaRequestDTO;
import com.gvendas.gestaovendas.dto.venda.VendaRequestDTO;
import com.gvendas.gestaovendas.dto.venda.VendaResponseDTO;
import com.gvendas.gestaovendas.entity.ClienteEntity;
import com.gvendas.gestaovendas.entity.VendaEntity;
import com.gvendas.gestaovendas.exception.RegraNegocioException;
import com.gvendas.gestaovendas.repository.ItemVendaRepository;
import com.gvendas.gestaovendas.repository.VendaRepository;

@Service
public class VendaService extends AbstractVendaService {

	@Autowired
	private VendaRepository vendaRepository;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ItemVendaRepository itemVendaRepository;

	@Autowired
	private ProdutoService produtoService;

	public ClienteVendaResponseDTO listarVendaPorCliente(Long codigoCliente) {
		ClienteEntity cliente = validarClienteVendaExiste(codigoCliente);
		List<VendaResponseDTO> vendaResponseDtoList = vendaRepository.findByClienteCodigo(codigoCliente).stream()
				.map(venda -> criandoVendaResponseDTO(venda, itemVendaRepository.findByVendaPorCodigo(venda.getCodigo())))
				.collect(Collectors.toList());
		return new ClienteVendaResponseDTO(cliente.getNome(), vendaResponseDtoList);
	}

	public ClienteVendaResponseDTO listarVendaPorCodigo(Long codigoVenda) {
		VendaEntity venda = validarVendaExiste(codigoVenda);
		return retornandoClienteVendaResponseDto(venda, itemVendaRepository.findByVendaPorCodigo(venda.getCodigo()));
	}

	public ClienteVendaResponseDTO salvar(Long codigoCliente, VendaRequestDTO vendaDto) {
		ClienteEntity cliente = validarClienteVendaExiste(codigoCliente);
		validarProdutoExiste(vendaDto.getItensVendaDto());
		VendaEntity vendaSalva = salvarVenda(cliente, vendaDto);
		return retornandoClienteVendaResponseDto(vendaSalva, itemVendaRepository.findByVendaPorCodigo(vendaSalva.getCodigo()));
	}
	
	private VendaEntity salvarVenda(ClienteEntity cliente, VendaRequestDTO vendaDto) {
		VendaEntity vendaSalva = vendaRepository.save(new VendaEntity(vendaDto.getData(), cliente));
		vendaDto.getItensVendaDto().stream().map(itemVendaDto -> criandoItemVenda(itemVendaDto, vendaSalva))
				.forEach(itemVendaRepository::save);
		return vendaSalva;
	}

	private void validarProdutoExiste(List<ItemVendaRequestDTO> itensVendaDto) {
		itensVendaDto.forEach(item -> produtoService.listarPorCodigo(item.getCodigoProduto()));
	}

	private VendaEntity validarVendaExiste(Long codigoVenda) {
		Optional<VendaEntity> venda = vendaRepository.findById(codigoVenda);
		if (venda.isEmpty()) {
			throw new RegraNegocioException(String.format("Venda de código %s não encontrada!", codigoVenda));
		}
		return venda.get();
	}

	private ClienteEntity validarClienteVendaExiste(Long codigoCliente) {
		Optional<ClienteEntity> cliente = clienteService.listarPorCodigo(codigoCliente);
		if (cliente.isEmpty()) {
			throw new RegraNegocioException(
					String.format("O cliente de código %s informado não existe no cadastro!", codigoCliente));
		}
		return cliente.get();
	}

	

}
