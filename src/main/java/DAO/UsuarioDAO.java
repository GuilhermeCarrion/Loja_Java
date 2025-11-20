package DAO;

import model.Usuario;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
	
	public void inserir(Usuario u) {
		String sql = "INSERT INTO usuarios (id_cliente, email, senha) VALUES (?, ?, ?)";
		
		try(Connection conn = ConnectionFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql)){
			
			if(u.getId_cliente() != null) {
				ps.setInt(1, u.getId_cliente());
			}else {
				ps.setNull(1, Types.INTEGER);
			}
			
			ps.setString(2, u.getEmail());
			ps.setString(3, u.getSenha());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Usuario buscarPorEmail(String email) {
		String sql = "SELECT * FROM usuarios WHERE email_usuario = ?";
		Usuario u = null;
		
		try(Connection conn = ConnectionFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql)){
			
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				u = new Usuario();
				u.setId(rs.getInt("id_usuario"));
				u.setEmail(rs.getString("email_usuario"));
				u.setSenha(rs.getString("senha_usuario"));
				u.setId_cliente(rs.getInt("id_cliente"));
			}
		} catch (SQLException e) {
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
}
