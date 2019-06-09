<%-- 
    Document   : ver_venda
    Created on : 17/11/2016, 15:21:10
    Author     : steff
--%>

<%@page import="MODEL.BEAM.Venda"%>
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
   Venda v = (Venda)request.getAttribute("venda");
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
    <div class="container" id="verVenda">
        <div class="header">
            <h1>Venda</h1> <img src="images/logo-horizontal.png" alt="Logo SiMarket"> </div>
        <div class="section">
            <!-- LADO ESQUERDO 0-->
            <div class="subContainer">
               <fieldset><legend>Dados da Operação</legend>
                <ul>
                    <li><b>ID:</b> <%=v.getId() %></li>
                    <li><b>Data:</b> <%=v.getDatahoraFormatada() %></li>
                    <li><b>Forma de Pagamento:</b> <%=v.getForma_pagamento() %></li>
                    <li><b>Vendedor:</b> <%=v.getVendedor() %></li>
                </ul>
                </fieldset>
                <fieldset><legend>Totais (R$)</legend>
                <ul>
                    <li><b>Total Bruto:</b> <span><%=String.format("%.2f",v.getTotal_bruto()) %></span></li>
                    <li><b>Descontos:</b> <span><%=String.format("%.2f",v.getTotal_descontos()) %></span></li>
                    <li><b>Total Líquido:</b> <span><%=String.format("%.2f",v.getTotal_liquido()) %></span></li>
                    <li><b>Valor Recebido:</b> <span><%=String.format("%.2f",v.getValor_recebido()) %></span></li>
                    <li><b>Troco:</b> <span><%=String.format("%.2f",v.getTroco()) %></span></li>
                    <li><b>Quebra de Caixa:</b> <span><%=String.format("%.2f",v.getQuebra_de_caixa()) %></span></li>
                    
                </ul>
                </fieldset>
                <!-- Totais da compra -->
                <div class="informacoes">
                    <div class="qitens">Qtd.<br><%=v.getQtd_itens() %></div>
                    <div class="total">Total<br><%=String.format("%.2f",v.getTotal_liquido()) %></div>
                    <div class="recebido">Pago<br><%=String.format("%.2f",v.getValor_recebido()) %></div>
                    <div class="troco">Troco<br><%=String.format("%.2f",v.getTroco()) %></div>
                </div>
            </div>
            <!-- CUPOM FISCAL (LADO DIREITO) --><pre class="cupom"><%=v.getCupom() %></pre>
            <div class="buttonsBot">
                <button class="cinzaButton rightButton" type="button" name="voltar" onclick="location.href='Vendas'">Voltar</button>
            </div>
        </div>
    </div>
</body>

</html>
