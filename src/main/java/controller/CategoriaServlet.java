package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import DAO.CategoriaDAO;
import model.Categoria;

@WebServlet("/categoria")
public class CategoriaServlet extends HttpServlet {
	private static final long serialVersion = 1L;
	private CategoriaDAO dao = new CategoriaDAO();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
		
		String action = req.getParameter("action");
		
		if(action == null) {
			action = "listar";
		}
		
		switch(action) {
			case "novo":
				mostrarFormNovo(req, res);
				break;
			case "editar":
				mostrarFormEditar(req, res);
				break;
			case "excluir":
				excluirCategoria(req, res);
				break;
			default:
				listarCategorias(req, res);
				break;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
		
		String action = req.getParameter("action");
		
		if("inserir".equals(action)) {
			inserirCategoria(req, res);
		}else if("atualizar".equals(action)){
			atualizarCategoria(req,res);
		}
	}
	
	//Listar todas
	private void listarCategorias(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
		List<Categoria> cats = dao.listarTodas();
		req.setAttribute("categorias", cats);
		RequestDispatcher rd = req.getRequestDispatcher("/produto/categorias.jsp");
		rd.forward(req, res);
	}
	
	//Form nova categoria
	private void mostrarFormNovo(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher("/produto/categoria_form.jsp");
		rd.forward(req, res);
	}
	
	//Form UPDATE
	private void mostrarFormEditar(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
		
		int id = Integer.parseInt(req.getParameter("id"));
		Categoria cat = dao.buscarPorId(id);
		
		req.setAttribute("categoria", cat);
		RequestDispatcher rd = req.getRequestDispatcher("/produto/categoria_form.jsp");
		rd.forward(req, res);
	}
	
	// CREATE 
	private void inserirCategoria(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
		
		try {
			String nome = req.getParameter("nome");
			
			if(nome == null || nome.trim().isEmpty()) {
				req.setAttribute("erro", "Informe o nome da categoria");
				RequestDispatcher rd = req.getRequestDispatcher("/categoria/categoria_form.jsp");
				rd.forward(req, res);
				return;
			}
			
			Categoria cat = new Categoria();
			cat.setNome_categoria(nome.trim());
			
			dao.inserir(cat);
			
			res.sendRedirect(req.getContextPath() + "/categoria?action=listar");
		} catch (Exception e){
			e.printStackTrace();
            req.setAttribute("erro", "Erro ao inserir categoria: " + e.getMessage());
            RequestDispatcher rd = req.getRequestDispatcher("/categoria/form.jsp");
            rd.forward(req, res);
		}
	}
	
	//UPDATE 
	private void atualizarCategoria(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
		
		try {
			int id = Integer.parseInt(req.getParameter("id"));
			String nome = req.getParameter("nome");
			
			if(nome == null || nome.trim().isEmpty()) {
				req.setAttribute("erro", "Informe o nome da categoria");
				mostrarFormEditar(req, res);
				return;
			}
			
			Categoria cat = new Categoria();
			cat.setId_categoria(id);
			cat.setNome_categoria(nome.trim());
			
			dao.atualizar(cat);
			
			res.sendRedirect(req.getContextPath() + "/categoria?action=listar");
		} catch (Exception e){
			e.printStackTrace();
            req.setAttribute("erro", "Erro ao inserir categoria: " + e.getMessage());
            mostrarFormEditar(req, res);
		}
	}
	
	// DELETE
	private void excluirCategoria(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
		try {
			int id = Integer.parseInt(req.getParameter("id"));
			
			int totProdutos = dao.contadorProdutos(id);
			
			if(totProdutos > 0) {
				req.setAttribute("erro", "Não é possivel excluir uma categoria com produtos atribuidos");
				listarCategorias(req, res);
				return;
			}
			
			dao.excluir(id);
			res.sendRedirect(req.getContextPath() + "/categoria?action=listar");
		} catch (Exception e) {
			e.printStackTrace();
            req.setAttribute("erro", "Erro ao inserir categoria: " + e.getMessage());
            mostrarFormEditar(req, res);
		}
		
	}
}
