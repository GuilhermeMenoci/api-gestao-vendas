package com.gvendas.gestaovendas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gvendas.gestaovendas.entity.ProdutoEntity;
import com.gvendas.gestaovendas.service.ProdutoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Produto")
@RestController
@RequestMapping("/produto")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;
	
	@ApiOperation(value = "Listar todos os produtos")
	@GetMapping
	public List<ProdutoEntity> listAll(){
		return produtoService.listAll();
	}
	
}
