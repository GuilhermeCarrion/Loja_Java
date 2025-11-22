package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import DAO.ClienteDAO;
import DAO.UsuarioDAO;
import model.Cliente;
import model.Usuario;

@WebServlet("/cliente")
public class ClienteServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
		
			String action = request.getParameter("action");
			
			if("completar_cadastro".equals(action)) {
				completarCadastro(request, response);
			} else if("atualizar".equals(action)) {
				atualizarDados(request, response);
			}
		}
	
	// Cadastrar apos logar
	private void completarCadastro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Usuario usu = (Usuario) session.getAttribute("usuarioLogado");
		
		if(usu == null) {
			response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
			return;
		}
		 // Dados FOrm
		String cpf = request.getParameter("cpf");
		String endereco = request.getParameter("endereco");
		String teltefone = request.getParameter("telefone");
		
		// New cliente
		Cliente cliente = new Cliente();
		cliente.setCpf_cliente(cpf);
		cliente.setEndereco_cliente(endereco);
		cliente.setTelefone_cliente(teltefone);
		
		ClienteDAO dao = new ClienteDAO();
		int idCliente = dao.inserir(cliente);
		
		if(idCliente > 0) {
			// Atualizando o cad do usuario com id do cliente
			usu.setId_cliente(idCliente);
			
			UsuarioDAO usuDAO = new UsuarioDAO();
			usuDAO.vincularCliente(usu.getId(), idCliente);
			
			// Atualizando a session com cliente = logado
			session.setAttribute("usuarioLogado", usu);
			session.setAttribute("clienteLogado", cliente);
			
			response.sendRedirect(request.getContextPath() + "/pages/index.jsp");
		}else {
			request.setAttribute("erro", "Erro ao completar cadastro");
			RequestDispatcher rd = request.getRequestDispatcher("/cliente/comp_cad.jsp");
			rd.forward(request, response);
		}
	}
	
	private void atualizarDados(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Usuario usu = (Usuario) session.getAttribute("usuarioLogado");
		
		if(usu == null || usu.getId_cliente() == null) {
			response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
			return;
		}
		
		String cpf = request.getParameter("cpf");
		String endereco = request.getParameter("endereco");
		String telegone = request.getParameter("telefone");
		String senha_atual = request.getParameter("senha_atual");
		String nova_senha = request.getParameter("nova_senha");
		
		ClienteDAO dao = new ClienteDAO();
		Cliente cliente = dao.buscarPorId(usu.getId_cliente());
		
		if(cliente != null) {
			cliente.setCpf_cliente(cpf);
			cliente.setEndereco_cliente(endereco);
			cliente.setTelefone_cliente(telegone);
			
			if(nova_senha != null && !nova_senha.isEmpty()) {
				if(senha_atual != null && !senha_atual.equals(usu.getSenha())) {
					usu.setSenha(nova_senha);
				} else {
					request.setAttribute("erro", "Senha incorreta");
					RequestDispatcher rd = request.getRequestDispatcher("/cliente/perfil.jsp");
					rd.forward(request, response);
					return;
				}
			}
			
			boolean sucesso = dao.atualizar(cliente);
			
			if(sucesso) {
				session.setAttribute("clienteLogado", cliente);
				request.setAttribute("sucesso", "Dados atulizados");
			} else {
				request.setAttribute("erro", "Erro ao atualizar dados");
			}
			
			RequestDispatcher rd = request.getRequestDispatcher("/cliente/perfil.jsp");
			rd.forward(request, response);
		}
	}
}
