package com.willianfernando.novoprojeto.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

 //Essa classe possui um relacionamento muitos pra muitos com a classe Categoria (existe uma tabela entre as duas efetuando devidas referências)

//mapeamento da classe Empresa - informamos que é uma entidade do JPA
@Entity
public class Empresa implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String cnpj;
	private String cep;
	private String rua;
	private String numero;
	private String bairro;
	private String cidade;
	private String estado;
	private String telefone;
	private String email;
	
	
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Cliente cliente;
	//do outro lado da associação já foram buscar os objetos, essa notação diz para não buscar
	//@JsonBackReference
	//uma empresa pode estar em várias categorias
	//@ManyToMany 
	/*Definindo tabela de relacionamento
	 * JoinTable incluimos o nome da tabela, e o nome da chave estrangeira da empresa
	 * e referenciamos a chave estrangeira da categoria(inverseJoinColums)
	 */
	/*@JoinTable(name="EMPRESA_CATEGORIA",
				joinColumns = @JoinColumn(name = "empresaId"),
				inverseJoinColumns = @JoinColumn(name = "categoriaId")
	)*/
	@ManyToOne
	@JoinColumn(name="categoria_id")
	private Categoria categoria;
	
	//Construtor Vazio
	public Empresa() {}
	
	public Empresa(Integer id, String nome, String cnpj, String cep,
				   String rua, String numero, String bairro, String cidade,
				   String estado, String telefone, String email, Categoria cat, Cliente cliente) {
		super();
		this.id = id;
		this.nome = nome;
		this.cnpj = cnpj;
		this.cep = cep;
		this.rua = rua;
		this.numero = numero;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
		this.telefone = telefone;
		this.email = email;
		this.categoria = cat;
		this.cliente = cliente;
	}
	
	
	
	public Empresa(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	
	public Empresa(Empresa obj) {
		id = obj.getId();
		nome = obj.getNome();
	}

	//Métodos de acesso - Getter e Setter
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	//comparar objetos pelo conteúdo e não pelo ponteiro, utilizando apenas o id
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empresa other = (Empresa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	

}
