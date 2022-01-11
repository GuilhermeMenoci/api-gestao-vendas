package com.gvendas.gestaovendas.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public List<ProdutoEntity> listAll(@PathVariable Long codigoCategoria){
		return produtoService.listAll(codigoCategoria);
	}
	
	@ApiOperation(value = "Listar produto por codigo", nickname = "listByCodigoProduct")
	@GetMapping("/{codigo}")
	public ResponseEntity<Optional<ProdutoEntity>> listByCodigo(@PathVariable Long codigo){
		Optional<ProdutoEntity> produto = produtoService.listByCodigo(codigo);
		return produto.isPresent() ? ResponseEntity.ok(produto) : ResponseEntity.notFound().build();
	}
	
	@ApiOperation(value = "Cadastrar um produto", nickname = "saveProduct")
	@PostMapping
	public ResponseEntity<ProdutoEntity> save(@Valid @RequestBody ProdutoEntity produto){
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.save(produto));
	}
	
	@ApiOperation(value = "Atualizar um produto", nickname = "updateProduct")
	@PutMapping("/{codigoProduto}")
	public ResponseEntity<ProdutoEntity> update(@PathVariable Long codigoCategoria,@PathVariable Long codigoProduto,
			@Valid @RequestBody ProdutoEntity produto){
		return ResponseEntity.status(HttpStatus.OK).body(produtoService.update(codigoCategoria, codigoProduto, produto));
	}
	
}
