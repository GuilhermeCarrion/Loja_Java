package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import DAO.UsuarioDAO;
import model.Usuario;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
		
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		Usuario usuario = usuarioDAO.autenticar(email, senha);
		
		if(usuario != null) {
			// Criando sessão apos login
			
			HttpSession session = request.getSession();
			session.setAttribute("usuarioLogado", usuario);
			
			// Verificando se ja completou cadastro (clientes table)
			if(usuario.getId_cliente() == null) {
				response.sendRedirect(request.getContextPath() + "/cliente/comp_cad.jsp");
			}else {
				response.sendRedirect(request.getContextPath() + "/pages/index.jsp");
			}
			
		}else {
			// Erro no login
			
			request.setAttribute("erro", "Email ou senha incorretos");
			RequestDispatcher rd = request.getRequestDispatcher(request.getContextPath() + "/pages/login.jsp");
			rd.forward(request, response);
		}
	}
}
