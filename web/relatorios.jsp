<%-- 
    Document   : relatorios
    Created on : 17/11/2016, 18:14:17
    Author     : steff
--%>

<%@page import="MODEL.BEAM.Central"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
   Central c = (Central)request.getAttribute("central");
   if(c==null || c.getLogado() == null)
   {
      RequestDispatcher rd = getServletContext().getRequestDispatcher("/Inicializador");
      rd.forward(request, response);
   }
   else if(!c.getLogado().temPapel("P_VisualizarRelatorio"))
   {
      RequestDispatcher rd = getServletContext().getRequestDispatcher("/Principal");
      rd.forward(request, response);
   }
   
%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>SiMarket</title>
    <link rel="stylesheet" href="css/season.css">
    <link rel="stylesheet" href="css/font-awesome.min.css"> </head>

<body oncontextmenu="return false">
    <div class="container" id="relatorios">
        <div class="header">
            <h1>Relatórios</h1> <img src="images/logo-horizontal.png" alt="Logo SiMarket"> </div>
        <div class="section">
            <fieldset class="itensFaltantes">
                <legend>Saldo em Estoque</legend>
                <form action="VerRelatorio" method="post">
                    <ul>
                        <li>
                            <input type="radio" value="observados" name="opcao" required>Produtos observados e com estoque mínimo atingido</li>
                        <li>
                            <input type="radio" value="zerados" name="opcao" required>Produtos sem saldo em estoque</li>
                    </ul>
                    <button class="azulButton" type="submit" name="estoque"><i class="fa fa-eye" aria-hidden="true"></i>Gerar</button>
                </form>
            </fieldset>
            <fieldset class="vendaPeriodo">
                <legend>Vendas por período</legend>
                <form action="VerRelatorio" method="post">
                    <ul>
                        <li> Data Inicial:
                            <input type="date" name="de" required> Data Final:
                            <input type="date" name="ate" required>
                            <input type="checkbox" name="canceladas" value="cancelada" title="Marque para também mostrar as vendas canceladas">Canc.
                        </li>
                    </ul>
                    <button class="azulButton" type="submit" name="vendas"><i class="fa fa-eye" aria-hidden="true"></i>Gerar</button>
                </form>
            </fieldset>
            <fieldset class="cadastros">
                <legend>Cadastros</legend>
                <form action="VerRelatorio" method="post">
                    <ul>
                        <li>
                            <input type="radio" value="produtos" name="tipo" required>Produtos</li>
                           <li> <input type="radio" value="usuarios" name="tipo" required>Usuários </li>
                    </ul>
                    <button class="azulButton" type="submit" name="cadastro"><i class="fa fa-eye" aria-hidden="true"></i>Gerar</button>
                </form>
            </fieldset>
            <div class="buttonsBot">
                <button class="cinzaButton rightButton" type="button" name="fechar" onclick="location.href='Principal'">Fechar</button>
            </div>
        </div>
    </div>
</body>

</html>