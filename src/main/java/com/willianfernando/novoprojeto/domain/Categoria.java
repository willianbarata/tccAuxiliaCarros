package com.willianfernando.novoprojeto.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


//Essa classe possui um relacionamento muitos pra muitos com a classe Empresa (existe uma tabela entre as duas efetuando devidas referências)


@Entity
public class Categoria implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
	//retirando referencia ciclica entre categoria e empresa -- suas listas serializa Json corretamente
	//@JsonManagedReference
	//uma categoria possui várias empresas, 
	//@ManyToMany(mappedBy="categorias") //referenciando mappeamento da classe Empresa
	@JsonIgnore
	@OneToMany(mappedBy="categoria")
	private List<Empresa> empresas = new ArrayList<>();

	//construtor vazio
	public Categoria() {}

	//construtor completo aepnas sem o list
	public Categoria(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public Categoria(Categoria obj) {
		id = obj.getId();
		nome = obj.getNome();
	}
	
	//Métodos de acesso - Getter e Setter
	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

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
		Categoria other = (Categoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
