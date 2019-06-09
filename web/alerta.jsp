<%-- 
    Document   : alerta
    Created on : 29/10/2016, 20:29:28
    Author     : steff
--%>

<%@page import="MODEL.BEAM.AlertaException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>

<% String acao = (String)request.getAttribute("acao"); %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>SiMarket</title>
        <link rel="stylesheet" href="css/season.css">
        <link rel="stylesheet" href="css/font-awesome.min.css">
    </head>
    <body oncontextmenu="return false">
        <div class="container" id="alerta">
            <div class="header">
                <h1>Alerta</h1>
                <img src="images/logo-horizontal.png" alt="Logo SiMarket">
            </div>
            <div class="section">
                <%=exception.getMessage()%>
                <div class="buttonsBot">
                    <form method="post" >
                        <button class="amareloButton leftButton" type="submit" formaction="<%= ((AlertaException) exception).getUrl1()%>" ><%= ((AlertaException) exception).getTexto1()%></button>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
