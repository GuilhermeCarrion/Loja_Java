<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Usuario" %>
<%@ page import="model.Cliente" %>
<%@ page import="model.Produto" %>
<%@ page import="DAO.ClienteDAO" %>
<%@ page import="DAO.ProdutoDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.NumberFormat" %>
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
    
    // Buscar produtos
    ProdutoDAO produtoDAO = new ProdutoDAO();
    List<Produto> produtos = produtoDAO.listarTodos();
    NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Loja Java - Início</title>
<link href="${pageContext.request.contextPath}/Styles/styles.css" rel="stylesheet">
</head>
<body class="full-page">

<div class="main-container">
    <!-- Header -->
    <div class="header">
        <div class="logo">
            <h1>Loja Java</h1>
        </div>
        
        <!-- Navbar -->
        <nav class="navbar">
            <a href="${pageContext.request.contextPath}/pages/index.jsp" class="nav-link">Home</a>
            <a href="${pageContext.request.contextPath}/produto?action=listar" class="nav-link">Produtos</a>
            <a href="${pageContext.request.contextPath}/categoria?action=listar" class="nav-link">Categorias</a>
            <a href="${pageContext.request.contextPath}/cliente/perfil.jsp" class="nav-link">Perfil</a>
            <a href="${pageContext.request.contextPath}/pedido?action=listar" class="nav-link">Pedidos</a>
        </nav>
        
        <div class="user-info">
            <% if(cliente != null) { %>
                <p class="user-name">Olá, <strong><%= cliente.getNome_cliente() %></strong></p>
                <p class="user-email"><%= usuario.getEmail() %></p>
            <% } else { %>
                <p class="user-name"><strong>Usuário:</strong> <%= usuario.getEmail() %></p>
            <% } %>
        </div>
    </div>
    
    <!-- Main content -->
    <div class="content">
        <div class="welcome-section">
            <div class="welcome-box">
                <h2>Bem-vindo à Loja Java!</h2>
                <p>Explore nossos produtos e aproveite as melhores ofertas</p>
            </div>
        </div>
        
        <!-- Produtos - CREATE(pedido + item_pedidos) -->
        <!-- PRODUTOS EM DESTAQUE -->
        <div class="products-showcase">
            <h3>Produtos Disponíveis</h3>
            
            <% if(produtos != null && !produtos.isEmpty()) { %>
                <div class="products-grid">
                    <% for(Produto p : produtos) { %>
                        <div class="product-card">
                            <!-- CATEGORIA -->
                            <div class="product-category">
                                <%= p.getNome_categoria() != null ? p.getNome_categoria() : "Sem Categoria" %>
                            </div>
                            
                            <!-- CORPO DO CARD -->
                            <div class="product-body">
                                <div class="product-name"><%= p.getNome_produto() %></div>
                                
                                <div class="product-description">
                                    <%= p.getDescricao_produto() != null ? p.getDescricao_produto() : "Produto de qualidade" %>
                                </div>
                                
                                <div class="product-price">
                                    <%= formatoMoeda.format(p.getPreco_produto()) %>
                                </div>
                                
                                <div class="product-stock <%= p.getEstoque_produto() > 0 ? "in-stock" : "out-of-stock" %>">
                                    <% if(p.getEstoque_produto() > 0) { %>
                                        <%= p.getEstoque_produto() %> unidades em estoque
                                    <% } else { %>
                                        Produto esgotado
                                    <% } %>
                                </div>
                                
                                <!-- AÇÕES -->
                                <% if(p.getEstoque_produto() > 0) { %>
                                    <form action="${pageContext.request.contextPath}/pedido" method="post" class="product-actions">
                                        <input type="hidden" name="action" value="adicionar_carrinho">
                                        <input type="hidden" name="id_produto" value="<%= p.getId_produto() %>">
                                        
                                        <input type="number" 
                                               name="quantidade" 
                                               value="1" 
                                               min="1" 
                                               max="<%= p.getEstoque_produto() %>" 
                                               class="quantity-input"
                                               required>
                                        
                                        <button type="submit" class="btn-buy">
                                            Comprar
                                        </button>
                                    </form>
                                <% } else { %>
                                    <button class="btn-buy" disabled>
                                        Indisponível
                                    </button>
                                <% } %>
                            </div>
                        </div>
                    <% } %>
                </div>
            <% } else { %>
                <div class="empty-state">
                    <p>Nenhum produto disponível no momento.</p>
                    <p><a href="${pageContext.request.contextPath}/produto?action=novo">Cadastre o primeiro produto</a></p>
                </div>
            <% } %>
        </div>
    </div>
    
    <!-- FOOTER -->
    <div class="footer">
        <p>&copy; 2024 Loja Java - Todos os direitos reservados</p>
    </div>
</div>


</body>
</html>