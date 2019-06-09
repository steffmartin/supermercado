<%-- 
    Document   : vendas
    Created on : 12/11/2016, 11:51:54
    Author     : steff
--%>

<%@page import="java.time.format.FormatStyle"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.Locale"%>
<%@page import="MODEL.BEAM.Venda"%>
<%@page import="java.util.ArrayList"%>
<%@page import="MODEL.BEAM.Central"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
 Central c = (Central)request.getAttribute("central");
   if(c==null || c.getLogado() == null)
   {
      RequestDispatcher rd = getServletContext().getRequestDispatcher("/Inicializador");
      rd.forward(request, response);
   }
   else if(!c.getLogado().temPapel("P_EfetuarVenda"))
   {
      RequestDispatcher rd = getServletContext().getRequestDispatcher("/Principal");
      rd.forward(request, response);
   }
   ArrayList<Venda> lista = (ArrayList<Venda>)request.getAttribute("lista");
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
    <div class="container" id="vendas">
        <div class="header">
            <h1>Vendas</h1> <img src="images/logo-horizontal.png" alt="Logo SiMarket"> </div>
        <div class="section">
            <div class="buttonsTop">
                <input id="search" type="search" placeholder="Pesquisar por ID, Data e Hora ou Vendedor..." maxlength="100" onsearch="filtroVendas()">
                <button class="azulButton rightButton" type="button" onclick="filtroVendas()"> <i class="fa fa-search" aria-hidden="true"></i> </button>
            </div>
            <form action="VerVenda" method="post">
                <div class="table">
                    <table id="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Data e Hora</th>
                                <th>Valor Bruto</th>
                                <th>Descontos</th>
                                <th>Valor Líquido</th>
                                <th>Quantidade de itens</th>
                                <th>Vendedor</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if(lista.isEmpty()){ %>
                            <tr>
                                <td colspan="8">Não há vendas finalizadas.</td>
                            </tr>
                            <% ;} else { for(Venda v : lista) { %>
                            <tr <% if(v.getForma_pagamento().equalsIgnoreCase("Cancelada")){ %>class="cancelada"<% ;} %>>
                                <td><input type="radio" name="id" required value="<%=v.getId() %>"><%=v.getId() %></td>
                                <td><%=v.getDatahoraFormatada() %></td>
                                <td dir="rtl"><%=String.format("%.2f",v.getTotal_bruto()) %></td>
                                <td dir="rtl"><%=String.format("%.2f",v.getTotal_descontos()) %></td>
                                <td dir="rtl"><%=String.format("%.2f",v.getTotal_liquido()) %></td>
                                <td><%=v.getQtd_itens() %></td>
                                <td><%=v.getVendedor() %></td>
                            </tr>
                            <% ;} } %>
                        </tbody>
                    </table>
                </div>
                <button class="azulButton" type="button" onclick="location.href='<% if(c.getVenda_ativa()==null){ %>NovaVenda<% ;} else { %>ContinuarVenda<% ;} %>'" name="incluir"> <i class="fa fa-plus-square-o" aria-hidden="true"></i> <% if(c.getVenda_ativa()==null){ %>Incluir<% ;} else { %>Continuar<% ;} %> </button>
                <% if(!lista.isEmpty()){ %>
                <button class="azulButton" type="submit" name="ver"> <i class="fa fa-file-o" aria-hidden="true"></i> Ver </button>
                <% ;} %>
            </form>
            <div class="buttonsBot">
                <button class="cinzaButton rightButton" type="button" onclick="location.href='Principal'">Fechar</button>
            </div>
        </div>
    </div>
</body>

</html>
