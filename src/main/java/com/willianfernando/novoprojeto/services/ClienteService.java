package com.willianfernando.novoprojeto.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.willianfernando.novoprojeto.DTO.ClienteDTO;
import com.willianfernando.novoprojeto.DTO.ClienteNewDTO;
import com.willianfernando.novoprojeto.domain.Cidade;
import com.willianfernando.novoprojeto.domain.Cliente;
import com.willianfernando.novoprojeto.domain.Endereco;
import com.willianfernando.novoprojeto.domain.Estado;
import com.willianfernando.novoprojeto.enums.Perfil;
import com.willianfernando.novoprojeto.enums.TipoCliente;
import com.willianfernando.novoprojeto.exception.DataIntegrityException;
import com.willianfernando.novoprojeto.exception.ObjectNotFoundException;
import com.willianfernando.novoprojeto.repositories.CidadeRepository;
import com.willianfernando.novoprojeto.repositories.ClienteRepository;
import com.willianfernando.novoprojeto.repositories.EnderecoRepository;
import com.willianfernando.novoprojeto.repositories.EstadoRepository;
import com.willianfernando.novoprojeto.security.UserSS;
import com.willianfernando.novoprojeto.services.exceptions.AuthorizationException;

@Service
public class ClienteService {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Cliente find(Integer id) {
		System.out.println("id");
		System.out.println(id);
		
		
		UserSS user = UserService.authenticated();
		System.out.println("user");
		System.out.println("willian");
		System.out.println(user);
		//se usuário encontrado for igual nulo, ou não possuir perfil de admin, 
		//e Id que estou buscando não é igual ao usuário logado
		if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso Negado");
		}
		Optional<Cliente> obj = repo.findById(id);
		System.out.println("obj = id "+id);
		System.out.println(obj);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente findEdicao(Integer id) {
		System.out.println("id");
		System.out.println(id);
		Optional<Cliente> obj = repo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente findByEmail(String email) {
		
		UserSS user = UserService.authenticated();
		/*if(user==null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso Negado");
		}
		*/
		Cliente obj = repo.findByEmail(email);
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! ID" + user.getId()
			+ ", tipo: " + Cliente.class.getName());
		}
		return obj;
	}
	
	
	@Transactional
	public Cliente insert(Cliente obj,Endereco end,Cidade cid, Estado est) {
		obj.setId(null);
		obj = repo.save(obj);
		System.out.println("obj.getEnderecos() - dentro do insert Cliente");
		//System.out.println(obj.getEnderecos());
		cidadeRepository.save(cid);
		estadoRepository.save(est);
		enderecoRepository.save(end);
		
		return obj;
	}
	
/*	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
*/	
	public Cliente updateNewDTO(ClienteNewDTO obj, Integer id) {
		Cliente newObj = find(id);
		System.out.println("updateNewDTO");
		System.out.println(newObj);
		updateData(newObj, obj);
		
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");
		}
	}
	
	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
		//Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		//Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		//cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
	
	private void updateData(Cliente newObj, ClienteNewDTO obj) {
		
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		newObj.setCpfOuCnpj(obj.getCpfOuCnpj());
		//Optional<Endereco> end = enderecoRepository.findById(obj.getEnderecoId());
		Endereco endereco = new Endereco();
		newObj.getEndereco().get(0).setBairro(obj.getBairro());
		newObj.getEndereco().get(0).setCep(obj.getCep());
		newObj.getEndereco().get(0).setComplemento(obj.getComplemento());
		newObj.getEndereco().get(0).setLogradouro(obj.getLogradouro());
		newObj.getEndereco().get(0).setNumero(obj.getNumero());
		newObj.getEndereco().get(0).getCidade().setNome(obj.getCidade());
		newObj.getEndereco().get(0).getCidade().getEstado().setNome(obj.getEstado());
	}
}
