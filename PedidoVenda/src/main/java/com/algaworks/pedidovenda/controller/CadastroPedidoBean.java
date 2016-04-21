package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.model.EnderecoEntrega;
import com.algaworks.pedidovenda.model.FormaPagamento;
import com.algaworks.pedidovenda.model.ItemPedido;
import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.model.Produto;
import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.repository.Usuarios;
import com.algaworks.pedidovenda.service.CadastroPedidoService;
import com.algaworks.pedidovenda.repository.Clientes;
import com.algaworks.pedidovenda.repository.Produtos;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.validation.SKU;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Usuarios usuarios;

	@Inject
	private Clientes clientes;

	@Inject
	private Produtos produtos;

	@Inject
	private CadastroPedidoService cadastroPedidoService;
	
	private String sku;
	
	@Produces
	@PedidoEdicao
	private Pedido pedido;
	private List<Usuario> vendedores;

	private Produto produtoLinhaEditavel;

	public void inicializar() {
		if (FacesUtil.isNotPostBack()) {
			this.vendedores = usuarios.vendedores();

			this.pedido.adicionarItemVazio();

			this.recalcularPedido();
		}

	}

	public CadastroPedidoBean() {
		limpar();

	}
	
	public void pedidoAlterado(@Observes PedidoAlteradoEvent event){
		this.pedido = event.getPedido();
	}

	public void salvar() {
		this.pedido.removerItemVazio();
		
		try{
		this.pedido = cadastroPedidoService.salvar(this.pedido);

		FacesUtil.addInfoMessage("Pedido salvo com sucesso!");
		} finally{
			this.pedido.adicionarItemVazio();
		}
	}

	public List<Cliente> completarCliente(String nome) {
		return this.clientes.listarNomes(nome);
	}

	public void recalcularPedido() {
		if (this.pedido != null) {
			this.pedido.recalcularValorTotal();
		}
	}
	
	public void carregarProdutoPorSku(){
		if (StringUtils.isNotEmpty(sku)){
			this.produtoLinhaEditavel = produtos.porSku(this.sku);
			this.carregarProdutoLinhaEditavel();
		}
	}
	
	public void atualizarQuantidade(ItemPedido item, int linha){
		if (item.getQuantidade() < 1){
			if (linha == 1){
				item.setQuantidade(1);
			} else {
				this.pedido.getItens().remove(linha);
			}
		}
		this.pedido.recalcularValorTotal();
	}

	public FormaPagamento[] getFormasPagamento() {
		return FormaPagamento.values();
	}

	private void limpar() {
		pedido = new Pedido();
		pedido.setEnderecoEntrega(new EnderecoEntrega());
	}
	
	public List<Produto> completarProduto(String nome) {
		return this.produtos.porNome(nome);
	}

	public void carregarProdutoLinhaEditavel() {
		ItemPedido item = this.pedido.getItens().get(0);
		
		if (this.produtoLinhaEditavel != null){
			if (this.existenteItemComProduto(this.produtoLinhaEditavel)){
				FacesUtil.addErrorMessage("Já existe um item no pedido com o produto informado!");
			} else {
				item.setProduto(produtoLinhaEditavel);
				item.setValorUnitario(this.produtoLinhaEditavel.getValorUnitario());
				
				this.pedido.adicionarItemVazio();
				this.produtoLinhaEditavel = null;
				this.sku = null;
				this.pedido.recalcularValorTotal();
			}
			
		}
	}

	private boolean existenteItemComProduto(Produto produto) {
		boolean existeItem = false;
		
		for (ItemPedido item : this.getPedido().getItens()){
			if (produto.equals(item.getProduto())){
				existeItem = true;
				break;
			}
		}
		
		return existeItem;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public List<Usuario> getVendedores() {
		return vendedores;
	}

	public boolean isEditando() {
		return this.pedido.getId() != null;
	}

	public Usuarios getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Usuarios usuarios) {
		this.usuarios = usuarios;
	}

	public Clientes getClientes() {
		return clientes;
	}

	public void setClientes(Clientes clientes) {
		this.clientes = clientes;
	}

	public Produto getProdutoLinhaEditavel() {
		return produtoLinhaEditavel;
	}

	public void setProdutoLinhaEditavel(Produto produtoLinhaEditavel) {
		this.produtoLinhaEditavel = produtoLinhaEditavel;
	}
	@SKU
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
	
}