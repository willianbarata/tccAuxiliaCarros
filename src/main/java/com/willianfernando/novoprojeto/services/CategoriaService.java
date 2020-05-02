package com.willianfernando.novoprojeto.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.willianfernando.novoprojeto.exception.*;
import com.willianfernando.novoprojeto.domain.Categoria;
import com.willianfernando.novoprojeto.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	

	
	//instanciar 
		@Autowired
		private CategoriaRepository repo;
		
		public Categoria find(Integer id) {
		
			Optional<Categoria> obj = repo.findById(id);
			
			return obj.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
			}
		
		public Categoria insert(Categoria obj) {
			obj.setId(null);
			return repo.save(obj);
		}
		
		public Categoria update(Categoria obj) {
			find(obj.getId());
			return repo.save(obj);
		}
		
		public void delete(Integer id) {
			find(id); 
			try {
			repo.deleteById(id);
			} catch(DataIntegrityViolationException e) {
				throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos !");
			}
		}
		
		public List<Categoria> findAll(){
			return repo.findAll(); 
		}
		
		public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderby, String direction){
			
			PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderby);
			return repo.findAll(pageRequest);
		}
		
		//método auxiliar que instância uma categoria a partir de um DTO
		public Categoria fromDTO(Categoria objDTO) {
			return new Categoria(objDTO.getId(), objDTO.getNome());
		}
		
		private void updateData(Categoria newObj, Categoria obj) {
			newObj.setNome(obj.getNome());
		}

}
