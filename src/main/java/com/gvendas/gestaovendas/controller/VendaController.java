package com.gvendas.gestaovendas.controller;

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

import com.gvendas.gestaovendas.dto.venda.ClienteVendaResponseDTO;
import com.gvendas.gestaovendas.dto.venda.VendaRequestDTO;
import com.gvendas.gestaovendas.service.VendaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Venda")
@RestController
@RequestMapping("/venda")
public class VendaController {

	@Autowired
	private VendaService vendaService;
	
	@ApiOperation(value = "Listar vendas por cliente", nickname = "listarVendaPorCliente")
	@GetMapping("/cliente/{codigoCliente}")
	public ResponseEntity<ClienteVendaResponseDTO> listarVendaPorCliente(@PathVariable Long codigoCliente){
		return ResponseEntity.ok(vendaService.listarVendaPorCliente(codigoCliente));
	}
	
	@ApiOperation(value = "Listar vendas por codigo", nickname = "listarVendaPorCodigo")
	@GetMapping("/{codigoVenda}")
	public ResponseEntity<ClienteVendaResponseDTO> listarVendaPorCodigo(@PathVariable Long codigoVenda){
		return ResponseEntity.ok(vendaService.listarVendaPorCodigo(codigoVenda));
	}
	
	@ApiOperation(value = "Registrar vendas", nickname = "salvarVenda")
	@PostMapping("/cliente/{codigoCliente}")
	public ResponseEntity<ClienteVendaResponseDTO> salvarVenda(@PathVariable Long codigoCliente, 
			@Valid @RequestBody VendaRequestDTO vendaDto){
		return ResponseEntity.status(HttpStatus.CREATED).body(vendaService.salvar(codigoCliente, vendaDto));
	}
	
	@ApiOperation(value = "Atualizar vendas", nickname = "atualizarVenda")
	@PutMapping("/{codigoVenda}/cliente/{codigoCliente}}")
	public ResponseEntity<ClienteVendaResponseDTO> atualizarVenda(@PathVariable Long codigoVenda, @PathVariable Long codigoCliente, 
			@Valid @RequestBody VendaRequestDTO vendaDto){
		return ResponseEntity.ok(vendaService.atualizarVenda(codigoVenda, codigoCliente, vendaDto));
	}
	
	@ApiOperation(value = "Deletar vendas", nickname = "deletarVenda")
	@DeleteMapping("/{codigoVenda}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarVenda(@PathVariable Long codigoVenda) {
		vendaService.deletarVenda(codigoVenda);
	}

	
	
}
