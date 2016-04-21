package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Grupo;
import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.service.CadastroUsuarioService;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroUsuarioService cadastroUsuarioService;

	private Usuario usuario;
	private List<Grupo> grupos;

	public CadastroUsuarioBean() {
		usuario = new Usuario();
	}

	private void limpar() {
		usuario = new Usuario();

	}

	public void inicializar() {
	
	}

	public void salvar() {
		this.usuario = cadastroUsuarioService.salvar(usuario);
		limpar();
		FacesUtil.addInfoMessage("Usuário salvo com sucesso!");
	}
	
	public boolean isEditando(){
		return this.usuario.getId() != null;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

}
