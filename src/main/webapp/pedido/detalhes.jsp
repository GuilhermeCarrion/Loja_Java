<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Usuario" %>
<%@ page import="model.Pedido" %>
<%@ page import="model.ItemPedido" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.Locale" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
    if(usuario == null) {
        response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
        return;
    }
    
    Pedido pedido = (Pedido) request.getAttribute("pedido");
    NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    boolean sucesso = "true".equals(request.getParameter("sucesso"));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detalhes do Pedido #<%= pedido.getId_pedido() %> - Loja Java</title>
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
            <p class="user-name">Olá, <strong>Usuário</strong></p>
            <p class="user-email"><%= usuario.getEmail() %></p>
        </div>
    </div>
    
    <!-- CONTEÚDO -->
    <div class="content">
        <div class="page-header">
            <h2>Pedido #<%= pedido.getId_pedido() %></h2>
            <a href="${pageContext.request.contextPath}/pedido?action=listar" class="btn-secondary">Voltar</a>
        </div>
        
        <!-- MENSAGEM DE SUCESSO -->
        <% if(sucesso) { %>
            <div class="alert-success">
                <strong>✅ Pedido realizado com sucesso!</strong> Seu pedido foi registrado e está sendo processado.
            </div>
        <% } %>
        
        <!-- INFORMAÇÕES DO PEDIDO -->
        <div class="pedido-info-container">
            <div class="pedido-info-card">
                <h3>Informações do Pedido</h3>
                <div class="info-row">
                    <span class="info-label">ID do Pedido:</span>
                    <span class="info-value"><strong>#<%= pedido.getId_pedido() %></strong></span>
                </div>
                <div class="info-row">
                    <span class="info-label">Cliente:</span>
                    <span class="info-value"><%= pedido.getNome_cliente() %></span>
                </div>
                <div class="info-row">
                    <span class="info-label">Data:</span>
                    <span class="info-value"><%= pedido.getData_pedido().format(formatoData) %></span>
                </div>
                <div class="info-row">
                    <span class="info-label">Status:</span>
                    <span class="info-value">
                        <span class="badge <%= getStatusClass(pedido.getStatus_pedido()) %>">
                            <%= pedido.getStatus_pedido() %>
                        </span>
                    </span>
                </div>
                <div class="info-row">
                    <span class="info-label">Total:</span>
                    <span class="info-value pedido-total"><%= formatoMoeda.format(pedido.getTotal_pedido()) %></span>
                </div>
            </div>
            
            <!-- ATUALIZAR STATUS -->
            <div class="pedido-status-card">
                <h3>Atualizar Status</h3>
                <form action="${pageContext.request.contextPath}/pedido" method="post">
                    <input type="hidden" name="action" value="atualizar_status">
                    <input type="hidden" name="id" value="<%= pedido.getId_pedido() %>">
                    
                    <select name="status" class="status-select">
                        <option value="Em andamento" <%= "Em andamento".equals(pedido.getStatus_pedido()) ? "selected" : "" %>>Em andamento</option>
                        <option value="Confirmado" <%= "Confirmado".equals(pedido.getStatus_pedido()) ? "selected" : "" %>>Confirmado</option>
                        <option value="Enviado" <%= "Enviado".equals(pedido.getStatus_pedido()) ? "selected" : "" %>>Enviado</option>
                        <option value="Entregue" <%= "Entregue".equals(pedido.getStatus_pedido()) ? "selected" : "" %>>Entregue</option>
                        <option value="Cancelado" <%= "Cancelado".equals(pedido.getStatus_pedido()) ? "selected" : "" %>>Cancelado</option>
                    </select>
                    
                    <button type="submit" class="btn-primary btn-small">Atualizar</button>
                </form>
            </div>
        </div>
        
        <!-- ITENS DO PEDIDO -->
        <div class="pedido-items-container">
            <h3>Itens do Pedido</h3>
            <table class="data-table">
                <thead>
                    <tr>
                        <th>Produto</th>
                        <th class="text-center">Quantidade</th>
                        <th class="text-right">Preço Unitário</th>
                        <th class="text-right">Subtotal</th>
                    </tr>
                </thead>
                <tbody>
                    <% for(ItemPedido item : pedido.getItens()) { %>
                        <tr>
                            <td><strong><%= item.getNome_produto() %></strong></td>
                            <td class="text-center"><%= item.getQuantidade_item() %></td>
                            <td class="text-right"><%= formatoMoeda.format(item.getPreco_unitario()) %></td>
                            <td class="text-right"><strong><%= formatoMoeda.format(item.getSubtotal()) %></strong></td>
                        </tr>
                    <% } %>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="3" class="text-right"><strong>TOTAL DO PEDIDO:</strong></td>
                        <td class="text-right cart-total"><strong><%= formatoMoeda.format(pedido.getTotal_pedido()) %></strong></td>
                    </tr>
                </tfoot>
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