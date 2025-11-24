<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Usuario" %>
<%@ page import="model.ItemPedido" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.math.BigDecimal" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
    if(usuario == null) {
        response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
        return;
    }
    
    Collection<ItemPedido> carrinho = (Collection<ItemPedido>) request.getAttribute("carrinho");
    NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    
    BigDecimal total = BigDecimal.ZERO;
    if(carrinho != null) {
        for(ItemPedido item : carrinho) {
            total = total.add(item.getSubtotal());
        }
    }
    
    String erro = request.getParameter("erro");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Carrinho de Compras - Loja Java</title>
<link href="${pageContext.request.contextPath}/Styles/styles.css?v=<%= System.currentTimeMillis() %>" rel="stylesheet">
</head>
<body class="full-page">

<div class="main-container">
    <!-- HEADER -->
    <div class="header">
        <div class="logo">
            <h1>🛒 Loja Java</h1>
        </div>
        
        <nav class="navbar">
            <a href="${pageContext.request.contextPath}/pages/index.jsp" class="nav-link">Home</a>
            <a href="${pageContext.request.contextPath}/produto?action=listar" class="nav-link">Produtos</a>
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
        <div class="page-header">
            <h2>Meu Carrinho</h2>
            <a href="${pageContext.request.contextPath}/pages/index.jsp" class="btn-secondary">Continuar Comprando</a>
        </div>
        
        <!-- MENSAGEM DE ERRO -->
        <% if(erro != null) { %>
            <div class="alert-error">
                <% if("carrinho_vazio".equals(erro)) { %>
                    <strong>Atenção:</strong> Seu carrinho está vazio!
                <% } else if("erro_ao_finalizar".equals(erro)) { %>
                    <strong>Erro:</strong> Não foi possível finalizar o pedido. Tente novamente.
                <% } %>
            </div>
        <% } %>
        
        <!-- CARRINHO -->
        <% if(carrinho != null && !carrinho.isEmpty()) { %>
            <div class="cart-container">
                <div class="cart-items">
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th>Produto</th>
                                <th class="text-center">Quantidade</th>
                                <th class="text-right">Preço Unitário</th>
                                <th class="text-right">Subtotal</th>
                                <th class="text-center">Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for(ItemPedido item : carrinho) { %>
                                <tr>
                                    <td><strong><%= item.getNome_produto() %></strong></td>
                                    <td class="text-center"><%= item.getQuantidade_item() %></td>
                                    <td class="text-right"><%= formatoMoeda.format(item.getPreco_unitario()) %></td>
                                    <td class="text-right"><strong><%= formatoMoeda.format(item.getSubtotal()) %></strong></td>
                                    <td class="actions">
                                        <a href="${pageContext.request.contextPath}/pedido?action=remover_item&id_produto=<%= item.getId_produto() %>" 
                                           class="btn-delete" 
                                           title="Remover"
                                           onclick="return confirm('Remover este item do carrinho?')">🗑️</a>
                                    </td>
                                </tr>
                            <% } %>
                        </tbody>
                        <tfoot>
                            <tr>
                                <td colspan="3" class="text-right"><strong>TOTAL:</strong></td>
                                <td class="text-right cart-total"><strong><%= formatoMoeda.format(total) %></strong></td>
                                <td></td>
                            </tr>
                        </tfoot>
                    </table>
                </div>
                
                <!-- AÇÕES -->
                <div class="cart-actions">
                    <form action="${pageContext.request.contextPath}/pedido" method="post" style="display: inline;">
                        <input type="hidden" name="action" value="finalizar_pedido">
                        <button type="submit" class="btn-primary btn-large">Finalizar Pedido</button>
                    </form>
                    
                    <a href="${pageContext.request.contextPath}/pages/index.jsp" class="btn-secondary btn-large">Adicionar Mais Produtos</a>
                    
                    <a href="${pageContext.request.contextPath}/pedido?action=limpar_carrinho" 
                       class="btn-danger btn-large"
                       onclick="return confirm('Deseja limpar todo o carrinho?')">Limpar Carrinho</a>
                </div>
            </div>
        <% } else { %>
            <div class="empty-cart">
                <div class="empty-cart-icon">🛒</div>
                <h3>Seu carrinho está vazio</h3>
                <p>Adicione produtos ao carrinho para começar suas compras!</p>
                <a href="${pageContext.request.contextPath}/pages/index.jsp" class="btn-primary">Ver Produtos</a>
            </div>
        <% } %>
    </div>
    
    <!-- FOOTER -->
    <div class="footer">
        <p>&copy; 2024 Loja Java - Todos os direitos reservados</p>
    </div>
</div>

</body>
</html>