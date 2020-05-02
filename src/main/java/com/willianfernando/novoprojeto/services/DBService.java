package com.willianfernando.novoprojeto.services;

import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.willianfernando.novoprojeto.domain.Categoria;
import com.willianfernando.novoprojeto.domain.Cidade;
import com.willianfernando.novoprojeto.domain.Cliente;
import com.willianfernando.novoprojeto.domain.Empresa;
import com.willianfernando.novoprojeto.domain.Endereco;
import com.willianfernando.novoprojeto.domain.Estado;
import com.willianfernando.novoprojeto.enums.Perfil;
import com.willianfernando.novoprojeto.enums.TipoCliente;
import com.willianfernando.novoprojeto.repositories.CategoriaRepository;
import com.willianfernando.novoprojeto.repositories.CidadeRepository;
import com.willianfernando.novoprojeto.repositories.ClienteRepository;
import com.willianfernando.novoprojeto.repositories.EmpresaRepository;
import com.willianfernando.novoprojeto.repositories.EnderecoRepository;
import com.willianfernando.novoprojeto.repositories.EstadoRepository;

@Service
public class DBService {
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public void instantiateTestDatabase() throws ParseException {


	System.out.println("teste willian");
	Estado est1 = new Estado(null, "Minas Gerais"); 
	Estado est2 = new Estado(null, "São Paulo");
	
	Cidade c1 = new Cidade(null, "Uberlandia", est1);
	Cidade c2 = new Cidade(null, "São Paulo", est2);
	Cidade c3 = new Cidade(null, "Campinas", est2);
	
	est1.getCidades().addAll(Arrays.asList(c1));
	est2.getCidades().addAll(Arrays.asList(c2,c3));
	
	estadoRepository.saveAll(Arrays.asList(est1, est2));
	cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));

	Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA, pe.encode("123456"));
	cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
	
	Cliente cli2 = new Cliente(null, "Ana Costa", "ana@gmail.com", "53193788074", TipoCliente.PESSOAFISICA, pe.encode("123456"));
	cli2.getTelefones().addAll(Arrays.asList("27123423", "93432193"));
	cli2.addPerfil(Perfil.ADMIN);
	
	Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
	Endereco e2 = new Endereco(null, "Avenida Mattos", "105", "sala 800", "Centro", "38777012", cli1, c2);
	Endereco e3 = new Endereco(null, "Avenida Brasil", "902", "sala 1", "São Francisco", "38752612", cli2, c2);
	
	
	/*cli1.getEnderecos().addAll();
	cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
	cli2.getEnderecos().addAll(Arrays.asList(e3));
	*/
	clienteRepository.saveAll(Arrays.asList(cli1, cli2));
	enderecoRepository.saveAll(Arrays.asList(e1,e2,e3));
	
	Categoria cat1 = new Categoria(null, "Borracharia");
	Categoria cat2 = new Categoria(null, "Auto Elétricos");
	Categoria cat3 = new Categoria(null,"Mecânica");
	Categoria cat4 = new Categoria(null,"Chaveiros");
	Categoria cat5 = new Categoria(null,"Guinchos");
	Categoria cat6 = new Categoria(null,"Socorro Motos");
	Categoria cat7 = new Categoria(null,"Peças");
	
	Empresa emp1 = new Empresa(null, "Borracharia Brasil", "10101010101010", "15800-020", "Rua maranhão", "1005", 
								"Centro", "Catanduva", "SP", "1735262525", "borracharia@borracharia.com.br",cat1,cli2);
	Empresa emp2 = new Empresa(null, "Auto Elétrico Brasil", "222222222222", "15800-020", "Rua maranhão", "1005", 
			"Centro", "Catanduva", "SP", "1735262525", "borracharia@borracharia.com.br",cat2,cli1);
	Empresa emp3 = new Empresa(null, "Auto Elétrico Japonês", "33333333333333", "15800-020", "Rua maranhão", "1005", 
			"Centro", "Catanduva", "SP", "1735262525", "borracharia@borracharia.com.br",cat2,cli1);
	Empresa emp4 = new Empresa(null, "Mecânica do Alemão", "444444444444444", "15800-020", "Rua maranhão", "1005", 
			"Centro", "Catanduva", "SP", "1735262525", "borracharia@borracharia.com.br",cat3,cli1);
	Empresa emp5 = new Empresa(null, "Guinchos SOS 24hrs", "9999999999999", "15800-020", "Rua maranhão", "1005", 
			"Centro", "Catanduva", "SP", "1735262525", "borracharia@borracharia.com.br",cat5,cli1);
	Empresa emp6 = new Empresa(null, "Look Motos", "8888888888888", "15800-020", "Rua maranhão", "1005", 
			"Centro", "Catanduva", "SP", "1735262525", "borracharia@borracharia.com.br",cat6,cli1);
	Empresa emp7 = new Empresa(null, "Auto peças", "777785966", "15800-020", "Rua maranhão", "1005", 
			"Centro", "Catanduva", "SP", "1735262525", "borracharia@borracharia.com.br",cat7,cli1);
	Empresa emp8 = new Empresa(null, "Chaveiros São Paulo", "987654321", "15800-020", "Rua maranhão", "1005", 
			"Centro", "Catanduva", "SP", "1735262525", "borracharia@borracharia.com.br",cat4,cli1);
	Empresa emp9 = new Empresa(null, "Wagner Borracheiro", "87976635", "15800-020", "Rua maranhão", "1005", 
			"Centro", "Catanduva", "SP", "1735262525", "borracharia@borracharia.com.br",cat1,cli1);
	//adicionando a lista -- associando a empresa
	cat1.getEmpresas().addAll(Arrays.asList(emp1));
	cat2.getEmpresas().addAll(Arrays.asList(emp2, emp3));
	cat3.getEmpresas().addAll(Arrays.asList(emp4));
	cat5.getEmpresas().addAll(Arrays.asList(emp5));
	cat6.getEmpresas().addAll(Arrays.asList(emp6));
	cat7.getEmpresas().addAll(Arrays.asList(emp7));
	cat4.getEmpresas().addAll(Arrays.asList(emp8));
	cat1.getEmpresas().addAll(Arrays.asList(emp9));
	
	categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5,cat6,cat7));
	
	empresaRepository.saveAll(Arrays.asList(emp1, emp2, emp3,emp4,emp5,emp6,emp7,emp8));
System.out.println("Teste willian 2");


	}
}
