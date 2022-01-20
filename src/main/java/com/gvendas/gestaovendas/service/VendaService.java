package com.gvendas.gestaovendas.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gvendas.gestaovendas.dto.venda.ClienteVendaResponseDTO;
import com.gvendas.gestaovendas.dto.venda.ItemVendaRequestDTO;
import com.gvendas.gestaovendas.dto.venda.VendaRequestDTO;
import com.gvendas.gestaovendas.dto.venda.VendaResponseDTO;
import com.gvendas.gestaovendas.entity.ClienteEntity;
import com.gvendas.gestaovendas.entity.ItemVenda;
import com.gvendas.gestaovendas.entity.ProdutoEntity;
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

	@org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public ClienteVendaResponseDTO salvar(Long codigoCliente, VendaRequestDTO vendaDto) {
		ClienteEntity cliente = validarClienteVendaExiste(codigoCliente);
		validarProdutoExisteEAtualizar(vendaDto.getItensVendaDto());
		VendaEntity vendaSalva = salvarVenda(cliente, vendaDto);
		return retornandoClienteVendaResponseDto(vendaSalva, itemVendaRepository.findByVendaPorCodigo(vendaSalva.getCodigo()));
	}
	
	public ClienteVendaResponseDTO atualizarVenda(Long codigoVenda, Long codigoCliente, VendaRequestDTO venda) {
		validarVendaExiste(codigoVenda);
		ClienteEntity cliente = validarClienteVendaExiste(codigoCliente);
		List<ItemVenda> itensVendaList = itemVendaRepository.findByVendaPorCodigo(codigoVenda);
		validarProdutoExisteEValidarEstoque(itensVendaList);
		validarProdutoExisteEAtualizar(venda.getItensVendaDto());
		itemVendaRepository.deleteAll(itensVendaList);
		VendaEntity vendaAtualizada = atualizarVenda(codigoVenda, cliente, venda);
		return retornandoClienteVendaResponseDto(vendaAtualizada, itemVendaRepository.findByVendaPorCodigo(vendaAtualizada.getCodigo()));
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public void deletarVenda(Long codigoVenda) {
		validarVendaExiste(codigoVenda);
		List<ItemVenda> itensVenda = itemVendaRepository.findByVendaPorCodigo(codigoVenda);
		validarProdutoExisteEValidarEstoque(itensVenda);
		itemVendaRepository.deleteAll(itensVenda);
		vendaRepository.deleteById(codigoVenda);;
	}
	
	private void validarProdutoExisteEValidarEstoque(List<ItemVenda> itensVenda) {
		itensVenda.forEach(item -> {
			ProdutoEntity produto = produtoService.validarSeProdutoExiste(item.getProduto().getCodigo());
			produto.setQuantidade(produto.getQuantidade() + item.getQuantidade());
			produtoService.atualizarQuantidadeEmEstoque(produto);
		});
	}
	
	private VendaEntity salvarVenda(ClienteEntity cliente, VendaRequestDTO vendaDto) {
		VendaEntity vendaSalva = vendaRepository.save(new VendaEntity(vendaDto.getData(), cliente));
		vendaDto.getItensVendaDto().stream().map(itemVendaDto -> criandoItemVenda(itemVendaDto, vendaSalva))
				.forEach(itemVendaRepository::save);
		return vendaSalva;
	}
	private VendaEntity atualizarVenda(Long codigoVenda, ClienteEntity cliente, VendaRequestDTO vendaDto) {
		VendaEntity vendaSalva = vendaRepository.save(new VendaEntity(codigoVenda, vendaDto.getData(), cliente));
		vendaDto.getItensVendaDto().stream().map(itemVendaDto -> criandoItemVenda(itemVendaDto, vendaSalva))
				.forEach(itemVendaRepository::save);
		return vendaSalva;
	}

	private void validarProdutoExisteEAtualizar(List<ItemVendaRequestDTO> itensVendaDto) {
		itensVendaDto.forEach(item -> { 
		ProdutoEntity produto = produtoService.validarSeProdutoExiste(item.getCodigoProduto());
		validarQuantidadeProdutoExiste(produto, item.getQuantidade());
		produto.setQuantidade(produto.getQuantidade() - item.getQuantidade());
		produtoService.atualizarQuantidadeEmEstoque(produto);
		});
	}
	
	private void validarQuantidadeProdutoExiste(ProdutoEntity produto, Integer quantidade) {
		if(!(produto.getQuantidade() >= quantidade)) {
			throw new RegraNegocioException(String.format("A quantidade %s informada para o produto %s não está disponivel em estoque", 
					quantidade, produto.getDescricao()));
		}
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
