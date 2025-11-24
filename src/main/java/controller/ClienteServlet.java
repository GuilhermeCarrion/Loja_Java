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
			
			//System.out.println("Action recebida: [" + action + "]");
			
			if("completar_cadastro".equals(action)) {
				completarCadastro(request, response);
			} else if("atualizar".equals(action)) {
				atualizarDados(request, response);
			} else {
				//System.out.println("Action invalida");
				
				response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
			}
		}
	
	// Cadastrar apos logar
	private void completarCadastro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Usuario usu = (Usuario) session.getAttribute("usuarioLogado");
		
		//System.out.println("Usuario da sessão: " + usu);
		
		if(usu == null) {
			response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
			return;
		}
		 // Dados FOrm
		String nome = request.getParameter("nome");
		String cpf = request.getParameter("cpf");
		String endereco = request.getParameter("endereco");
		String telefone = request.getParameter("telefone");
		
		//System.out.println("CPF: " + cpf);
	    //System.out.println("Endereco: " + endereco);
	    //System.out.println("Telefone: " + telefone);
		
		// New cliente
		Cliente cliente = new Cliente();
		cliente.setNome_cliente(nome);
		cliente.setCpf_cliente(cpf);
		cliente.setEndereco_cliente(endereco);
		cliente.setTelefone_cliente(telefone);
		
		//System.out.println("Cliente criado: " + cliente);
		
		ClienteDAO dao = new ClienteDAO();
		int idCliente = dao.inserir(cliente);
		
		//System.out.println("ID retornado: " + idCliente);
		
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
			//System.out.println("Erro! ID = 0");

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
		
		String nome = request.getParameter("nome");
		String cpf = request.getParameter("cpf");
		String endereco = request.getParameter("endereco");
		String telefone = request.getParameter("telefone");
		String senha_atual = request.getParameter("senha_atual");
		String nova_senha = request.getParameter("nova_senha");
		
		ClienteDAO dao = new ClienteDAO();
		Cliente cliente = dao.buscarPorId(usu.getId_cliente());
		
		if(cliente != null) {
			cliente.setNome_cliente(nome);
			cliente.setCpf_cliente(cpf);
			cliente.setEndereco_cliente(endereco);
			cliente.setTelefone_cliente(telefone);
			
			boolean sucessoCliente = dao.atualizar(cliente);
			// Padrão true para caso não seja alterado
			boolean sucessoSenha = true;
			
			//Caso altere a senha
			if(nova_senha != null && !nova_senha.isEmpty()) {
				if(senha_atual != null && senha_atual.equals(usu.getSenha())) {
					UsuarioDAO usuDAO = new UsuarioDAO();
					sucessoSenha = usuDAO.atualizarSenha(usu.getId(), nova_senha);
					
					if(sucessoSenha) {
						usu.setSenha(nova_senha);
						session.setAttribute("usuarioLogado", usu);
						System.out.println("Senha alterada");
					}
				} else {
					//System.out.println("Senha incorreta: " + senha_atual + "\nSenha nova: " + nova_senha);
					request.setAttribute("erro", "Senha incorreta");
					RequestDispatcher rd = request.getRequestDispatcher("/cliente/perfil.jsp");
					rd.forward(request, response);
					return;
				}
			}
			
			if(sucessoCliente && sucessoSenha) {
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
