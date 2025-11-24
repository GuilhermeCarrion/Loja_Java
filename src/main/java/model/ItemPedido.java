package model;

import java.math.BigDecimal;

public class ItemPedido {
	private int id_pedido;
	private int id_produto;
	private int quantidade_item;
	private BigDecimal preco_unitario;
	
	// Info produto
	private String nome_produto;
	
	public ItemPedido() {}

	public ItemPedido(int id_pedido, int id_produto, int quantidade_item, BigDecimal preco_unitario) {
		super();
		this.id_pedido = id_pedido;
		this.id_produto = id_produto;
		this.quantidade_item = quantidade_item;
		this.preco_unitario = preco_unitario;
	}
	
	// Calcular Subtotal
	public BigDecimal getSubtotal() {
		return preco_unitario.multiply(new BigDecimal(quantidade_item));
	}

	public int getId_pedido() {
		return id_pedido;
	}

	public void setId_pedido(int id_pedido) {
		this.id_pedido = id_pedido;
	}

	public int getId_produto() {
		return id_produto;
	}

	public void setId_produto(int id_produto) {
		this.id_produto = id_produto;
	}

	public int getQuantidade_item() {
		return quantidade_item;
	}

	public void setQuantidade_item(int quantidade_item) {
		this.quantidade_item = quantidade_item;
	}

	public BigDecimal getPreco_unitario() {
		return preco_unitario;
	}

	public void setPreco_unitario(BigDecimal preco_unitario) {
		this.preco_unitario = preco_unitario;
	}

	public String getNome_produto() {
		return nome_produto;
	}

	public void setNome_produto(String nome_poduto) {
		this.nome_produto = nome_poduto;
	}
	
	
}
