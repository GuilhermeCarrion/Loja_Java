package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
	private int id_pedido;
	private int id_cliente;
	private LocalDateTime data_pedido;
	private BigDecimal total_pedido;
	private String status_pedido;
	
	// Exibir nome do cliente
	private String nome_cliente;
	
	//Listar itens do pedido
	private List<ItemPedido> itens;
	
	public Pedido() {
		this.itens = new ArrayList<>();
	}

	public Pedido(int id_pedido, int id_cliente, LocalDateTime data_pedido, BigDecimal total_pedido,
			String status_pedido) {
		super();
		this.id_pedido = id_pedido;
		this.id_cliente = id_cliente;
		this.data_pedido = data_pedido;
		this.total_pedido = total_pedido;
		this.status_pedido = status_pedido;
		this.itens = new ArrayList<>();
	}

	public int getId_pedido() {
		return id_pedido;
	}

	public void setId_pedido(int id_pedido) {
		this.id_pedido = id_pedido;
	}

	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public LocalDateTime getData_pedido() {
		return data_pedido;
	}

	public void setData_pedido(LocalDateTime data_pedido) {
		this.data_pedido = data_pedido;
	}

	public BigDecimal getTotal_pedido() {
		return total_pedido;
	}

	public void setTotal_pedido(BigDecimal total_pedido) {
		this.total_pedido = total_pedido;
	}

	public String getStatus_pedido() {
		return status_pedido;
	}

	public void setStatus_pedido(String status_pedido) {
		this.status_pedido = status_pedido;
	}

	public String getNome_cliente() {
		return nome_cliente;
	}

	public void setNome_cliente(String nome_cliente) {
		this.nome_cliente = nome_cliente;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}
	
	public void addItem(ItemPedido item) {
		this.itens.add(item);
	}
}
