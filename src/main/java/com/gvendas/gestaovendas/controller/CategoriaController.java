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

import com.gvendas.gestaovendas.dto.CategoriaRequestDTO;
import com.gvendas.gestaovendas.dto.CategoriaResponseDTO;
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

	@ApiOperation(value = "Listar todas as categorias", nickname = "listAllCategory")
	@GetMapping
	public List<CategoriaResponseDTO> listAll() {
		return categoriaService.listAll().stream()
				.map(categoria -> CategoriaResponseDTO.converterParaCategoriaDTO(categoria))
				.collect(Collectors.toList());
	}

	@ApiOperation(value = "Listar por código", nickname = "listByCodigoCategory")
	@GetMapping("/{codigo}")
	public ResponseEntity<CategoriaResponseDTO> listByCodigo(@PathVariable Long codigo) {
		Optional<CategoriaEntity> categoria = categoriaService.listByCodigo(codigo);
		return categoria.isPresent()
				? ResponseEntity.ok(CategoriaResponseDTO.converterParaCategoriaDTO(categoria.get()))
				: ResponseEntity.notFound().build();
	}

	@ApiOperation(value = "Cadastrar uma categoria", nickname = "saveCategory")
	@PostMapping
	public ResponseEntity<CategoriaResponseDTO> save(@Valid @RequestBody CategoriaRequestDTO categoriaDTO) {
		CategoriaEntity categoriaSave = categoriaService.save(categoriaDTO.converterParaEntidade());
		return ResponseEntity.status(HttpStatus.CREATED).body(CategoriaResponseDTO.converterParaCategoriaDTO(categoriaSave));
	}

	@ApiOperation(value = "Atualizar uma categoria", nickname = "updateCategory")
	@PutMapping("/{codigo}")
	public ResponseEntity<CategoriaEntity> update(@PathVariable Long codigo,
			@Valid @RequestBody CategoriaRequestDTO categoriaDTO) {
		return ResponseEntity.ok(categoriaService.update(codigo, categoriaDTO.converterParaEntidade(codigo)));
	}

	@ApiOperation(value = "Deletar uma categoria", nickname = "deleteCategory")
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long codigo) {
		categoriaService.delete(codigo);
	}

}
