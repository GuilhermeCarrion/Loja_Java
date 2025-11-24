package DAO;

import model.Pedido;
import util.ConnectionFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {
	
	// CREATE 
	public int inserir(Pedido pedido) {
		String sql = "INSERT INTO pedidos (id_cliente, total_pedido, status_pedido) VALUES (?, ?, ?)";
		int idGerado = 0;
		
		try(Connection conn = ConnectionFactory.getConnection();
	            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
		
			stmt.setInt(1, pedido.getId_cliente());
			stmt.setBigDecimal(2, pedido.getTotal_pedido());
			
			// GARANTIR que status nunca seja NULL
            String status = pedido.getStatus_pedido();
            if(status == null || status.trim().isEmpty()) {
                status = "Em andamento";
            }
			
			stmt.setString(3, status);
			
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.next()) {
				idGerado = rs.getInt(1);
			}
			
			System.out.println("Pedido inserido com sucesso");
			
		} catch(SQLException e) {
			System.err.println("Erro ao inserir item do pedido");
			e.printStackTrace();
		}
		
		return idGerado;
	}
	
	// Listar todos os pedidos
	public List<Pedido> listarTodos(){
		String sql = "SELECT p.*, c.nome_cliente FROM pedidos p INNER JOIN clientes c ON p.id_cliente = c.id_cliente ORDER BY p.data_pedido DESC";
		
		List<Pedido> pedidos = new ArrayList<>();
		
		try(Connection conn = ConnectionFactory.getConnection();
	            PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()){
			
			while(rs.next()) {
				Pedido pedido = new Pedido();
				pedido.setId_pedido(rs.getInt("id_pedido"));
				pedido.setId_cliente(rs.getInt("id_cliente"));
				pedido.setData_pedido(rs.getTimestamp("data_pedido").toLocalDateTime());
				pedido.setTotal_pedido(rs.getBigDecimal("total_pedido"));
				
                String status = rs.getString("status_pedido");
                if(status == null || status.trim().isEmpty()) {
                    status = "Em andamento";
                }
                
				pedido.setStatus_pedido(status);
				pedido.setNome_cliente(rs.getString("nome_cliente"));
				
				pedidos.add(pedido);
			}
			
		} catch(SQLException e) {
			System.err.println("Erro ao listar todos pedido" + e.getMessage());
			e.printStackTrace();
		}
		
		return pedidos;
	}
	
	//Listar pedidos de um cliente
	public List<Pedido> listarPorCliente(int idCliente){
		String sql = "SELECT * FROM pedidos WHERE id_cliente = ? ORDER BY data_pedido DESC";
		
		List<Pedido> pedidos = new ArrayList<>();
        
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId_pedido(rs.getInt("id_pedido"));
                pedido.setId_cliente(rs.getInt("id_cliente"));
                pedido.setData_pedido(rs.getTimestamp("data_pedido").toLocalDateTime());
                pedido.setTotal_pedido(rs.getBigDecimal("total_pedido"));
                
                String status = rs.getString("status_pedido");
                if(status == null || status.trim().isEmpty()) {
                    status = "Em andamento";
                }
                pedido.setStatus_pedido(status);
                
                pedidos.add(pedido);
            }
            
        } catch(SQLException e) {
            System.err.println("Erro ao listar pedidos do cliente: " + e.getMessage());
            e.printStackTrace();
        }
        
        return pedidos;
	}
	
	// Buscar pedido por ID
    public Pedido buscarPorId(int id) {
        String sql = "SELECT p.*, c.nome_cliente FROM pedidos p INNER JOIN clientes c ON p.id_cliente = c.id_cliente WHERE p.id_pedido = ?";
        
        Pedido pedido = null;
        
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
                pedido = new Pedido();
                pedido.setId_pedido(rs.getInt("id_pedido"));
                pedido.setId_cliente(rs.getInt("id_cliente"));
                pedido.setData_pedido(rs.getTimestamp("data_pedido").toLocalDateTime());
                pedido.setTotal_pedido(rs.getBigDecimal("total_pedido"));
                
                String status = rs.getString("status_pedido");
                if(status == null || status.trim().isEmpty()) {
                    status = "Em andamento";
                }
                pedido.setStatus_pedido(status);
                
                pedido.setNome_cliente(rs.getString("nome_cliente"));
            }
            
        } catch(SQLException e) {
            System.err.println("Erro ao buscar pedido: " + e.getMessage());
            e.printStackTrace();
        }
        
        return pedido;
    }
    
    // UPDATE - Atualizar status do pedido
    public boolean atualizarStatus(int idPedido, String novoStatus) {
    	String sql = "UPDATE pedidos SET status_pedido = ? WHERE id_pedido = ?";
    	
    	try(Connection conn = ConnectionFactory.getConnection();
	            PreparedStatement stmt = conn.prepareStatement(sql)){
			
    		stmt.setString(1, novoStatus);
    		stmt.setInt(2, idPedido);
    		
    		int totAfetado = stmt.executeUpdate();
    		return totAfetado > 0;
    		
		} catch(SQLException e) {
			System.err.println("Erro ao atualizar status pedido" + e.getMessage());
			e.printStackTrace();
			return false;
		}
    }
    
    // DELETE
    public boolean excluir(int id) {
    	String sql = "DELETE FROM pedidos WHERE id_pedido = ?";
    	
    	try(Connection conn = ConnectionFactory.getConnection();
	            PreparedStatement stmt = conn.prepareStatement(sql)){
			
    		stmt.setInt(1, id);
    		
    		int totAfetado = stmt.executeUpdate();
    		return totAfetado > 0;
    		
		} catch(SQLException e) {
			System.err.println("Erro ao excluir pedido" + e.getMessage());
			e.printStackTrace();
			return false;
		}
    }
}
