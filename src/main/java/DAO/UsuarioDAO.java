package DAO;

import model.Usuario;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
	
	public void inserir(Usuario u) {
		String sql = "INSERT INTO usuarios (id_cliente, email_usuario, senha_usuario) VALUES (?, ?, ?)";
		
		try(Connection conn = ConnectionFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql)){
			
			if(u.getId_cliente() != null) {
				stmt.setInt(1, u.getId_cliente());
			}else {
				stmt.setNull(1, Types.INTEGER);
			}
			
			stmt.setString(2, u.getEmail());
			stmt.setString(3, u.getSenha());
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Erro ao inserir usuário: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public Usuario buscarPorEmail(String email) {
		String sql = "SELECT * FROM usuarios WHERE email_usuario = ?";
		Usuario u = null;
		
		try(Connection conn = ConnectionFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql)){
			
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				u = new Usuario();
				u.setId(rs.getInt("id_usuario"));
				u.setEmail(rs.getString("email_usuario"));
				u.setSenha(rs.getString("senha_usuario"));
				u.setId_cliente(rs.getInt("id_cliente"));
				
				int idCliente = rs.getInt("id_cliente");
				if(!rs.wasNull()) {
					u.setId_cliente(idCliente);
				}
			}
		} catch (SQLException e) {
			System.err.println("Erro ao buscar usuário: " + e.getMessage());
			e.printStackTrace();
		}
		
		return u;
	}
	
	public Usuario autenticar(String email, String senha) {
		Usuario u = buscarPorEmail(email);
		
		if(u != null && u.getSenha().equals(senha)) {
			return u;
		}
		
		return null;
	}
	
	public boolean vincularCliente(int idUsuario, int idCliente) {
		String sql = "UPDATE usuarios SET id_cliente = ? WHERE id_usuario = ?";
		
		try(Connection conn = ConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			
			stmt.setInt(1, idCliente);
			stmt.setInt(2, idUsuario);
			
			int totAfetado = stmt.executeUpdate();
			return totAfetado > 0;
		} catch (SQLException e) {
			System.err.println("Erro ao vincular cliente: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean atualizarSenha(int idUsuario, String novaSenha) {
		String sql = "UPDATE usuarios SET senha_usuario = ? WHERE id_usuario = ?";
		
		try(Connection conn = ConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setString(1, novaSenha);
			stmt.setInt(2, idUsuario);
			
			int totAfetado = stmt.executeUpdate();
			
			return totAfetado > 0;
		} catch (SQLException e) {
			System.err.println("Erro ao atualizar senha: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
}
