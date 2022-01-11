package com.gvendas.gestaovendas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gvendas.gestaovendas.entity.ProdutoEntity;
import com.gvendas.gestaovendas.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	public List<ProdutoEntity> listAll(){
		return produtoRepository.findAll();
	}
	
}
