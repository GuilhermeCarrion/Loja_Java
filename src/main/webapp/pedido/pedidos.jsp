<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Usuario" %>
<%@ page import="model.Pedido" %>
<%@ page import="model.Cliente" %>
<%@ page import="DAO.ClienteDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.Locale" %>
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
    
    List<Pedido> pedidos = (List<Pedido>) request.getAttribute("pedidos");
    NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pedidos - Loja Java</title>
<link href="${pageContext.request.contextPath}/Styles/styles.css?v=<%= System.currentTimeMillis() %>" rel="stylesheet">
</head>
<body class="full-page">

<div class="main-container">
    <!-- HEADER -->
    <div class="header">
        <div class="logo">
            <h1>Loja Java</h1>
        </div>
        
        <nav class="navbar">
            <a href="${pageContext.request.contextPath}/pages/index.jsp" class="nav-link">Home</a>
            <a href="${pageContext.request.contextPath}/produto?action=listar" class="nav-link">Produtos</a>
            <a href="${pageContext.request.contextPath}/categoria?action=listar" class="nav-link">Categorias</a>
            <a href="${pageContext.request.contextPath}/cliente/perfil.jsp" class="nav-link">Perfil</a>
            <a href="${pageContext.request.contextPath}/pedido?action=listar" class="nav-link active">Pedidos</a>
        </nav>
        
        <div class="user-info">
            <p class="user-name">Olá, <strong><%= cliente.getNome_cliente() %></strong></p>
            <p class="user-email"><%= usuario.getEmail() %></p>
        </div>
    </div>
    
    <!-- CONTEÚDO -->
    <div class="content">
        <div class="page-header">
            <h2>Gerenciar Pedidos</h2>
            <a href="${pageContext.request.contextPath}/pedido?action=carrinho" class="btn-primary">🛒 - Carrinho</a>
        </div>
        
        <% if("true".equals(request.getParameter("sucesso"))) { %>
            <div class="alert-success">
                <strong>Pedido realizado com sucesso!</strong> Seu pedido foi registrado e está sendo processado.
            </div>
        <% } %>
        
        <!-- TABELA DE PEDIDOS -->
        <div class="table-container">
            <table class="data-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Cliente</th>
                        <th>Data</th>
                        <th class="text-right">Total</th>
                        <th class="text-center">Status</th>
                        <th class="text-center">Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <% if(pedidos != null && !pedidos.isEmpty()) { %>
                        <% for(Pedido p : pedidos) { %>
                            <tr>
                                <td><strong>#<%= p.getId_pedido() %></strong></td>
                                <td><%= p.getNome_cliente() %></td>
                                <td><%= p.getData_pedido().format(formatoData) %></td>
                                <td class="text-right"><strong><%= formatoMoeda.format(p.getTotal_pedido()) %></strong></td>
                                <td class="text-center">
                                    <span class="badge <%= getStatusClass(p.getStatus_pedido()) %>">
                                        <%= p.getStatus_pedido() %>
                                    </span>
                                </td>
                                <td class="actions">
                                    <a href="${pageContext.request.contextPath}/pedido?action=detalhes&id=<%= p.getId_pedido() %>" 
                                       class="btn-view" 
                                       title="Ver Detalhes">&#128065;</a>
                                    
                                    <a href="${pageContext.request.contextPath}/pedido?action=excluir&id=<%= p.getId_pedido() %>" 
                                       class="btn-delete" 
                                       title="Excluir"
                                       onclick="return confirm('Tem certeza que deseja excluir este pedido?')">&#x1F5D1;</a>
                                </td>
                            </tr>
                        <% } %>
                    <% } else { %>
                        <tr>
                            <td colspan="6" class="empty-message">
                                Nenhum pedido realizado ainda. <a href="${pageContext.request.contextPath}/pages/index.jsp">Comece a comprar!</a>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
    
    <!-- FOOTER -->
    <div class="footer">
        <p>&copy; 2024 Loja Java - Todos os direitos reservados</p>
    </div>
</div>

<%!
	private String getStatusClass(String status) {
	    if(status == null || status.trim().isEmpty()) {
	        status = "Em andamento";
	    }
	    
	    switch(status) {
	        case "Em andamento":
	            return "badge-warning";
	        case "Confirmado":
	            return "badge-info";
	        case "Enviado":
	            return "badge-primary";
	        case "Entregue":
	            return "badge-success";
	        case "Cancelado":
	            return "badge-danger";
	        default:
	            return "badge-secondary";
	    }
	}
%>

</body>
</html>