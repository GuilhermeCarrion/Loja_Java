package model;

public class Cliente {
	private int id;
	private String cpf_cliente;
	private String endereco_cliente;
	private String telefone_cliente;
	
	public Cliente() {}

	public Cliente(int id, String cpf_cliente, String endereco_cliente, String telefone_cliente) {
		super();
		this.id = id;
		this.cpf_cliente = cpf_cliente;
		this.endereco_cliente = endereco_cliente;
		this.telefone_cliente = telefone_cliente;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCpf_cliente() {
		return cpf_cliente;
	}

	public void setCpf_cliente(String cpf_cliente) {
		this.cpf_cliente = cpf_cliente;
	}

	public String getEndereco_cliente() {
		return endereco_cliente;
	}

	public void setEndereco_cliente(String endereco_cliente) {
		this.endereco_cliente = endereco_cliente;
	}

	public String getTelefone_cliente() {
		return telefone_cliente;
	}

	public void setTelefone_cliente(String telefone_cliente) {
		this.telefone_cliente = telefone_cliente;
	}

	
}
