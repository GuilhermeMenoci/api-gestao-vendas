package com.gvendas.gestaovendas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gvendas.gestaovendas.entity.CategoriaEntity;
import com.gvendas.gestaovendas.service.CategoriaService;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping
	public List<CategoriaEntity> listAll(){
		return categoriaService.listAll();
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Optional<CategoriaEntity>> listByCodigo(@PathVariable Long codigo){
		Optional<CategoriaEntity> categoria = categoriaService.listByCodigo(codigo);
		return categoria.isPresent() ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<CategoriaEntity> save(@RequestBody CategoriaEntity categoria){
		CategoriaEntity categoriaSave = categoriaService.save(categoria);
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSave);
	}
	
	
}
