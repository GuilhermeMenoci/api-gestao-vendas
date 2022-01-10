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

import com.gvendas.gestaovendas.entity.CategoriaEntity;
import com.gvendas.gestaovendas.service.CategoriaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Categoria")
@RestController
@RequestMapping("/categoria")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@ApiOperation(value = "Listar todas as categorias")
	@GetMapping
	public List<CategoriaEntity> listAll(){
		return categoriaService.listAll();
	}
	
	@ApiOperation(value = "Listar por código")
	@GetMapping("/{codigo}")
	public ResponseEntity<Optional<CategoriaEntity>> listByCodigo(@PathVariable Long codigo){
		Optional<CategoriaEntity> categoria = categoriaService.listByCodigo(codigo);
		return categoria.isPresent() ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
	}
	
	@ApiOperation(value = "Cadastrar uma categoria")
	@PostMapping
	public ResponseEntity<CategoriaEntity> save(@Valid @RequestBody CategoriaEntity categoria){
		CategoriaEntity categoriaSave = categoriaService.save(categoria);
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSave);
	}
	
	@ApiOperation(value = "Atualizar uma categoria")
	@PutMapping("/{codigo}")
	public ResponseEntity<CategoriaEntity> update(@PathVariable Long codigo, @Valid @RequestBody CategoriaEntity categoria){
		return ResponseEntity.ok(categoriaService.update(codigo, categoria));
	}
	
	
}
