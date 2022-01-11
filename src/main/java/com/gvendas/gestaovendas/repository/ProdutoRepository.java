package com.gvendas.gestaovendas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gvendas.gestaovendas.entity.ProdutoEntity;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long>{

	List<ProdutoEntity> findByCategoriaCodigo(Long codigoCategoria);
	
	Optional<ProdutoEntity> findByCategoriaCodigoAndDescricao(Long codigoCategoria, String descricao);
	
}
