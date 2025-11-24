package DAO;

import model.ItemPedido;
import util.ConnectionFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemPedidoDAO {
	
	// CREATE
	public boolean inserir(ItemPedido item) {
		String sql = "INSERT INTO item_pedido (id_pedido, id_produto, quantidade_item, preco_unitario) VALUES (?, ?, ?, ?)";
		
		try(Connection conn = ConnectionFactory.getConnection();
	            PreparedStatement stmt = conn.prepareStatement(sql)){
			
			stmt.setInt(1, item.getId_pedido());
			stmt.setInt(2, item.getId_produto());
			stmt.setInt(3, item.getQuantidade_item());
			stmt.setBigDecimal(4, item.getPreco_unitario());
			
			int totAfetado = stmt.executeUpdate();
			return totAfetado > 0;
		} catch (SQLException e) {
			System.err.println("Erro ao inserir item do pedido" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	// READ - Itens de um pedido especifico
	public List<ItemPedido> listarPorPedido(int idPedido){
		String sql = "SELECT ip.*, p.nome_produto FROM item_pedido ip INNER JOIN produtos p ON ip.id_produto = p.id_produto WHERE ip.id_pedido = ?";
		
		List<ItemPedido> itens = new ArrayList<>();
		
		try(Connection conn = ConnectionFactory.getConnection();
	            PreparedStatement stmt = conn.prepareStatement(sql)){
			 
			stmt.setInt(1, idPedido);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				ItemPedido item = new ItemPedido();
				item.setId_pedido(rs.getInt("id_pedido"));
				item.setId_produto(rs.getInt("id_produto"));
				item.setQuantidade_item(rs.getInt("quantidade_item"));
				item.setPreco_unitario(rs.getBigDecimal("preco_unitario"));
				item.setNome_produto(rs.getString("nome_produto"));
				
				itens.add(item);
			}
		} catch(SQLException e) {
			System.err.println("Erro ao listar item do pedido especifico" + e.getMessage());
			e.printStackTrace();
		}
		
		return itens;
	}
	
	// DELETE
	public boolean excluirPorPedido(int idPedido) {
		String sql = "DELETE FROM item_pedido WHERE id_pedido = ?";
		
		try(Connection conn = ConnectionFactory.getConnection();
	            PreparedStatement stmt = conn.prepareStatement(sql)){
			
			stmt.setInt(1, idPedido);
			stmt.executeUpdate();
			return true;
			
		} catch(SQLException e) {
			System.err.println("Erro ao excluir item do pedido" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
}
