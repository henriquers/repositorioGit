package com.algaworks.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.model.StatusPedido;
import com.algaworks.pedidovenda.repository.Pedidos;
import com.algaworks.pedidovenda.util.jpa.Transacional;

public class EmissaoPedidoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	public CadastroPedidoService cadastroPedidoService;
	
	@Inject
	public EstoqueService estoqueService;
	
	@Inject
	public Pedidos pedidos;
	
	@Transacional
	public Pedido emitir(Pedido pedido) {
		pedido = this.cadastroPedidoService.salvar(pedido);
		
		// Verifica se o pedido pode ser emitido
		if (pedido.isNaoEmissivel()){ 
			throw new NegocioException("Pedido não pode ser emitido com Status: " + 
					pedido.getStatus().getDescricao());
		}
		// se o pedido puder ser emitido, baixa do estoque, altera o status e guarda no repositório
		
		this.estoqueService.baixarItensEstoque(pedido);
		
		pedido.setStatus(StatusPedido.EMITIDO); 
		
		pedido = this.pedidos.guardar(pedido); 
		
		return pedido;
	}

}
