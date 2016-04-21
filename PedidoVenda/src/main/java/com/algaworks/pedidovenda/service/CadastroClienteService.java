package com.algaworks.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.repository.Clientes;
import com.algaworks.pedidovenda.util.jpa.Transacional;

public class CadastroClienteService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Clientes clientes;

	@Transacional
	public Cliente salvar(Cliente cliente) {
		Cliente clienteExistente = (Cliente) clientes.porNome(cliente.getNome());
		// Produto produtoExistente = produtos.porSku(produto.getSku());

		if (clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new NegocioException("Já existe um Cliente com o nome: (" + cliente.getNome() + ") informado!");
		}

		return clientes.guardar(cliente);
	}

}
