package com.willianfernando.novoprojeto.DTO;

import java.io.Serializable;

import com.willianfernando.novoprojeto.domain.Empresa;

public class EmpresaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

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
	private Integer categoriaId;
	private Integer clienteId;
	
	public EmpresaDTO() {}
	
	public EmpresaDTO(Empresa obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.cep = obj.getCep();
		this.rua = obj.getRua();
		this.numero = obj.getNumero();
		this.bairro = obj.getBairro();
		this.cidade = obj.getCidade();
		this.estado = obj.getEstado();
		this.telefone = obj.getTelefone();
		this.email = obj.getEmail();
		this.cnpj = obj.getCnpj();
		this.categoriaId = obj.getCategoria().getId();
		this.clienteId = obj.getCliente().getId();
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
		this.nome= nome;
	}
	
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Integer getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Integer categoriaId) {
		this.categoriaId = categoriaId;
	}

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
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
	
}
