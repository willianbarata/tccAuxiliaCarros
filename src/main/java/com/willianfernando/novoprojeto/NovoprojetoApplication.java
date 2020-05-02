package com.willianfernando.novoprojeto;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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




@SpringBootApplication

public class NovoprojetoApplication implements CommandLineRunner{
	

	

	public static void main(String[] args) {
		SpringApplication.run(NovoprojetoApplication.class, args);
	}
	//implementação do CommandLineRunner
		@Override
		public void run(String... args) throws Exception {
			
		
		}

}
