package com.algaworks.pedidovenda.controller;

public class Clientes {
	private String nome;
	private String tipo;
	private String cnpj;
	private Endereco endereco;

	
	public Clientes(String nome, String tipo, String cnpj) {
		this.nome = nome;
		this.tipo = tipo;
		this.cnpj = cnpj;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
}
