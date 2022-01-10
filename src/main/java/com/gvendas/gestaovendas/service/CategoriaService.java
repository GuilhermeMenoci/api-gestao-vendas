package com.gvendas.gestaovendas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gvendas.gestaovendas.entity.CategoriaEntity;
import com.gvendas.gestaovendas.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public List<CategoriaEntity> listAll(){
		return categoriaRepository.findAll();
	}
	
	public Optional<CategoriaEntity> listByCodigo(Long codigo){
		return categoriaRepository.findById(codigo);
	}
	
	public CategoriaEntity save(CategoriaEntity categoria) {
		return categoriaRepository.save(categoria);
	}
	
	public CategoriaEntity update(Long codigo, CategoriaEntity categoria) {
		CategoriaEntity categoriaSave = validCategoriaExist(codigo);
		BeanUtils.copyProperties(categoria, categoriaSave, "codigo");
		return categoriaRepository.save(categoriaSave);
	}
	
	public void delete(Long codigo) {
		categoriaRepository.deleteById(codigo);
	}

	private CategoriaEntity validCategoriaExist(Long codigo) {
		Optional<CategoriaEntity> categoria = listByCodigo(codigo);
		if(categoria.isEmpty())
			throw new EmptyResultDataAccessException(1);
		return categoria.get();
	}
	
}
