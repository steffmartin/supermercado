<%-- 
    Document   : login
    Created on : 11/11/2016, 19:38:21
    Author     : steff
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="UTF-8">
        <title>SiMarket</title>
        <link rel="stylesheet" href="css/season.css">
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <script type="text/javascript" src="js/season.js"></script>
    </head>

    <body oncontextmenu="return false">
        <div class="container" id="login">
            <div class="header">
                <h1>Login</h1> <img src="images/logo-horizontal.png" alt="Logo SiMarket"> </div>
            <div class="section">
                <form action="AtenticarUsuario" method="post">
                    <input type="text" name="usuario" placeholder="Usuário" pattern="[^']+" title="Não pode conter ( ' )." maxlength="20" required> <i class="fa fa-user" aria-hidden="true"></i>
                    <input type="password" name="senha" placeholder="Senha" pattern="[^']+" title="Não pode conter ( ' )." maxlength="10" required> <i class="fa fa-lock" aria-hidden="true"></i>
                    <div class="buttonsBot">
                        <button class="azulButton leftButton" type="submit" name="entrar">Entrar</button>
                    </div>
                </form>
            </div>
        </div>
    </body>

</html>