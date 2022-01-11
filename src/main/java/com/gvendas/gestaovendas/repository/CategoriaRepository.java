package com.gvendas.gestaovendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gvendas.gestaovendas.entity.CategoriaEntity;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long>{

	CategoriaEntity findByNome(String nome);
	
}
