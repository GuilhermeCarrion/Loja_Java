<%@ page contentType="text/html; charset=UTF-8" %>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bem Vindo ! - Loja Java</title>
<link href="../Styles/styles.css" rel="stylesheet">
</head>
<body>

	<div class="login-container">
        <h2>Entrar</h2>
        
        <!-- Mensagem de erro -->
        <% String erro = (String) request.getAttribute("erro"); %>
		<% if (erro != null) { %>
   			<p style="color: #c62828; text-align:center; font-weight:bold;"><%= erro %></p>
		<% } %>
        
        <form action="../login" method="post">
            <div class="form-group">
                <label for="email">E-mail</label>
                <input type="email" id="email" name="email" placeholder="Digite seu e-mail" required>
            </div>
            <div class="form-group">
                <label for="senha">Senha</label>
                <input type="password" id="senha" name="senha" placeholder="Digite sua senha" required>
            </div>
            <button type="submit" class="btn-login">Entrar</button>
        </form>
        
        <% if(request.getAttribute("erro") != null){ %>
		    <p style="color:red"><%= request.getAttribute("erro") %></p>
		<% } %>

        <div class="login-footer">
            <p>Não tem uma conta? <a href="../cliente/cadastro.jsp">Cadastre-se</a></p>
        </div>
    </div>
</body>
</html>