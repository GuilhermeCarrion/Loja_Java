package DAO;

import java.sql.*;
import model.Cliente;
import util.ConnectionFactory;

public class ClienteDAO {
	
	public int inserir(Cliente cliente) {
		String sql = "INSERT INTO clientes (cpf_cliente, endereco_cliente, telefone_cliente) VALUES (?, ?, ?)";
		int idGerado = 0;
		
		try(Connection conn = ConnectionFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			
			stmt.setString(1, cliente.getCpf_cliente());
			stmt.setString(2, cliente.getEndereco_cliente());
			stmt.setString(3, cliente.getTelefone_cliente());
			
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.next()) {
				idGerado = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.err.println("Erro ao inserir cliente" + e.getMessage());
			e.printStackTrace();
		}
		return idGerado;
	}
	
	public boolean atualizar(Cliente cliente) {
		String sql = "UPDATE clientes SET cpf_cliente = ?, endereco_cliente = ?, telefone_cliente = ? WHERE id_cliente = ?";
		
		try(Connection conn = ConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			
			stmt.setString(1, cliente.getCpf_cliente());
			stmt.setString(2, cliente.getEndereco_cliente());
			stmt.setString(3, cliente.getTelefone_cliente());
			stmt.setInt(4, cliente.getId());
			
			int totAfetado = stmt.executeUpdate();
			return totAfetado > 0;
		} catch (SQLException e) {
			System.err.println("Erro ao atualiza cliente" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public Cliente buscarPorId(int idCliente) {
		String sql = "SELECT * FROM clientes WHERE id_cliente = ?";
		Cliente cliente = null;
		
		try(Connection conn = ConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			
			stmt.setInt(1, idCliente);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				cliente = new Cliente();
				cliente.setId(rs.getInt("id_cliente"));
				cliente.setCpf_cliente(rs.getString("cpf_cliente"));
				cliente.setEndereco_cliente(rs.getString("endereco_cliente"));
				cliente.setTelefone_cliente(rs.getString("telefone_cliente"));
			}
		} catch (SQLException e) {
			System.err.println("Erro ao atualiza cliente" + e.getMessage());
			e.printStackTrace();
		}
		
		return cliente;
	}
	
}
