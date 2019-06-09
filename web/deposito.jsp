<%-- 
    Document   : deposito
    Created on : 16/11/2016, 12:16:28
    Author     : steff
--%>

<%@page import="MODEL.BEAM.Central"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
   Central c = (Central)request.getAttribute("central");
   if(c==null || c.getLogado() == null || c.getVenda_ativa()==null)
   {
      RequestDispatcher rd = getServletContext().getRequestDispatcher("/Inicializador");
      rd.forward(request, response);
   }
   else if(!c.getLogado().temPapel("P_EfetuarVenda"))
   {
      RequestDispatcher rd = getServletContext().getRequestDispatcher("/Principal");
      rd.forward(request, response);
   }
   float recebido = (float)request.getAttribute("recebido");
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>SiMarket</title>
    <link rel="stylesheet" href="css/season.css">
    <link rel="stylesheet" href="css/font-awesome.min.css"> </head>

<body oncontextmenu="return false">
    <div class="container" id="configCaixa">
        <div class="header">
            <h1>Valor Recebido: R$ <%=String.format("%.2f",recebido) %></h1> <img src="images/logo-horizontal.png" alt="Logo SiMarket"> </div>
        <div class="section">
            <form action="FinalizarEmDinheiro" method="post">
               <p>Informe com quais notas e moedas o valor foi recebido:</p>
                <fieldset class="notas">
                    <legend>Notas: </legend>
                    <ul>
                        <li>
                            R$ 100,00 x<input required value="0" type="number" min="0" max="999999" name="ncem" style="background-image: url(images/ncem.png)">
                        </li>
                        <li>
                            R$ 50,00 x<input required value="0" type="number" min="0" max="999999" name="ncinquenta" style="background-image: url(images/ncinquenta.png)">
                        </li>
                        <li>
                            R$ 20,00 x<input required value="0" type="number" min="0" max="999999" name="nvinte" style="background-image: url(images/nvinte.png)">
                        </li>
                        <li>
                            R$ 10,00 x<input required value="0" type="number" min="0" max="999999" name="ndez" style="background-image: url(images/ndez.png)">
                        </li>
                        <li>
                            R$ 5,00 x<input required value="0" type="number" min="0" max="999999" name="ncinco" style="background-image: url(images/ncinco.png)">
                        </li>
                        <li>
                            R$ 2,00 x<input required value="0" type="number" min="0" max="999999" name="ndois" style="background-image: url(images/ndois.png)">
                        </li>
                    </ul>
                </fieldset>
                <fieldset class="moedas">
                    <legend>Moedas: </legend>
                    <ul>
                        <li>
                            R$ 1,00 x<input required value="0" type="number" min="0" max="999999" name="mum" style="background-image: url(images/mum.png)">
                        </li>
                        <li>
                            R$ 0,50 x<input required value="0" type="number" min="0" max="999999" name="mcinquenta" style="background-image: url(images/mcinquenta.png)">
                        </li>
                        <li>
                            R$ 0,25 x<input required value="0" type="number" min="0" max="999999" name="mvintecinco" style="background-image: url(images/mvintecinco.png)">
                        </li>
                        <li>
                            R$ 0,10 x<input required value="0" type="number" min="0" max="999999" name="mdez" style="background-image: url(images/mdez.png)">
                        </li>
                        <li>
                            R$ 0,05 x<input required value="0" type="number" min="0" max="999999" name="mcinco" style="background-image: url(images/mcinco.png)">
                        </li>
                        <li>
                            R$ 0,01 x<input required value="0" type="number" min="0" max="999999" name="mzeroum" style="background-image: url(images/mzeroum.png)">
                        </li>
                    </ul>
                </fieldset>
                        <p><input type="checkbox" required> Testei as notas recebidas e afirmo que não são falsas.</p>
                <div class="buttonsBot">
                    <button class="azulButton leftButton" type="submit" name="confirmardeposito">Confirmar</button>
                    <button class="cinzaButton rightButton" type="button" name="fechar" onclick="location.href='ContinuarVenda'">Voltar</button>
                </div>
            </form>
        </div>
    </div>
</body>

</html>


