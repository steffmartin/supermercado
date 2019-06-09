<%-- 
    Document   : troco
    Created on : 15/11/2016, 19:28:56
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
   int[] cx = (int[])request.getAttribute("saque");
   float quebra = (float)request.getAttribute("quebra");
   float troco = (float)request.getAttribute("troco");
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
            <h1>Troco: R$ <%=String.format("%.2f",troco) %></h1> <img src="images/logo-horizontal.png" alt="Logo SiMarket"> </div>
        <div class="section">
            <form action="ConfirmarTroco" method="post">
               <p>Devolva o troco nas quantidades especificas abaixo:</p>
                <fieldset class="notas">
                    <legend>Notas: </legend>
                    <ul>
                        <% if(cx[0]>0){ %>
                        <li>
                            R$ 100,00 x<input disabled type="number" min="0" max="999999" name="ncem" style="background-image: url(images/ncem.png)" value="<%=cx[0] %>">
                        </li>
                        <% ;} %>
                        <% if(cx[1]>0){ %>
                        <li>
                            R$ 50,00 x<input disabled type="number" min="0" max="999999" name="ncinquenta" style="background-image: url(images/ncinquenta.png)"value="<%=cx[1] %>">
                        </li>
                        <% ;} %>
                        <% if(cx[2]>0){ %>
                        <li>
                            R$ 20,00 x<input disabled type="number" min="0" max="999999" name="nvinte" style="background-image: url(images/nvinte.png)"value="<%=cx[2] %>">
                        </li>
                        <% ;} %>
                        <% if(cx[3]>0){ %>
                        <li>
                            R$ 10,00 x<input disabled type="number" min="0" max="999999" name="ndez" style="background-image: url(images/ndez.png)"value="<%=cx[3] %>">
                        </li>
                        <% ;} %>
                        <% if(cx[4]>0){ %>
                        <li>
                            R$ 5,00 x<input disabled type="number" min="0" max="999999" name="ncinco" style="background-image: url(images/ncinco.png)"value="<%=cx[4] %>">
                        </li>
                        <% ;} %>
                        <% if(cx[5]>0){ %>
                        <li>
                            R$ 2,00 x<input disabled type="number" min="0" max="999999" name="ndois" style="background-image: url(images/ndois.png)"value="<%=cx[5] %>">
                        </li>
                        <% ;} %>
                    </ul>
                </fieldset>
                <fieldset class="moedas">
                    <legend>Moedas: </legend>
                    <ul>
                        <% if(cx[6]>0){ %>
                        <li>
                            R$ 1,00 x<input disabled type="number" min="0" max="999999" name="mum" style="background-image: url(images/mum.png)"value="<%=cx[6] %>">
                        </li>
                        <% ;} %>
                        <% if(cx[7]>0){ %>
                        <li>
                            R$ 0,50 x<input disabled type="number" min="0" max="999999" name="mcinquenta" style="background-image: url(images/mcinquenta.png)"value="<%=cx[7] %>">
                        </li>
                        <% ;} %>
                        <% if(cx[8]>0){ %>
                        <li>
                            R$ 0,25 x<input disabled type="number" min="0" max="999999" name="mvintecinco" style="background-image: url(images/mvintecinco.png)"value="<%=cx[8] %>">
                        </li>
                        <% ;} %>
                        <% if(cx[9]>0){ %>
                        <li>
                            R$ 0,10 x<input disabled type="number" min="0" max="999999" name="mdez" style="background-image: url(images/mdez.png)"value="<%=cx[9] %>">
                        </li>
                        <% ;} %>
                        <% if(cx[10]>0){ %>
                        <li>
                            R$ 0,05 x<input disabled type="number" min="0" max="999999" name="mcinco" style="background-image: url(images/mcinco.png)"value="<%=cx[10] %>">
                        </li>
                        <% ;} %>
                        <% if(cx[11]>0){ %>
                        <li>
                            R$ 0,01 x<input disabled type="number" min="0" max="999999" name="mzeroum" style="background-image: url(images/mzeroum.png)"value="<%=cx[11] %>">
                        </li>
                        <% ;} %>
                    </ul>
                </fieldset>
                        <% if(quebra != 0){ %>
                        <p><input type="checkbox" required> O cliente concorda com a devolução parcial do troco (Falta R$ <%=String.format("%.2f",quebra) %>).</p>
                        <% ;} %>
                <div class="buttonsBot">
                    <button class="azulButton leftButton" type="submit" name="confirmar">Confirmar</button>
                    <button class="cinzaButton rightButton" type="button" name="fechar" onclick="location.href='<% if(request.getAttribute("emdinheiro")!=null){ %>VoltarAoDeposito<% ;} else{ %>ContinuarVenda<% ;} %>'">Voltar</button>
                </div>
            </form>
        </div>
    </div>
</body>

</html>

