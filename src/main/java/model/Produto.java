package model;

import java.math.BigDecimal;

public class Produto {
	private int id_produto;
	private String nome_produto;
	private String descricao_produto;
	private BigDecimal preco_produto;
	private int estoque_produto;
	private Integer id_categoria;
	private String nome_categoria;
	
	public Produto() {}

	public Produto(int id_produto, String nome_produto, String descricao_produto, BigDecimal preco_produto,
			int estoque_produto, Integer id_categoria, String nome_categoria) {
		super();
		this.id_produto = id_produto;
		this.nome_produto = nome_produto;
		this.descricao_produto = descricao_produto;
		this.preco_produto = preco_produto;
		this.estoque_produto = estoque_produto;
		this.id_categoria = id_categoria;
		this.nome_categoria = nome_categoria;
	}

	public int getId_produto() {
		return id_produto;
	}

	public void setId_produto(int id_produto) {
		this.id_produto = id_produto;
	}

	public String getNome_produto() {
		return nome_produto;
	}

	public void setNome_produto(String nome_produto) {
		this.nome_produto = nome_produto;
	}

	public String getDescricao_produto() {
		return descricao_produto;
	}

	public void setDescricao_produto(String descricao_produto) {
		this.descricao_produto = descricao_produto;
	}

	public BigDecimal getPreco_produto() {
		return preco_produto;
	}

	public void setPreco_produto(BigDecimal preco_produto) {
		this.preco_produto = preco_produto;
	}

	public int getEstoque_produto() {
		return estoque_produto;
	}

	public void setEstoque_produto(int estoque_produto) {
		this.estoque_produto = estoque_produto;
	}

	public Integer getId_categoria() {
		return id_categoria;
	}

	public void setId_categoria(Integer id_categoria) {
		this.id_categoria = id_categoria;
	}

	public String getNome_categoria() {
		return nome_categoria;
	}

	public void setNome_categoria(String nome_categoria) {
		this.nome_categoria = nome_categoria;
	}

	@Override
	public String toString() {
		return "Produto{" + 
					"id=" + id_produto +
					", nome='" + nome_produto + "\'" +
					", preco=" + preco_produto +
					", estoque=" + estoque_produto + 
					"}";
	}
}
