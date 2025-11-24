package DAO;

import model.Produto;
import util.ConnectionFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
	
	// Create prod
	public int inserir(Produto produto) {
		String sql = "INSERT INTO produtos (nome_produto, descricao_produto, preco_produto, estoque_produto, id_categoria) VALUES (?, ?, ?, ?, ?)";
		int idGerado = 0;
		
		try(Connection conn = ConnectionFactory.getConnection();
	            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			
			stmt.setString(1, produto.getNome_produto());
			stmt.setString(2, produto.getDescricao_produto());
			stmt.setBigDecimal(3, produto.getPreco_produto());
			stmt.setInt(4, produto.getEstoque_produto());
			
			
			
			if(produto.getId_categoria() != null) {
				stmt.setInt(5, produto.getId_categoria());
			} else {
				stmt.setNull(5, Types.INTEGER);
			}
			
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.next()) {
				idGerado = rs.getInt(1);
			}
			
			System.out.println("Produto inserido com sucesso");
		} catch (SQLException e) {
			System.err.println("Erro ao inserir produto " + e.getMessage());
			e.printStackTrace();
		}
		
		return idGerado;
	}
	
	// Read prod - listar TODOS produtos com LEFT JOIN para pegar nome_categoria (Exibir nome categoria na tela Produtos)
	public List<Produto> listarTodos(){
		String sql = "SELECT p.*, c.nome_categoria FROM produtos p LEFT JOIN categorias c ON p.id_categoria = c.id_categoria ORDER BY p.id_produto DESC";
		
		// Todos produtos
		List<Produto> prods = new ArrayList<>();
		
		try(Connection conn = ConnectionFactory.getConnection();
	            PreparedStatement stmt = conn.prepareStatement(sql);
	            ResultSet rs = stmt.executeQuery()){
			
			while(rs.next()) {
				// Produto que sera adicionado na lista de todos os produtos 
				Produto prod = new Produto();
				prod.setId_produto(rs.getInt("id_produto"));
				prod.setNome_produto(rs.getString("nome_produto"));
				prod.setDescricao_produto(rs.getString("descricao_produto"));
				prod.setPreco_produto(rs.getBigDecimal("preco_produto"));
				prod.setEstoque_produto(rs.getInt("estoque_produto"));
				
				// Pegando o id da categoria para a consguir o nome da categoria
				int idCat = rs.getInt("id_categoria");
				
				// Verificando se esta null
				if(!rs.wasNull()) {
					prod.setId_categoria(idCat);
				}
				
				// Atribuindo o nome da categoria apos pegar o idCat
				prod.setNome_categoria(rs.getString("nome_categoria"));
				
				// Adicionando o produto a lista 
				prods.add(prod);
			}
		} catch (SQLException e) {
			System.err.println("Erro ao listar produtos " + e.getMessage());
			e.printStackTrace();
		}
		
		// Retorna lista com produtos
		return prods;
	}
	
	// READ - Por Id_produto (Usado para editar/exibir detalhes) -> Funcionalidade da tela de Produtos
	public Produto buscarPorId(int id) {
		String sql = "SELECT p.*, c.nome_categoria FROM produtos p LEFT JOIN categorias c ON p.id_categoria = c.id_categoria WHERE p.id_produto = ?";
		
		Produto prod = null;
		
		try(Connection conn = ConnectionFactory.getConnection();
	            PreparedStatement stmt = conn.prepareStatement(sql)){
			
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				prod = new Produto();
				prod.setId_produto(rs.getInt("id_produto"));
				prod.setNome_produto(rs.getString("nome_produto"));
				prod.setDescricao_produto(rs.getString("descricao_produto"));
				prod.setPreco_produto(rs.getBigDecimal("preco_produto"));
				prod.setEstoque_produto(rs.getInt("estoque_produto"));
				
				// Pegando o id da categoria para a consguir o nome da categoria
				int idCat = rs.getInt("id_categoria");
				
				// Verificando se esta null
				if(!rs.wasNull()) {
					prod.setId_categoria(idCat);
				}
				
				// Atribuindo o nome da categoria apos pegar o idCat
				prod.setNome_categoria(rs.getString("nome_categoria"));
			}
		} catch (SQLException e) {
			System.err.println("Erro ao buscar produto " + e.getMessage());
			e.printStackTrace();
		}
		
		// Retornando produto com id_produto = id de busca
		return prod;
	}
	
	// UPDATE
	public boolean atualizar(Produto prod) {
		String sql = "UPDATE produtos SET nome_produto = ?, descricao_produto = ?, preco_produto = ?, estoque_produto = ?, id_categoria = ? WHERE id_produto = ?";
		
		try(Connection conn = ConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			
			stmt.setString(1, prod.getNome_produto());
			stmt.setString(2, prod.getDescricao_produto());
			stmt.setBigDecimal(3, prod.getPreco_produto());
			stmt.setInt(4, prod.getEstoque_produto());
			
			//System.out.println("SQL: " + sql);
	        //System.out.println("ID: " + prod.getId_produto());
	        //System.out.println("Nome: " + prod.getNome_produto());
	        //System.out.println("Descrição: " + prod.getDescricao_produto());
	        //System.out.println("Preço: " + prod.getPreco_produto());
	        //System.out.println("Estoque: " + prod.getEstoque_produto());
	        //System.out.println("ID Categoria: " + prod.getId_categoria());
			
			if(prod.getId_categoria() != null) {
				stmt.setInt(5, prod.getId_categoria());
			} else {
				stmt.setNull(5, Types.INTEGER);
			}
			
			stmt.setInt(6, prod.getId_produto());
			
			int totAfetado = stmt.executeUpdate();
			//System.out.println("Produto atualizado! " + totAfetado);
			
			return totAfetado > 0;
		} catch (SQLException e) {
			System.err.println("Erro ao atualizar produto: " + e.getMessage());
            e.printStackTrace();
            return false;
		}
	}
	
	// DELETE
	public boolean excluir(int id) {
		String sql = "DELETE FROM produtos WHERE id_produto = ?";
		
		//System.out.println("=== EXCLUIR PRODUTO - DAO ===");
        //System.out.println("SQL: " + sql);
        //System.out.println("ID: " + id);
		
		try(Connection conn = ConnectionFactory.getConnection();
	            PreparedStatement stmt = conn.prepareStatement(sql)){
			
			stmt.setInt(1, id);
			
			int totAfetado = stmt.executeUpdate();
			System.out.println("Produto excluido");
			
			return totAfetado > 0;
			
		} catch(SQLException e) {
            System.err.println("Erro ao excluir produto: " + e.getMessage());
            e.printStackTrace();
            return false;
		}
	}
}
