package com.willianfernando.novoprojeto.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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
import com.willianfernando.novoprojeto.DTO.ClienteNewDTO;
import com.willianfernando.novoprojeto.domain.Cidade;
import com.willianfernando.novoprojeto.domain.Cliente;
import com.willianfernando.novoprojeto.domain.Endereco;
import com.willianfernando.novoprojeto.domain.Estado;
import com.willianfernando.novoprojeto.services.ClienteService;


@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> listar(@PathVariable Cliente id) {
		System.out.println("willian");
		Cliente obj = service.find(id.getId());
		System.out.println("obj.getEndereco().get(0).getBairro()");
		System.out.println(obj.getEndereco().get(0).getBairro());
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value="/editar/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> listarDTO(@PathVariable Cliente id) {
		System.out.println("willian");
		Cliente obj = service.find(id.getId());
		System.out.println("obj.getEndereco().get(0).getId()");
		System.out.println(obj.getEndereco().get(0).getId());
		System.out.println("EDITAR");
		System.out.println(obj.getEndereco().get(0).getBairro());
		ClienteNewDTO cliNew = new ClienteNewDTO(
				obj.getNome(),
				obj.getEmail(),
				obj.getCpfOuCnpj(),
				1,
				obj.getEndereco().get(0).getLogradouro(),
				obj.getSenha(),
				obj.getEndereco().get(0).getNumero(),
				obj.getEndereco().get(0).getComplemento(),
				obj.getEndereco().get(0).getBairro(),
				obj.getEndereco().get(0).getCep(),
				obj.getTelefones().get(0),
				obj.getTelefones().get(1),
				obj.getEndereco().get(0).getCidade().getId(),
				obj.getEndereco().get(0).getCidade().getNome(),
				obj.getEndereco().get(0).getCidade().getEstado().getNome()
				);
	
		return ResponseEntity.ok().body(cliNew);
	}
	
	
	
	@RequestMapping(value="/email", method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@RequestParam(value="value") String email) {	
		System.out.println("passou");
		System.out.println(email);
		Cliente obj = service.findByEmail(email);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteNewDTO objDTO, @PathVariable Integer id){
		System.out.println("Passou Aqui Clientes "+id);
		System.out.println(objDTO);
		service.updateNewDTO(objDTO, id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> list = service.findAll();
		List<ClienteDTO> listDTO = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@RequestMapping(value="/page",method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderby", defaultValue="nome") String orderby, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Cliente> list = service.findPage(page,linesPerPage,orderby, direction );
		Page<ClienteDTO> listDTO = list.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDTO){
		System.out.println(objDTO);
		Cliente obj = service.fromDTO(objDTO);
		Estado estado = new Estado(null,objDTO.getEstado());
		Cidade cid = new Cidade(null, objDTO.getCidade(), estado);
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(),
								objDTO.getBairro(), objDTO.getCep(), obj, cid);
		
				
		obj = service.insert(obj,end,cid,estado);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
}
