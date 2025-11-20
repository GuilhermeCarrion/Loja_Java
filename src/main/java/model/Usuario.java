package model;

public class Usuario {
	private int id;
	private String email;
	private String senha;
	private Integer id_cliente;
	
	public Usuario() {}
	
	public Usuario(int id, String email, String senha, Integer id_cliente) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.id_cliente = id_cliente;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Integer getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(Integer id_cliente) {
		this.id_cliente = id_cliente;
	}
	
	

}
