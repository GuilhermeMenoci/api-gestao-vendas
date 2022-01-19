package com.gvendas.gestaovendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gvendas.gestaovendas.entity.VendaEntity;

@Repository
public interface VendaRepository extends JpaRepository<VendaEntity, Long>{

	List<VendaEntity> findByClienteCodigo(Long codigoCliente);
	
}
