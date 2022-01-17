package com.gvendas.gestaovendas.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gvendas.gestaovendas.dto.cliente.ClienteResponseDTO;
import com.gvendas.gestaovendas.entity.ClienteEntity;
import com.gvendas.gestaovendas.service.ClienteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Cliente")
@RestController
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@ApiOperation(value = "Listar todos os clientes", nickname = "listAllClients")
	@GetMapping
	public List<ClienteResponseDTO> listarTodasCategoria() {
		return clienteService.listarTodosClientes().stream()
				.map(cliente -> ClienteResponseDTO.converterParaClienteDTO(cliente)).collect(Collectors.toList());
	}

	@ApiOperation(value = "Listar por código", nickname = "listByCodigoClients")
	@GetMapping("/{codigo}")
	public ResponseEntity<ClienteResponseDTO> listarPorCodigo(@PathVariable Long codigo) {
		Optional<ClienteEntity> cliente = clienteService.listarPorCodigo(codigo);
		return cliente.isPresent() ? ResponseEntity.ok(ClienteResponseDTO.converterParaClienteDTO(cliente.get()))
				: ResponseEntity.notFound().build();
	}

}