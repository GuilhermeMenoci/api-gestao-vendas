package com.gvendas.gestaovendas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gvendas.gestaovendas.entity.ClienteEntity;
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
	
}
