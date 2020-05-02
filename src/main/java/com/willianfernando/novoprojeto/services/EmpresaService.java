package com.willianfernando.novoprojeto.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.willianfernando.novoprojeto.DTO.EmpresaDTO;
import com.willianfernando.novoprojeto.domain.Categoria;
import com.willianfernando.novoprojeto.domain.Cliente;
import com.willianfernando.novoprojeto.domain.Empresa;
import com.willianfernando.novoprojeto.exception.DataIntegrityException;
import com.willianfernando.novoprojeto.exception.ObjectNotFoundException;
import com.willianfernando.novoprojeto.repositories.CategoriaRepository;
import com.willianfernando.novoprojeto.repositories.EmpresaRepository;
import com.willianfernando.novoprojeto.security.UserSS;
import com.willianfernando.novoprojeto.services.exceptions.AuthorizationException;

@Service
public class EmpresaService {

	//instanciar 
		@Autowired
		private EmpresaRepository repo;
		
		@Autowired
		private CategoriaRepository categoriaRepository;
		
		@Autowired
		private CategoriaService categoriaService;
		
		@Autowired
		private ClienteService clienteService;
		
		public Empresa find(Integer id) {
			Optional<Empresa> obj = repo.findById(id);
			return obj.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! Id: " + id + ", Tipo: " + Empresa.class.getName()));
			}
		
		@Transactional
		public Empresa insert(Empresa obj) {
			System.out.println("Dentro do Insert");
			System.out.println(obj);
			obj.setId(null);
			obj = repo.save(obj);
			return obj;
		}
		
		public Empresa update(Empresa obj) {
			Empresa newObj = find(obj.getId());
			updateData(newObj, obj);
			return repo.save(newObj);
		}

		public void delete(Integer id) {
			find(id);
			try {
				repo.deleteById(id);
			}
			catch (DataIntegrityViolationException e) {
				throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");
			}
		}
		
		//busca paginada
		public Page<Empresa> search(String nome,  List<Integer> ids,Integer page, Integer linesPerPage, String orderby, String direction){
			PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderby);
			List<Categoria> categorias = categoriaRepository.findAllById(ids);
			return repo.search(nome, categorias, pageRequest);
			
		}
		
		public Page<Empresa> searchCliente(Integer clienteid, Integer page, Integer linesPerPage, String orderby, String direction){
			PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderby);
			return repo.searchCliente(clienteid, pageRequest);
			
		}
	
		public List<Empresa> findAll() {
			return repo.findAll();
		}
		
		public Empresa fromDTO(EmpresaDTO objDto) {
			
			Categoria cat = categoriaService.find(objDto.getCategoriaId());
			Cliente cli = clienteService.find(objDto.getClienteId());
			Empresa emp = new Empresa(null, objDto.getNome(), objDto.getCnpj(), objDto.getCep(),
									objDto.getRua(), objDto.getNumero(), objDto.getBairro(),
									objDto.getCidade(), objDto.getEstado(), objDto.getTelefone(),
									objDto.getEmail(), cat, cli);
			return emp;
		}
		
		private void updateData(Empresa newObj, Empresa obj) {
			newObj.setNome(obj.getNome());
			newObj.setCnpj(obj.getCnpj());
			newObj.setCep(obj.getCep());
			newObj.setRua(obj.getRua());
			newObj.setNumero(obj.getNumero());
			newObj.setBairro(obj.getBairro());
			newObj.setCidade(obj.getCidade());
			newObj.setEstado(obj.getEstado());
			newObj.setTelefone(obj.getTelefone());
			newObj.setEmail(obj.getEmail());
			newObj.setCliente(obj.getCliente());
			newObj.setCategoria(obj.getCategoria());
			
		}
		
		public Page<Empresa> findPage(Integer page, Integer linesPerPage, String orderby, String direction){
			
			UserSS user = UserService.authenticated();
			
			if(user==null) {
				throw new AuthorizationException("Acesso negado");
			}
			
			PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderby);
			Cliente cliente = clienteService.find(user.getId());
			return repo.findByCliente(cliente, pageRequest);
		}
}
