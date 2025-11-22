package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import DAO.UsuarioDAO;
import model.Usuario;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet{
	
	 @Override
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	    		throws ServletException, IOException {
		 
		 String email = request.getParameter("email");
		 String senha = request.getParameter("senha");
		 
		 UsuarioDAO dao = new UsuarioDAO();
		 
		 Usuario u = new Usuario();
		 u.setEmail(email);
		 u.setSenha(senha);
		 u.setId_cliente(null);
		 
		 dao.inserir(u);
		 
		 response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
	 }
}
