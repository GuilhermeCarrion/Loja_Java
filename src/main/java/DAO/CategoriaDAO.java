package DAO;

import model.Categoria;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
	
	// CREATE
	public int inserir(Categoria cat) {
		String sql = "INSERT INTO categorias (nome_categoria) VALUES (?)";
		int idGerado = 0;
		
		try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			
			stmt.setString(1, cat.getNome_categoria());
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.next()) {
				idGerado = rs.getInt(1);
			}
			
			System.out.println("Categoria inserifa com sucesso");
		} catch (SQLException e) {
			System.err.println("Erro ao inserir categoria " + e.getMessage());
			e.printStackTrace();
		}
		
		return idGerado;
	}
	
	// READ - Todas categorias
	public List<Categoria> listarTodas(){
		String sql = "SELECT * FROM categorias ORDER BY nome_categoria";
		List<Categoria> cats = new ArrayList<>();
		
		try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery()){
			
			while(rs.next()) {
				Categoria cat = new Categoria();
				cat.setId_categoria(rs.getInt("id_categoria"));
				cat.setNome_categoria(rs.getString("nome_categoria"));
				cats.add(cat);
			}
			
		} catch(SQLException e) {
			System.err.println("Erro ao listar categorias " + e.getMessage());
			e.printStackTrace();
		}
		
		return cats;
	}
	
	// READ - por ID
	public Categoria buscarPorId(int id) {
		String sql = "SELECT * FROM categorias WHERE id_categoria = ?";
		Categoria cat = null;
		
		try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            	
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				cat = new Categoria();
				cat.setId_categoria(rs.getInt("id_categoria"));
				cat.setNome_categoria(rs.getString("nome_categoria"));
			}
			
        } catch (SQLException e) {
        	System.err.println("Erro ao buscar categoria " + e.getMessage());
        	e.printStackTrace();
        }
		
		return cat;
	}
	
	// UPDATE 
	public boolean atualizar(Categoria cat) {
		String sql = "UPDATE categorias SET nome_categoria = ? WHERE id_categoria = ?";

		try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
			
			stmt.setString(1, cat.getNome_categoria());
			stmt.setInt(2, cat.getId_categoria());
			
			int totAfetado = stmt.executeUpdate();
			System.out.println("Categoria atulizada");
			
			return totAfetado > 0;
		}catch (SQLException e) {
        	System.err.println("Erro ao buscar categoria " + e.getMessage());
        	e.printStackTrace();
        	return false;
        }
	}
	
	// DELETE
	public boolean excluir(int id) {
		String sql = "DELETE FROM categorias WHERE id_categoria = ?";
		
		try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
			
			stmt.setInt(1, id);
			
			int totAfetado = stmt.executeUpdate();
			System.out.println("Categoria excluida");
			
			return totAfetado > 0;
			
		}catch (SQLException e) {
        	System.err.println("Erro ao buscar categoria " + e.getMessage());
        	e.printStackTrace();
        	return false;
        }
	}
	
	// Contador de produtos por categoria - Para impedir exclusaõ de categoria com produtos vinculados
	public int contadorProdutos(int idCategoria) {
		String sql = "SELECT COUNT(*) as total FROM produtos WHERE id_categoria = ?";
		int total = 0;
		

		try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
		
			stmt.setInt(1, idCategoria);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()){
				total = rs.getInt("total");
			}
			
		}catch (SQLException e) {
        	System.err.println("Erro ao buscar categoria " + e.getMessage());
        	e.printStackTrace();
        }
		
		return total;
	}
}
