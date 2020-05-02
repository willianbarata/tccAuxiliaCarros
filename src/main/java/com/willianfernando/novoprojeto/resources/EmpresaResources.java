package com.willianfernando.novoprojeto.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.willianfernando.novoprojeto.DTO.ClienteDTO;
import com.willianfernando.novoprojeto.DTO.EmpresaDTO;
import com.willianfernando.novoprojeto.domain.Cliente;
import com.willianfernando.novoprojeto.domain.Empresa;
import com.willianfernando.novoprojeto.resources.utils.URL;
import com.willianfernando.novoprojeto.services.CategoriaService;
import com.willianfernando.novoprojeto.services.EmpresaService;

@RestController
@RequestMapping(value="/empresas")
public class EmpresaResources {

	@Autowired
	private EmpresaService service;
	
	@Autowired
	private CategoriaService categoriaService;
	

	private Page<Empresa> list;
	private Page<EmpresaDTO> listDTO;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> listar(@PathVariable Integer id) {
		Empresa obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<EmpresaDTO>> findPage(
			@RequestParam(value="clienteid", defaultValue="") String clienteid,
			@RequestParam(value="nome", defaultValue="") String nome,
			@RequestParam(value="categorias", defaultValue="") String categorias,
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderby", defaultValue="nome") String orderby, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {

		if(clienteid.isEmpty()) {
			String nomeDecoded = URL.decodeParam(nome);
			List<Integer> ids = URL.decodeIntList(categorias);
			list = service.search(nomeDecoded, ids, page,linesPerPage,orderby, direction );
		}else {
			Integer vClienteid = Integer.parseInt(clienteid);
		    list = service.searchCliente(vClienteid, page,linesPerPage,orderby, direction );	
		}
		listDTO = list.map(obj -> new EmpresaDTO(obj)); 
		return ResponseEntity.ok().body(listDTO);
	}
	
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody EmpresaDTO objDTO){
		System.out.println("teste POST");
		System.out.println(objDTO.getNome());
		Empresa obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody EmpresaDTO objDTO, @PathVariable Integer id){
	
		Empresa obj = service.fromDTO(objDTO);
		
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
	

