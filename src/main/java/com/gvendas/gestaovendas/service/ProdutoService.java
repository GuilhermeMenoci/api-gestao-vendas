package com.gvendas.gestaovendas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gvendas.gestaovendas.entity.ProdutoEntity;
import com.gvendas.gestaovendas.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
//	public List<ProdutoEntity> listAll(){
//		return produtoRepository.findAll();
//	}
	
	public List<ProdutoEntity> listAll(Long codigoCategoria){
		return produtoRepository.findByCategoriaCodigo(codigoCategoria);
	}
	
	public Optional<ProdutoEntity> listByCodigo(Long codigo){
		return produtoRepository.findById(codigo);
	}
	
}
