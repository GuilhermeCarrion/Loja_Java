<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cadastrar - Loja Java</title>
<link href="${pageContext.request.contextPath}/Styles/styles.css" rel="stylesheet">
</head>
<body>

<div class="login-container">
    <h2>Criar conta</h2>

    <form action="${pageContext.request.contextPath}/register" method="post">
        <div class="form-group">
            <label for="email">E-mail</label>
            <input type="email" id="email" name="email" placeholder="Digite seu e-mail" required>
        </div>
        <div class="form-group">
            <label for="senha">Senha</label>
            <input type="password" id="senha" name="senha" placeholder="Digite sua senha" required>
        </div>
        <button type="submit" class="btn-login">Cadastrar</button>
    </form>

    <div class="login-footer">
        <p>Já tem uma conta? <a href="${pageContext.request.contextPath}/pages/login.jsp">Entrar</a></p>
    </div>
</div>

</body>
</html>