<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
    if(usuario == null) {
        response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Complete seu Cadastro - Loja Java</title>
<link href="${pageContext.request.contextPath}/Styles/styles.css" rel="stylesheet">
</head>
<body>

<div class="login-container">
    <h2>Complete seu Cadastro</h2>
    <p style="text-align: center; color: #666; margin-bottom: 20px;">
        Para continuar, precisamos de mais algumas informações
    </p>
    
    <% String erro = (String) request.getAttribute("erro"); %>
    <% if (erro != null) { %>
        <p style="color: #c62828; text-align:center; font-weight:bold;"><%= erro %></p>
    <% } %>
    
    <form action="${pageContext.request.contextPath}/cliente" method="post">
        <input type="hidden" name="action" value="completar_cadastro">
        
        <div class="form-group">
            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome" placeholder="Digite seu nome" required style="background-color: #f5f5f5;">
        </div>
        
        <div class="form-group">
            <label for="nome">CPF:</label>
            <input type="text" id="cpf" name="cpf" placeholder="Ex: 000.000.000-00" required style="background-color: #f5f5f5;">
        </div>
        
        <div class="form-group">
            <label for="nome">Telêfone:</label>
            <input type="text" id="telefone" name="telefone" placeholder="Ex: (11)11111-1111" required style="background-color: #f5f5f5;">
        </div>
        
        <div class="form-group">
            <label for="email">Endreço:</label>
            <input type="text" id="endreco" name="endereco" placeholder="Ex: Rua ABC 123 ou 10000-000" style="background-color: #f5f5f5;">
            <small style="color: #666;">Você pode utilizar o Seu Endereço ou CEP</small>
        </div>
        
        <button type="submit" class="btn-login">Salvar e Continuar</button>
    </form>
</div>

</body>
</html>