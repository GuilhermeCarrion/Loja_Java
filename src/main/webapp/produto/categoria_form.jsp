<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Usuario" %>
<%@ page import="model.Categoria" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
    if(usuario == null) {
        response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
        return;
    }
    
    Categoria categoria = (Categoria) request.getAttribute("categoria");
    boolean isEdicao = (categoria != null);
    String erro = (String) request.getAttribute("erro");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= isEdicao ? "Editar Categoria" : "Nova Categoria" %> - Loja Java</title>
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
            <a href="${pageContext.request.contextPath}/categoria?action=listar" class="nav-link active">Categorias</a>
            <a href="${pageContext.request.contextPath}/cliente/perfil.jsp" class="nav-link">Perfil</a>
            <a href="${pageContext.request.contextPath}/pedido?action=listar" class="nav-link">Pedidos</a>
        </nav>
        
        <div class="user-info">
            <p class="user-name">Olá, <strong>Usuário</strong></p>
            <p class="user-email"><%= usuario.getEmail() %></p>
        </div>
    </div>
    
    <!-- CONTEÚDO -->
    <div class="content">
        <div class="form-page">
            <div class="form-header">
                <h2><%= isEdicao ? "Editar Categoria" : "Nova Categoria" %></h2>
                <a href="${pageContext.request.contextPath}/categoria?action=listar" class="btn-secondary">Voltar</a>
            </div>
            
            <!-- MENSAGEM DE ERRO -->
            <% if(erro != null) { %>
                <div class="alert-error">
                    <strong>Erro:</strong> <%= erro %>
                </div>
            <% } %>
            
            <!-- FORMULÁRIO -->
            <div class="form-container">
                <form action="${pageContext.request.contextPath}/categoria" method="post">
                    <input type="hidden" name="action" value="<%= isEdicao ? "atualizar" : "inserir" %>">
                    <% if(isEdicao) { %>
                        <input type="hidden" name="id" value="<%= categoria.getId_categoria() %>">
                    <% } %>
                    
                    <div class="form-row">
                        <div class="form-group full-width">
                            <label for="nome">Nome da Categoria *</label>
                            <input type="text" 
                                   id="nome" 
                                   name="nome" 
                                   value="<%= isEdicao ? categoria.getNome_categoria() : "" %>" 
                                   placeholder="Ex: Eletrônicos, Livros, Roupas..."
                                   required 
                                   maxlength="100"
                                   autofocus>
                            <small>Nome que identificará esta categoria (máximo 100 caracteres)</small>
                        </div>
                    </div>
                    
                    <div class="form-actions">
                        <button type="submit" class="btn-primary">
                            <%= isEdicao ? "Salvar Alterações" : "Cadastrar Categoria" %>
                        </button>
                        <a href="${pageContext.request.contextPath}/categoria?action=listar" class="btn-cancel">Cancelar</a>
                    </div>
                </form>
            </div>
            
            <!-- DICA -->
            <div class="info-box" style="margin-top: 20px;">
                <p><strong>Dica:</strong> Categorias ajudam a organizar seus produtos. Você pode vincular produtos às categorias na página de cadastro de produtos.</p>
            </div>
        </div>
    </div>
    
    <!-- FOOTER -->
    <div class="footer">
        <p>&copy; 2024 Loja Java - Todos os direitos reservados</p>
    </div>
</div>

</body>
</html>