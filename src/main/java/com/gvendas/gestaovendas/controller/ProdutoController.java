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

import com.gvendas.gestaovendas.dto.produto.ProdutoRequestDTO;
import com.gvendas.gestaovendas.dto.produto.ProdutoResponseDTO;
import com.gvendas.gestaovendas.entity.ProdutoEntity;
import com.gvendas.gestaovendas.service.ProdutoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Produto")
@RestController
@RequestMapping("/categoria{codigoCategoria}/produto")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

//	@ApiOperation(value = "Listar todos os produtos")
//	@GetMapping
//	public List<ProdutoEntity> listAll(){
//		return produtoService.listAll();
//	}

	@ApiOperation(value = "Listar todos os produtos", nickname = "listAllProduct")
	@GetMapping
	public List<ProdutoResponseDTO> listarTodosProdutos(@PathVariable Long codigoCategoria) {
		return produtoService.listarTodosProdutos(codigoCategoria).stream()
				.map(produto -> ProdutoResponseDTO.converterParaProdutoDTO(produto)).collect(Collectors.toList());
	}

	@ApiOperation(value = "Listar produto por codigo", nickname = "listByCodigoProduct")
	@GetMapping("/{codigo}")
	public ResponseEntity<ProdutoResponseDTO> listarPorCodigo(@PathVariable Long codigo) {
		Optional<ProdutoEntity> produto = produtoService.listarPorCodigo(codigo);
		return produto.isPresent() ? ResponseEntity.ok(ProdutoResponseDTO.converterParaProdutoDTO(produto.get()))
				: ResponseEntity.notFound().build();
	}

	@ApiOperation(value = "Cadastrar um produto", nickname = "saveProduct")
	@PostMapping
	public ResponseEntity<ProdutoResponseDTO> salvarProduto(@PathVariable Long codigoCategoria,
			@Valid @RequestBody ProdutoRequestDTO produto) {
		ProdutoEntity produtoSalvo = produtoService.salvarProduto(codigoCategoria,
				produto.converterParaEntidade(codigoCategoria));
		return ResponseEntity.status(HttpStatus.CREATED).body(ProdutoResponseDTO.converterParaProdutoDTO(produtoSalvo));
	}

	@ApiOperation(value = "Atualizar um produto", nickname = "updateProduct")
	@PutMapping("/{codigoProduto}")
	public ResponseEntity<ProdutoResponseDTO> atualizarProduto(@PathVariable Long codigoCategoria,
			@PathVariable Long codigoProduto, @Valid @RequestBody ProdutoRequestDTO produto) {
		ProdutoEntity produtoAtualizado = produtoService.atualizarProduto(codigoCategoria, codigoProduto,
				produto.converterParaEntidade(codigoCategoria, codigoProduto));
		return ResponseEntity.ok(ProdutoResponseDTO.converterParaProdutoDTO(produtoAtualizado));
	}

	@ApiOperation(value = "Deletar um produto", nickname = "deleteProduct")
	@DeleteMapping("/{codigoProduto}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarProduto(@PathVariable Long codigoCategoria, @PathVariable Long codigoProduto) {
		produtoService.deletarProduto(codigoCategoria, codigoProduto);
	}

}
