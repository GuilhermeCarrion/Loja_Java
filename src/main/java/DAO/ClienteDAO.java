package DAO;

import java.sql.*;
import model.Cliente;
import util.ConnectionFactory;

public class ClienteDAO {
	
	public Cliente login(String email, String senha) {
		Cliente cliente = null;
		String sql = "SELECT * FROM clientes WHERE email_cliente = ? AND senha_cliente = ?";
		
		try(Connection conn = ConnectionFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql)){
			
			stmt.setString(1, email);
			stmt.setString(2, senha);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				cliente = new Cliente();
				cliente.setId_cliente(rs.getInt("id"));
				cliente.setNome_cliente(rs.getString("nome"));
				cliente.setEmail_cliente(rs.getString("email"));
				cliente.setSenha_cliente(rs.getString("senha"));
			}
		} catch(SQLException e) {
			System.out.println("Erro ao fazer login: " + e.getMessage());
		}
		
		return cliente;
	}
}
