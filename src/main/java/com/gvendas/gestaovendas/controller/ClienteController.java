 package com.gvendas.gestaovendas.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gvendas.gestaovendas.dto.cliente.ClienteRequestDTO;
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

	@ApiOperation(value = "Cadastrar um cliente", nickname = "saveCliente")
	@PostMapping
	public ResponseEntity<ClienteResponseDTO> salvarCategoria(@Valid @RequestBody ClienteRequestDTO clienteDTO) {
		ClienteEntity clienteSalvo = clienteService.salvarCliente(clienteDTO.converterParaEntidade());
		return ResponseEntity.status(HttpStatus.CREATED).body(ClienteResponseDTO.converterParaClienteDTO(clienteSalvo));
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<ClienteResponseDTO> atualizarCliente(@PathVariable Long codigo, @Valid @RequestBody ClienteRequestDTO clienteDto){
		ClienteEntity clienteAtualizado = clienteService.atualizarCliente(codigo, clienteDto.converterParaEntidade(codigo));
		return ResponseEntity.ok(ClienteResponseDTO.converterParaClienteDTO(clienteAtualizado));
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarCliente(@PathVariable Long codigo) {
		clienteService.deletarCliente(codigo);
	}
	
}
