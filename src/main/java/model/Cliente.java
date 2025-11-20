package model;

public class Cliente {
	private int id_cliente;
	private String nome_cliente;
	private String email_cliente;
	private String senha_cliente;
	
	public Cliente() {
		
	}

	public Cliente(int id_cliente, String nome_cliente, String email_cliente, String senha_cliente) {
		this.id_cliente = id_cliente;
		this.nome_cliente = nome_cliente;
		this.email_cliente = email_cliente;
		this.senha_cliente = senha_cliente;
	}
	
	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public String getNome_cliente() {
		return nome_cliente;
	}

	public void setNome_cliente(String nome_cliente) {
		this.nome_cliente = nome_cliente;
	}

	public String getEmail_cliente() {
		return email_cliente;
	}

	public void setEmail_cliente(String email_cliente) {
		this.email_cliente = email_cliente;
	}

	public String getSenha_cliente() {
		return senha_cliente;
	}

	public void setSenha_cliente(String senha_cliente) {
		this.senha_cliente = senha_cliente;
	}
	
	
}
