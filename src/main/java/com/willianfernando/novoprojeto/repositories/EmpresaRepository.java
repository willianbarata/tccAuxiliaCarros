package com.willianfernando.novoprojeto.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.willianfernando.novoprojeto.domain.Categoria;
import com.willianfernando.novoprojeto.domain.Cliente;
import com.willianfernando.novoprojeto.domain.Empresa;


@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer>{

	@Transactional(readOnly=true)
	Page<Empresa> findByCliente(Cliente cliente, Pageable pageRequest);
	
	@Transactional(readOnly=true)
	@Query("SELECT DISTINCT obj FROM Empresa obj INNER JOIN obj.categoria cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Empresa> search(@Param("nome") String nome,@Param("categorias") List<Categoria> categorias, Pageable pageRequest);
	
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Empresa obj WHERE obj.cliente.id = :clienteid")
	Page<Empresa> searchCliente(@Param("clienteid") Integer clienteid, Pageable pageRequest);
	
}
