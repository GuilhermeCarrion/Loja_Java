<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Usuario" %>
<%@ page import="model.Cliente" %>
<%@ page import="DAO.ClienteDAO" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
    if(usuario == null || usuario.getId_cliente() == null) {
        response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
        return;
    }
    
    // Buscar dados do cliente
    ClienteDAO clienteDAO = new ClienteDAO();
    Cliente cliente = clienteDAO.buscarPorId(usuario.getId_cliente());
    
    if(cliente == null) {
        response.sendRedirect(request.getContextPath() + "/cliente/completar_cadastro.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Meu Perfil - Loja Java</title>
<link href="${pageContext.request.contextPath}/Styles/styles.css" rel="stylesheet">
<style>
    .profile-header {
        text-align: center;
        margin-bottom: 30px;
    }
    .profile-header h2 {
        color: #c62828;
        margin-bottom: 5px;
    }
    .profile-header p {
        color: #666;
    }
    .btn-secondary {
        width: 100%;
        padding: 12px;
        background-color: #666;
        color: #fff;
        font-size: 16px;
        font-weight: bold;
        border: none;
        border-radius: 6px;
        cursor: pointer;
        margin-top: 10px;
    }
    .btn-secondary:hover {
        background-color: #555;
    }
    .alert-success {
        background-color: #d4edda;
        color: #155724;
        padding: 10px;
        border-radius: 6px;
        margin-bottom: 20px;
        text-align: center;
    }
</style>
</head>
<body>

<div class="login-container" style="width: 450px;">
    <div class="profile-header">
        <h2>Meu Perfil</h2>
        <p>Gerencie suas informações pessoais</p>
    </div>
    
    <% String erro = (String) request.getAttribute("erro"); %>
    <% if (erro != null) { %>
        <p style="color: #c62828; text-align:center; font-weight:bold;"><%= erro %></p>
    <% } %>
    
    <% String sucesso = (String) request.getAttribute("sucesso"); %>
    <% if (sucesso != null) { %>
        <div class="alert-success"><%= sucesso %></div>
    <% } %>
    
    <form action="${pageContext.request.contextPath}/cliente" method="post">
        <input type="hidden" name="action" value="atualizar">
        
        <div class="form-group">
            <label for="nome">CPF: </label>
            <input type="text" id="nome" name="nome" value="<%= cliente.getCpf_cliente() %>" required>
        </div>
        
        <div class="form-group">
            <label for="email">Endereço: </label>
            <input type="email" id="email" name="email" value="<%= cliente.getEndereco_cliente() %>" required>
        </div>
        
        <div class="form-group">
            <label for="email">Telefone: </label>
            <input type="email" id="email" name="email" value="<%= cliente.getTelefone_cliente() %>" required>
        </div>
        
        <hr style="margin: 30px 0; border: none; border-top: 1px solid #ddd;">
        
        <div class="form-group">
            <label for="senha_atual">Senha Atual</label>
            <input type="password" id="senha_atual" name="senha_atual" placeholder="Digite sua senha atual">
        </div>
        
        <div class="form-group">
            <label for="nova_senha">Nova Senha</label>
            <input type="password" id="nova_senha" name="nova_senha" placeholder="Digite a nova senha">
        </div>
        
        <button type="submit" class="btn-login">Salvar Alterações</button>
    </form>
    
    <form action="${pageContext.request.contextPath}/pages/index.jsp" method="get" style="margin-top: 10px;">
        <button type="submit" class="btn-secondary">Voltar para Início</button>
    </form>
    
    <div class="login-footer" style="margin-top: 20px;">
        <p><a href="${pageContext.request.contextPath}/logout">Sair da conta</a></p>
    </div>
</div>

</body>
</html>