<%-- 
    Document   : erro
    Created on : 28/10/2016, 20:04:16
    Author     : steff
--%>

<%@page import="JDBC.ServerConnect"%>
<%@page import="JDBC.DataBaseConnect"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>SiMarket</title>
    <link rel="stylesheet" href="css/season.css">
    <link rel="stylesheet" href="css/font-awesome.min.css">
</head>
<body oncontextmenu="return false">
    <div class="container" id="erro">
        <div class="header">
            <h1>Erro</h1>
            <img src="images/logo-horizontal.png" alt="Logo SiMarket">
        </div>
        <div class="section">
            Um problema interno foi identificado, passe os detalhes abaixo para o suporte do sistema.
            <xmp class="mensagemerro">               
<%=exception.getMessage()%>
                
<%=exception.getCause()%></xmp>
            <div class="buttonsBot">
               <button class="cinzaButton rightButton" type="button" onclick="location.href='Principal'">Fechar</button>
            </div>
        </div>
    </div>
</body>
</html>
<%
//Página de erro sempre fechará as conexões

Connection conexao;
conexao = DataBaseConnect.fechaConexao();
conexao = ServerConnect.fechaConexao();
%>