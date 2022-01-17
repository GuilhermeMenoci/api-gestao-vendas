package com.gvendas.gestaovendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gvendas.gestaovendas.entity.ClienteEntity;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long>{

	ClienteEntity findByNome(String nome);
	
}
