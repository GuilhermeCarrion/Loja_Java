package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import DAO.ProdutoDAO;
import model.Produto;

@WebServlet("/produto")
public class ProdutoServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private ProdutoDAO dao = new ProdutoDAO();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
				throws ServletException, IOException {
		
		String action = req.getParameter("action");
		
		// Action é nulla quando a tela é exibida
		if(action == null) {
			action = "listar";
		}
		
		// Tratamento de action(funcionalidades)
		switch(action) {
			case "novo":
				mostrarFormNovo(req, res);
				break;
			case "editar":
				mostrarFormEditar(req, res);
				break;
			case "excluir":
				excluirProduto(req, res);
				break;
			default:
				listarProdutos(req, res);
				break;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
		
		String action = req.getParameter("action");
		
		if("inserir".equals(action)) {
			inserirProduto(req, res);
		} else if("atualizar".equals(action)) {
			atualizarProduto(req, res);
		}
	}
	
	// Listando TODOS os produtos - READ
	private void listarProdutos(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		List<Produto> prods = dao.listarTodos();
		req.setAttribute("produtos", prods);
		RequestDispatcher rd = req.getRequestDispatcher("/produto/produtos.jsp");
		rd.forward(req, res);
	}
	
	// Formulario novo produto - CREATE
	private void mostrarFormNovo(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		RequestDispatcher rd = req.getRequestDispatcher("/produto/produto_form.jsp");
		rd.forward(req, res);
	}
	
	// Formulario com dados do produto - UPDATE
	private void mostrarFormEditar(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		int id = Integer.parseInt(req.getParameter("id"));
		Produto prod = dao.buscarPorId(id);
		
		req.setAttribute("produto", prod);
		RequestDispatcher rd = req.getRequestDispatcher("/produto/produto_form.jsp");
		rd.forward(req, res);
	}
	
	// Função para inserir novo produto
	private void inserirProduto(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		try {
			// Armazenando dados do Form
			String nome = req.getParameter("nome");
			String descricao = req.getParameter("descricao");
			BigDecimal preco = new BigDecimal(req.getParameter("preco"));
			int estoque = Integer.parseInt(req.getParameter("estoque"));
			String categoria = req.getParameter("categoria");
			
			System.out.println(categoria);
			
			Produto prod = new Produto();
			prod.setNome_produto(nome);
			prod.setDescricao_produto(descricao);
			prod.setPreco_produto(preco);
			prod.setEstoque_produto(estoque);
			
			if(categoria != null && !categoria.isEmpty()) {
				int idCat = Integer.parseInt(categoria);
				prod.setId_categoria(idCat);
			}
			
			// Inserindo produto com dados do form
			dao.inserir(prod);
			
			// Redirecionando para a pagina de produtos listando todos
			res.sendRedirect(req.getContextPath() + "/produto?action=listar");
			
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("erro", "Erro ao inserir produto" + e.getMessage());
			RequestDispatcher rd = req.getRequestDispatcher("/produto/produto_form.jsp");
			rd.forward(req, res);
		}
	}
	
	// Atualizar produto por id
	private void atualizarProduto(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		try {
			
			//System.out.println("ID (string): [" + req.getParameter("id") + "]");
            //System.out.println("Nome: " + req.getParameter("nome"));
            //System.out.println("Categoria (string): [" + req.getParameter("categoria") + "]");
            
            String idStr = req.getParameter("id");
            if(idStr == null || idStr.trim().isEmpty()) {
                throw new Exception("ID do produto não foi enviado!");
            }
			
			// Mesmo processo de Inserir
            int id = Integer.parseInt(idStr);
			String nome = req.getParameter("nome");
			String descricao = req.getParameter("descricao");
			BigDecimal preco = new BigDecimal(req.getParameter("preco"));
			int estoque = Integer.parseInt(req.getParameter("estoque"));
			String categoria = req.getParameter("categoria");
			
			Produto prod = new Produto();
			//System.out.println("ID depois do SET: " + prod.getId_produto());
			prod.setId_produto(id);
			prod.setNome_produto(nome);
			prod.setDescricao_produto(descricao);
			prod.setPreco_produto(preco);
			prod.setEstoque_produto(estoque);
			
			if(categoria != null && !categoria.isEmpty()) {
				int idCat = Integer.parseInt(categoria);
				prod.setId_categoria(idCat);
			}
			
			 //boolean sucesso = dao.atualizar(prod);
	         //System.out.println("Atualização: " + (sucesso ? "SUCESSO" : "FALHOU"));
			
			// Altualizando com dados do form
			dao.atualizar(prod);
			
			res.sendRedirect(req.getContextPath() + "/produto?action=listar");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("erro", "Erro ao inserir produto" + e.getMessage());
			// Exibe novamente form com dados do banco
			mostrarFormEditar(req, res);
		}
	}
	
	// Excluir
	private void excluirProduto(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String idStr = req.getParameter("id");
        
        int id = Integer.parseInt(idStr);
        //System.out.println("ID parseado: " + id);
        
        //boolean sucesso = dao.excluir(id);
        //System.out.println("Exclusão: " + (sucesso ? "SUCESSO" : "FALHOU"));
		
        dao.excluir(id);
        
		res.sendRedirect(req.getContextPath() + "/produto?action=listar");
	}
	
}
