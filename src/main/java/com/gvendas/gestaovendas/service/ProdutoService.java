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
	
	public List<ProdutoEntity> listarTodosProdutos(Long codigoCategoria){
		return produtoRepository.findByCategoriaCodigo(codigoCategoria);
	}
	
	public Optional<ProdutoEntity> listarPorCodigo(Long codigo){
		return produtoRepository.findById(codigo);
	}
	
	public ProdutoEntity salvarProduto(Long codigoCategoria, ProdutoEntity produto) {
		validarCategoriaExiste(codigoCategoria);
		validarProdutoDuplicado(produto);
		return produtoRepository.save(produto);
	}
	
	public ProdutoEntity atualizarProduto(Long codigoCategoria, Long codigoProduto, ProdutoEntity produto) {
		ProdutoEntity produtoSalvo = validarSeProdutoExiste(codigoProduto);
		validarProdutoDuplicado(produto);
		validarCategoriaExiste(codigoCategoria);
		BeanUtils.copyProperties(produto, produtoSalvo, "codigo");
		return produtoRepository.save(produtoSalvo);
	}
	
	public void deletarProduto(Long codigoCategoria, Long codigoProduto) {
		ProdutoEntity produto = validarSeProdutoExiste(codigoProduto);
		validarCategoriaExiste(codigoCategoria);
		produtoRepository.delete(produto);
	}
	
	private ProdutoEntity validarSeProdutoExiste(Long codigoProduto) {
		Optional<ProdutoEntity> produto = listarPorCodigo(codigoProduto);
		if(produto.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		return produto.get();
	}

	private void validarProdutoDuplicado(ProdutoEntity produto) {
		Optional<ProdutoEntity> produtoPorDescricao = produtoRepository.findByCategoriaCodigoAndDescricao(produto.getCategoria().
				getCodigo(), produto.getDescricao());
		if(produtoPorDescricao.isPresent() && produtoPorDescricao.get().getCodigo() != produto.getCodigo()) {
			throw new RegraNegocioException(String.format("O produto %s já está cadastrado!", produto.getDescricao()));
		}
	}
	
	private void validarCategoriaExiste(Long codigoCategoria) {
		if(codigoCategoria == null) {
			throw new RegraNegocioException("A categoria não pode ser nula");
		}
		
		if(categoriaService.listarPorCodigo(codigoCategoria).isEmpty()) {
			throw new RegraNegocioException(String.format("A categoria de codigo %s informada não existe no cadastro!", codigoCategoria));
		}
	}
	
}
