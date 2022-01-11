package com.gvendas.gestaovendas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gvendas.gestaovendas.entity.ProdutoEntity;
import com.gvendas.gestaovendas.exception.RegraNegocioException;
import com.gvendas.gestaovendas.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaService categoriaService;
	
//	public List<ProdutoEntity> listAll(){
//		return produtoRepository.findAll();
//	}
	
	public List<ProdutoEntity> listAll(Long codigoCategoria){
		return produtoRepository.findByCategoriaCodigo(codigoCategoria);
	}
	
	public Optional<ProdutoEntity> listByCodigo(Long codigo){
		return produtoRepository.findById(codigo);
	}
	
	public ProdutoEntity save(Long codigoCategoria, ProdutoEntity produto) {
		validCategoriaExist(codigoCategoria);
		validProductDuplicated(produto);
		return produtoRepository.save(produto);
	}
	
	public ProdutoEntity update(Long codigoCategoria, Long codigoProduto, ProdutoEntity produto) {
		ProdutoEntity produtoSalvo = validProductExist(codigoProduto);
		validProductDuplicated(produto);
		validCategoriaExist(codigoCategoria);
		BeanUtils.copyProperties(produto, produtoSalvo, "codigo");
		return produtoRepository.save(produtoSalvo);
	}
	
	public void delete(Long codigoCategoria, Long codigoProduto) {
		ProdutoEntity produto = validProductExist(codigoProduto);
		validCategoriaExist(codigoCategoria);
		produtoRepository.delete(produto);
	}
	
	private ProdutoEntity validProductExist(Long codigoProduto) {
		Optional<ProdutoEntity> produto = listByCodigo(codigoProduto);
		if(produto.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		return produto.get();
	}

	private void validProductDuplicated(ProdutoEntity produto) {
		Optional<ProdutoEntity> produtoPorDescricao = produtoRepository.findByCategoriaCodigoAndDescricao(produto.getCategoria().
				getCodigo(), produto.getDescricao());
		if(produtoPorDescricao.isPresent() && produtoPorDescricao.get().getCodigo() != produto.getCodigo()) {
			throw new RegraNegocioException(String.format("O produto %s já está cadastrado!", produto.getDescricao()));
		}
	}
	
	private void validCategoriaExist(Long codigoCategoria) {
		if(codigoCategoria == null) {
			throw new RegraNegocioException("A categoria não pode ser nula");
		}
		
		if(categoriaService.listByCodigo(codigoCategoria).isEmpty()) {
			throw new RegraNegocioException(String.format("A categoria de codigo %s informada não existe no cadastro!", codigoCategoria));
		}
	}
	
}
