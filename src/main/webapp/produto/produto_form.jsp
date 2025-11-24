<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Usuario" %>
<%@ page import="model.Produto" %>
<%@ page import="model.Categoria" %>
<%@ page import="DAO.CategoriaDAO" %>
<%@ page import="java.util.List" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
    if(usuario == null) {
        response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
        return;
    }
    
    Produto produto = (Produto) request.getAttribute("produto");
    // Será toda uma edição sempre que não tiver produto sendo puxado
    boolean isEdicao = (produto != null);
    
    // Buscar categorias para o select
    CategoriaDAO categoriaDAO = new CategoriaDAO();
    List<Categoria> categorias = categoriaDAO.listarTodas();
    
    String erro = (String) request.getAttribute("erro");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= isEdicao ? "Editar Produto" : "Novo Produto" %> - Loja Java</title>
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
            <a href="${pageContext.request.contextPath}/produto?action=listar" class="nav-link active">Produtos</a>
            <a href="${pageContext.request.contextPath}/categoria?action=listar" class="nav-link">Categorias</a>
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
                <h2><%= isEdicao ? "Editar Produto" : "Novo Produto" %></h2>
                <a href="${pageContext.request.contextPath}/produto?action=listar" class="btn-secondary">Voltar</a>
            </div>
            
            <!-- Msg erro -->
            <% if(erro != null){ %>
            	<div class="alert-error">
            		<strong>Erro:</strong> <%= erro %>
            	</div>
            <%} %>
            
            <!-- Form -->
            <div>
            	<form action="${pageContext.request.contextPath}/produto" method="post">
            		<input type="hidden" name="action" value="<%= isEdicao ? "atualizar" : "inserir" %>">
            		<% if(isEdicao){ %>
            			<input type="hidden" name="id" value="<%= produto.getId_produto() %>">
            		<%} %>
            		
            		<div class="form-row">
                        <div class="form-group full-width">
                            <label for="nome">Nome do Produto *</label>
                            <input type="text" 
                                   id="nome" 
                                   name="nome" 
                                   value="<%= isEdicao ? produto.getNome_produto() : "" %>" 
                                   placeholder="Ex: Notebook Dell Inspiron"
                                   required 
                                   maxlength="100">
                        </div>
                    </div>
                    
                    <div class="form-row">
                        <div class="form-group full-width">
                            <label for="descricao">Descrição</label>
                            <textarea id="descricao" 
                                      name="descricao" 
                                      rows="4" 
                                      placeholder="Descrição detalhada do produto..."
                                      maxlength="250"><%= isEdicao && produto.getDescricao_produto() != null ? produto.getDescricao_produto() : "" %></textarea>
                            <small>Máximo 250 caracteres</small>
                        </div>
                    </div>
                    
                    <div class="form-row">
                        <div class="form-group half-width">
                            <label for="preco">Preço (R$) *</label>
                            <input type="number" 
                                   id="preco" 
                                   name="preco" 
                                   value="<%= isEdicao ? produto.getPreco_produto() : "" %>" 
                                   placeholder="0.00"
                                   step="0.01" 
                                   min="0" 
                                   required>
                        </div>
                        
                        <div class="form-group half-width">
                            <label for="estoque">Estoque *</label>
                            <input type="number" 
                                   id="estoque" 
                                   name="estoque" 
                                   value="<%= isEdicao ? produto.getEstoque_produto() : "0" %>" 
                                   placeholder="0"
                                   min="0" 
                                   required>
                        </div>
                    </div>
                    
                    <div class="form-row">
                    	<div class="form-group full-width">
                    		<label for="categoria">Categoria</label>
                    		<select id="categoria" name="categoria">
                    			<option value="">-- Selecione uma categoria--</option>
                    			<% if(categorias != null && !categorias.isEmpty()){ %>
                    				<% for(Categoria cat : categorias){ %>
                    					<option value="<%= cat.getId_categoria() %>"
                    						<%= isEdicao && produto.getId_categoria() != null && produto.getId_categoria() == cat.getId_categoria() ? "selected" : "" %>>
                    						<%= cat.getNome_categoria() %>
                    					</option>
                    				<%} %>
                    			<%}else{ %>
                    				<option value="" disabled>Nenhuma categoria cadastrada</option>
                    			<%} %>	
                    		</select>
                    		<small>Opcional - você pode cadastrar categorias <a href="${pageContext.request.contextPath}/categoria?action=listar">aqui</a></small>
            			</div>
            		</div>
            		
            		<div class="form-actions">
                        <button type="submit" class="btn-primary">
                            <%= isEdicao ? "Salvar Alterações" : "Cadastrar Produto" %>
                        </button>
                    </div>
            	</form>
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