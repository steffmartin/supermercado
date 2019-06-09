<%-- 
    Document   : sucesso
    Created on : 29/10/2016, 20:22:36
    Author     : steff
--%>

<%@page import="MODEL.BEAM.SucessoException"%>
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
        <div class="container" id="sucesso">
            <div class="header">
                <h1>Sucesso</h1>
                <img src="images/logo-horizontal.png" alt="Logo SiMarket">
            </div>
            <div class="section">
                <%=exception.getMessage()%>
                <div class="buttonsBot">
                    <form method="post">
                        <button class="verdeButton leftButton" type="submit" formaction="<%= ((SucessoException) exception).getUrl1()%>" type="submit"><%= ((SucessoException) exception).getTexto1()%></button>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
