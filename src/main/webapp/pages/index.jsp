<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Usuario" %>
<%@ page import="model.Cliente" %>
<%@ page import="DAO.ClienteDAO" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
    if(usuario == null) {
        response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
        return;
    }
    
    Cliente cliente = null;
    if(usuario.getId_cliente() != null) {
        ClienteDAO clienteDAO = new ClienteDAO();
        cliente = clienteDAO.buscarPorId(usuario.getId_cliente());
    }
    
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Loja Java - Início</title>
<link href="${pageContext.request.contextPath}/Styles/styles.css" rel="stylesheet">
<style>
    body {
        background: #f5f5f5;
        padding: 20px;
    }
    .container {
        max-width: 1200px;
        margin: 0 auto;
        background: white;
        padding: 30px;
        border-radius: 12px;
        box-shadow: 0 0 15px rgba(0,0,0,0.1);
    }
    .header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        border-bottom: 2px solid #c62828;
        padding-bottom: 20px;
        margin-bottom: 30px;
    }
    .header h1 {
        color: #c62828;
        margin: 0;
    }
    .user-info {
        text-align: right;
    }
    .user-info p {
        margin: 5px 0;
        color: #666;
    }
    .user-info a {
        color: #c62828;
        text-decoration: none;
        margin: 0 10px;
    }
    .user-info a:hover {
        text-decoration: underline;
    }
    .welcome-box {
        background: linear-gradient(135deg, #c62828 0%, #e53935 100%);
        color: white;
        padding: 40px;
        border-radius: 12px;
        text-align: center;
        margin-bottom: 30px;
    }
    .welcome-box h2 {
        margin: 0 0 10px 0;
        font-size: 32px;
    }
    .welcome-box p {
        font-size: 18px;
        opacity: 0.9;
    }
</style>
</head>
<body>

<div class="container">
    <div class="header">
        <h1>Loja Java</h1>
        <div class="user-info">
            <% if(cliente != null) { %>
                <p><strong>Bem-vindo, <%= cliente.getCpf_cliente() %>!</strong></p>
                <div style="margin-top: 10px;">
                	<a href="${pageContext.request.contextPath}/cliente/perfil.jsp">Meu Perfil</a>
                	<a href="${pageContext.request.contextPath}/logout">Sair</a>
           		</div>
            <% } else { %>
                <p><strong>Usuário:</strong> <%= usuario.getEmail() %></p>
                <div style="margin-top: 10px;">
                	<a href="${pageContext.request.contextPath}/cliente/comp_cad.jsp">Meu Perfil</a>
                	<a href="${pageContext.request.contextPath}/logout">Sair</a>
            	</div>
                
            <% } %>
            
        </div>
    </div>
    
    <div class="welcome-box">
        <h2>Bem-vindo à Loja Java!</h2>
        <p>Explore nossos produtos e aproveite as melhores ofertas</p>
    </div>
    
    <div style="padding: 20px; text-align: center;">
        <h3 style="color: #333;">Sistema em Desenvolvimento</h3>
        <p style="color: #666;">Em breve: Catálogo de Produtos, Carrinho de Compras e muito mais!</p>
    </div>
</div>

</body>
</html>