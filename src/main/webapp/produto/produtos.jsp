<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Usuario" %>
<%@ page import="model.Produto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
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
	
	List<Produto> produtos = (List<Produto>) request.getAttribute("produtos");
	// Formato moeda REAL
	NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Produtos - Loja Java</title>
<link href="${pageContext.request.contextPath}/Styles/styles.css?v=<%= System.currentTimeMillis() %>" rel="stylesheet">
</head>
<body class="full-page">

<div class="main-container">
    <!-- HEADER + NAVBAR -->
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
            <p class="user-name">Olá, <strong><%= cliente.getNome_cliente() %></strong></p>
            <p class="user-email"><%= usuario.getEmail() %></p>
        </div>
    </div>
    
    <!-- Content -->
    <div class="content">
    	<div class="page-header">
    		<h2>Gerenciar Produtos</h2>
    		<a href="${pageContext.request.contextPath}/produto?action=novo" class="btn-primary">Novo Produto</a>
    	</div>
    	
    	<!-- Tabale de produtos -->
    	<div class="table-container">
    		<table class="data-table">
    			<thead>
    				<tr>
    					<th>ID</th>
    					<th>Categoria</th>
    					<th>Nome</th>
    					<th>Descrição</th>
    					<th>Preço</th>
    					<th>Estoque</th>
    					<th>Ações</th>
    				</tr>
    			</thead>
    			<tbody>
    				<% if(produtos != null && !produtos.isEmpty()){%>
    					<% for(Produto p : produtos){ %>
    						<tr>
    							<td><%= p.getId_produto() %></td>
    							<!-- Caso produto esteja sem categoria -->
    							<td><%= p.getNome_categoria() != null ? p.getNome_categoria() : "<em>Sem Categoria</em>" %></td>
    							<td><strong><%= p.getNome_produto() %></strong></td>
    							<td><%= p.getDescricao_produto() != null ? p.getDescricao_produto() : "-" %></td>
    							<td class="text-right"><%= formatoMoeda.format(p.getPreco_produto()) %></td>
    							<td class="text-center">
    								<span class="badge <%= p.getEstoque_produto() > 0 ? "badge-success" : "bagde-danger" %>">
    									<%= p.getEstoque_produto() %>
    								</span>
    							</td>
    							<td class="actions">
    								<a href="${pageContext.request.contextPath}/produto?action=editar&id=<%= p.getId_produto() %>" class="btn-edit" tittle="editar">&#9999;&#65039;</a>
    								<a href="${pageContext.request.contextPath}/produto?action=excluir&id=<%= p.getId_produto() %>" class="btn-delete" tittle="excluir"
    									onclick="return confirm('Tem certeza que deseja excluir este produto?')">&#128465;</a>
    							</td>
    						</tr>
    					<%} %>
    				<%}else{ %>
    					<tr>
    						<td colspan="7" class="empty-message">
    							Nenhum produto cadastrado. <a href="${pageContext.request.contextPath}/produto?action=novo">Clique aqui para adicionar</a>
    						</td>
    					</tr>
    				<%} %>
    				
    			</tbody>	
    		</table>
    	</div>
    </div>
    
    <!-- FOOTER -->
    <div class="footer">
        <p>&copy; 2024 Loja Java - Todos os direitos reservados</p>
    </div>
</div>

</body>
</html>