<%-- 
    Document   : alterar_senha
    Created on : 12/11/2016, 17:11:34
    Author     : steff
--%>

<%@page import="MODEL.BEAM.Usuario"%>
<%@page import="MODEL.BEAM.Central"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
   Central c = (Central)request.getAttribute("central");
   Usuario u = c.getLogado();
   
   //Proteger página de usuários não logados
   if(c==null || u == null)
   {
      RequestDispatcher rd = getServletContext().getRequestDispatcher("/Inicializador");
      rd.forward(request, response);
   }
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>SiMarket</title>
    <link rel="stylesheet" href="css/season.css">
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
    <script type="text/javascript" src="js/season.js"></script>
</head>

<body oncontextmenu="return false">
    <div class="container" id="trocarSenha">
        <div class="header">
            <h1>Alterar Senha</h1> <img src="images/logo-horizontal.png" alt="Logo SiMarket"> </div>
        <div class="section">
            <p>Para alterar sua senha preencha os campos abaixo:</p>
            <form action="AlterarSenha" method="post">
                <input type="password" name="senhaatual" placeholder="Senha Atual" pattern="[^']+" title="Não pode conter ( ' )." maxlength="10" required> <i class="fa fa-lock" aria-hidden="true"></i>
                <input type="password" name="novasenha" placeholder="Digite a Nova Senha" pattern="[^']+" title="Não pode conter ( ' )." maxlength="10" required> <i class="fa fa-lock" aria-hidden="true"></i>
                <input type="password" name="novasenha2" placeholder="Confirme a Nova Senha" pattern="[^']+" title="Não pode conter ( ' )." maxlength="10" required> <i class="fa fa-lock" aria-hidden="true"></i>
                <input type="checkbox" onclick="mostrarSenha(this)"> Mostrar campos
                <div class="buttonsBot">
                    <button class="azulButton leftButton" type="submit" name="alterar">Alterar</button>
                    <button class="cinzaButton rightButton" type="button" onclick="location.href='Principal'" name="voltar">Cancelar</button>
                </div>
            </form>
        </div>
    </div>
</body>

</html>
