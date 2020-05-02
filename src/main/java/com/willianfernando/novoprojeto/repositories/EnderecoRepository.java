package com.willianfernando.novoprojeto.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.willianfernando.novoprojeto.domain.Empresa;
import com.willianfernando.novoprojeto.domain.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer>{
	
	/*@Transactional(readOnly=true)
	@Query("SELECT obj FROM Endereco obj WHERE obj.cliente.cliente_id = :clienteid")
	Endereco searchCli(@Param("clienteid") Integer clienteid);
	*/
	/*
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Endereco obj WHERE obj.cliente.cliente_id = :clienteid")
	Page<Endereco> searchCli(@Param("clienteid") Integer clienteid, Pageable pageRequest);
	*/
}
