<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Usuario" %>
<%@ page import="model.Categoria" %>
<%@ page import="DAO.CategoriaDAO" %>
<%@ page import="java.util.List" %>
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
    
    List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
    String erro = (String) request.getAttribute("erro");
    
    // Para contar produtos por categoria
    CategoriaDAO categoriaDAO = new CategoriaDAO();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Categorias - Loja Java</title>
<link href="${pageContext.request.contextPath}/Styles/styles.css?v=<%= System.currentTimeMillis() %>" rel="stylesheet">
</head>
<body class="full-page">

<div class="main-container">
    <!-- HEADER COM NAVBAR -->
    <div class="header">
        <div class="logo">
            <h1>Loja Java</h1>
        </div>
        
        <nav class="navbar">
            <a href="${pageContext.request.contextPath}/pages/index.jsp" class="nav-link">Home</a>
            <a href="${pageContext.request.contextPath}/produto?action=listar" class="nav-link">Produtos</a>
            <a href="${pageContext.request.contextPath}/categoria?action=listar" class="nav-link active">Categorias</a>
            <a href="${pageContext.request.contextPath}/cliente/perfil.jsp" class="nav-link">Perfil</a>
            <a href="${pageContext.request.contextPath}/pedido?action=listar" class="nav-link">Pedidos</a>
        </nav>
        
        <div class="user-info">
            <p class="user-name">Olá, <strong><%= cliente.getNome_cliente() %></strong></p>
            <p class="user-email"><%= usuario.getEmail() %></p>
        </div>
    </div>
    
    <!-- CONTEÚDO -->
    <div class="content">
        <div class="page-header">
            <h2>Gerenciar Categorias</h2>
            <a href="${pageContext.request.contextPath}/categoria?action=novo" class="btn-primary">Nova Categoria</a>
        </div>
        
        <!-- MENSAGEM DE ERRO -->
        <% if(erro != null) { %>
            <div class="alert-error">
                <strong>Atenção:</strong> <%= erro %>
            </div>
        <% } %>
        
        <!-- TABELA DE CATEGORIAS -->
        <div class="table-container">
            <table class="data-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome da Categoria</th>
                        <th class="text-center">Produtos Vinculados</th>
                        <th class="text-center">Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <% if(categorias != null && !categorias.isEmpty()) { %>
                        <% for(Categoria cat : categorias) { %>
                            <% int totalProdutos = categoriaDAO.contadorProdutos(cat.getId_categoria()); %>
                            <tr>
                                <td><%= cat.getId_categoria() %></td>
                                <td><strong><%= cat.getNome_categoria() %></strong></td>
                                <td class="text-center">
                                    <span class="badge <%= totalProdutos > 0 ? "badge-info" : "badge-secondary" %>">
                                        <%= totalProdutos %>
                                    </span>
                                </td>
                                <td class="actions">
                                    <a href="${pageContext.request.contextPath}/categoria?action=editar&id=<%= cat.getId_categoria() %>" 
                                       class="btn-edit" 
                                       title="Editar">&#9999;&#65039;</a>
                                    
                                    <% if(totalProdutos == 0) { %>
                                        <a href="${pageContext.request.contextPath}/categoria?action=excluir&id=<%= cat.getId_categoria() %>" 
                                           class="btn-delete" 
                                           title="Excluir"
                                           onclick="return confirm('Tem certeza que deseja excluir esta categoria?')">&#128465;</a>
                                    <% } else { %>
                                        <span class="btn-disabled" title="Não pode excluir: possui produtos vinculados">&#128273;</span>
                                    <% } %>
                                </td>
                            </tr>
                        <% } %>
                    <% } else { %>
                        <tr>
                            <td colspan="4" class="empty-message">
                                Nenhuma categoria cadastrada. <a href="${pageContext.request.contextPath}/categoria?action=novo">Clique aqui para adicionar a primeira</a>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
        
        <!-- INFO SOBRE EXCLUSÃO -->
        <div class="info-box">
            <p><strong>Informação:</strong> Categorias com produtos vinculados não podem ser excluídas. Remova ou altere a categoria dos produtos primeiro.</p>
        </div>
    </div>
    
    <!-- FOOTER -->
    <div class="footer">
        <p>&copy; 2024 Loja Java - Todos os direitos reservados</p>
    </div>
</div>

</body>
</html>