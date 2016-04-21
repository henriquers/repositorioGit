package com.algaworks.pedidovenda.controller;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.model.Endereco;
import com.algaworks.pedidovenda.repository.Enderecos;
import com.algaworks.pedidovenda.service.CadastroClienteService;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Produces
	@ClienteEdicao
	private Cliente cliente;
	
	private Endereco enderecoSelecionado;
	
	@Inject
	private CadastroClienteService cadastroClienteService;
	
	@Inject
	private Endereco endereco;
	
	@Inject
	private Enderecos enderecos;
	
	private Endereco enderecoEditar = new Endereco();

	public CadastroClienteBean() {
		limpar();
		endereco = new Endereco();
		cliente.setDocumentoReceitaFederal(null);
		

	}
	

	public void salvar() {
		this.cliente = this.cadastroClienteService.salvar(this.cliente);
		limpar();
		FacesUtil.addInfoMessage("Cliente salvo com sucesso!");
	}

	private void limpar() {
		cliente = new Cliente();

	}
	
	 public void adicionarEndereco() {
	        //this.cliente.getEnderecos().add(novoEndereco);
	        if (this.endereco != null && this.cliente != null) {

	            this.cliente.getEnderecos().add(this.enderecoEditar);
	            this.endereco.setCliente(this.cliente);
	            salvar();
	            System.out.println(endereco.getCep());
	            
	        } else {
	        	System.out.println("Cliente: " + cliente);
	        	
	            FacesUtil.addInfoMessage("selecione um endereco");
	        }
	        
	        endereco = new Endereco();
	    }
	
	public boolean isEditando() {
		return this.cliente.getId() != null;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void inicializar() {

	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEnderecoEditar() {
		return enderecoEditar;
	}

	public void setEnderecoEditar(Endereco enderecoEditar) {
		this.enderecoEditar = enderecoEditar;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public CadastroClienteService getCadastroClienteService() {
		return cadastroClienteService;
	}

	public Enderecos getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(Enderecos enderecos) {
		this.enderecos = enderecos;
	}


	public Endereco getEnderecoSelecionado() {
		return enderecoSelecionado;
	}


	public void setEnderecoSelecionado(Endereco enderecoSelecionado) {
		this.enderecoSelecionado = enderecoSelecionado;
	}

}
