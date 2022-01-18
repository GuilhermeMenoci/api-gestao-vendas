package com.gvendas.gestaovendas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gvendas.gestaovendas.entity.ClienteEntity;
import com.gvendas.gestaovendas.exception.RegraNegocioException;
import com.gvendas.gestaovendas.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public List<ClienteEntity> listarTodosClientes(){
		return clienteRepository.findAll();
	}
	
	public Optional<ClienteEntity> listarPorCodigo(Long codigo){
		return clienteRepository.findById(codigo);
	}
	
	public ClienteEntity salvarCliente(ClienteEntity cliente) {
		validarClienteDuplicado(cliente);
		return clienteRepository.save(cliente);
	}
	
	public ClienteEntity atualizarCliente(Long codigo, ClienteEntity cliente) {
		ClienteEntity clienteAtualizar = validarClienteExiste(codigo);
		validarClienteDuplicado(cliente);
		BeanUtils.copyProperties(cliente, clienteAtualizar, "codigo");
		return clienteRepository.save(clienteAtualizar);
	}
	
	public void deletarCliente(Long codigo) {
		clienteRepository.deleteById(codigo);
	}
	
	private ClienteEntity validarClienteExiste(Long codigo) {
		Optional<ClienteEntity> cliente = listarPorCodigo(codigo);
		if(cliente.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		return cliente.get();
	}
	
	private void validarClienteDuplicado(ClienteEntity cliente) {
		ClienteEntity clientePorNome = clienteRepository.findByNome(cliente.getNome());
		if(clientePorNome != null && clientePorNome.getCodigo() != cliente.getCodigo()) {
			throw new RegraNegocioException(String.format("O cliente %s já está cadastrado", cliente.getNome().toUpperCase()));
		}
		
	}
	
}
