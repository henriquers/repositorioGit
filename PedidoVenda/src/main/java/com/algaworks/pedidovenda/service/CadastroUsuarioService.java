package com.algaworks.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.repository.Usuarios;
import com.algaworks.pedidovenda.util.jpa.Transacional;

public class CadastroUsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Usuarios usuarios;
	
	@Transacional
	public Usuario salvar(Usuario usuario){
		Usuario usuarioExistente = usuarios.porNome(usuario.getNome());
		
		if (usuarioExistente != null && !usuarioExistente.equals(usuario)){
			throw new NegocioException("Já existe um usuário com o nome informado!");
		}
		
		return usuarios.guardar(usuario);
		
	}

}
