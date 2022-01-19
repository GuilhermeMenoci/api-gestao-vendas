package com.gvendas.gestaovendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gvendas.gestaovendas.entity.ItemVenda;

@Repository
public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long>{

	List<ItemVenda> findByVendaCodigo(Long codigoVenda);
	
}
