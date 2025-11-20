package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import util.ConnectionFactory;
import java.sql.Connection;

@WebServlet("/test-connection")
public class TestConnectionServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
			
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try (Connection conn = ConnectionFactory.getConnection()){
			out.println("<h2 style='color:green'>Conexão realizada com sucesso!</h2>");
		} catch (Exception e) {
			out.println("<h2 style='color:red'>Erro ao conectar: " + e.getMessage() + "</h2>");
            e.printStackTrace(out);
		}
	}
}