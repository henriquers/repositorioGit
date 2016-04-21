package com.algaworks.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.algaworks.pedidovenda.model.Endereco;
import com.algaworks.pedidovenda.service.NegocioException;
import com.algaworks.pedidovenda.util.jpa.Transacional;

public class Enderecos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Endereco guardar(Endereco endereco) {
		return manager.merge(endereco);
	}

	@Transacional
	public void remover(Endereco endereco) {
		try {
			endereco = porId(endereco.getId());
			manager.remove(endereco);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Endereço não pode ser excluído!");
		}
	}

	public Endereco porId(Long id) {
		return manager.find(Endereco.class, id);
	}

	public List<Endereco> porLogradouro(String logradouro) {
		return this.manager.createQuery("from Produto where upper(logradouro) like :nome", Endereco.class)
				.setParameter("nome", logradouro.toUpperCase() + "%").getResultList();
	}

}