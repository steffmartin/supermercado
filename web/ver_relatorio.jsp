<%-- 
    Document   : ver_relatorio
    Created on : 17/11/2016, 18:24:31
    Author     : steff
--%>

<%@page import="MODEL.BEAM.Relatorio"%>
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
   
    Relatorio r = (Relatorio)request.getAttribute("relatorio");
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>SiMarket</title>
    <link rel="stylesheet" href="css/season.css">
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
    <script type="text/javascript" src="js/season.js"></script> </head>

<body oncontextmenu="return false">
    <div class="container" id="relatoriogerado">
        <div class="header">
            <h1>Visualização de Relatório</h1> <img src="images/logo-horizontal.png" alt="Logo SiMarket"> </div>
        <div class="section">
               <p><%=r.getNome() %></p>
			   <div class="relatorio">
                               <div>Empresa: <%=c.getEmpresa() %>
<%=r.getConteudo() %></div>
			   </div>
                <div class="buttonsBot">
                    <button class="azulButton leftButton" type="button" name="imprimir" onclick="$('*').scrollTop(0);window.print()">Imprimir</button>
                    <button class="cinzaButton rightButton" type="button" name="voltar" onclick="location.href='Relatorios'">Voltar</button>
                </div>
        </div>
    </div>
</body>

</html>