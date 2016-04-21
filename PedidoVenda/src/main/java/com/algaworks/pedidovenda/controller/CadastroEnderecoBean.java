package com.algaworks.pedidovenda.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Endereco;

@Named
@ViewScoped
public class CadastroEnderecoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Endereco endereco;
	private Endereco enderecoSelecionado;

	public CadastroEnderecoBean() {
		endereco = new Endereco();
		this.enderecoSelecionado = new Endereco();
	}

	public void salvar() {
		
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Endereco getEnderecoSelecionado() {
		return enderecoSelecionado;
	}

	public void setEnderecoSelecionado(Endereco enderecoSelecionado) {
		this.enderecoSelecionado = enderecoSelecionado;
	}

}
