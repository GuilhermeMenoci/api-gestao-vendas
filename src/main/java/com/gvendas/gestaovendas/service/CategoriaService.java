package com.gvendas.gestaovendas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	
}
