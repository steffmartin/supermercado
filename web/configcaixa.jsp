<%-- 
    Document   : configuracoes_caixa
    Created on : 12/11/2016, 13:47:10
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
   else if(!c.getLogado().temPapel("P_EfetuarVenda"))
   {
      RequestDispatcher rd = getServletContext().getRequestDispatcher("/Principal");
      rd.forward(request, response);
   }
   int[] cx = c.getCaixa();
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
            <h1>Configurações do Caixa</h1> <img src="images/logo-horizontal.png" alt="Logo SiMarket"> </div>
        <div class="section">
            <form action="ConfigurarCaixa" method="post">
               <p>Informe a quantidade disponível em caixa para cada um dos valores abaixo:</p>
                <fieldset class="notas">
                    <legend>Notas: </legend>
                    <ul>
                        <li>
                            R$ 100,00 x<input type="number" min="0" max="999999" name="ncem" style="background-image: url(images/ncem.png)" value="<%=cx[0] %>">
                        </li>
                        <li>
                            R$ 50,00 x<input type="number" min="0" max="999999" name="ncinquenta" style="background-image: url(images/ncinquenta.png)"value="<%=cx[1] %>">
                        </li>
                        <li>
                            R$ 20,00 x<input type="number" min="0" max="999999" name="nvinte" style="background-image: url(images/nvinte.png)"value="<%=cx[2] %>">
                        </li>
                        <li>
                            R$ 10,00 x<input type="number" min="0" max="999999" name="ndez" style="background-image: url(images/ndez.png)"value="<%=cx[3] %>">
                        </li>
                        <li>
                            R$ 5,00 x<input type="number" min="0" max="999999" name="ncinco" style="background-image: url(images/ncinco.png)"value="<%=cx[4] %>">
                        </li>
                        <li>
                            R$ 2,00 x<input type="number" min="0" max="999999" name="ndois" style="background-image: url(images/ndois.png)"value="<%=cx[5] %>">
                        </li>
                    </ul>
                </fieldset>
                <fieldset class="moedas">
                    <legend>Moedas: </legend>
                    <ul>
                        <li>
                            R$ 1,00 x<input type="number" min="0" max="999999" name="mum" style="background-image: url(images/mum.png)"value="<%=cx[6] %>">
                        </li>
                        <li>
                            R$ 0,50 x<input type="number" min="0" max="999999" name="mcinquenta" style="background-image: url(images/mcinquenta.png)"value="<%=cx[7] %>">
                        </li>
                        <li>
                            R$ 0,25 x<input type="number" min="0" max="999999" name="mvintecinco" style="background-image: url(images/mvintecinco.png)"value="<%=cx[8] %>">
                        </li>
                        <li>
                            R$ 0,10 x<input type="number" min="0" max="999999" name="mdez" style="background-image: url(images/mdez.png)"value="<%=cx[9] %>">
                        </li>
                        <li>
                            R$ 0,05 x<input type="number" min="0" max="999999" name="mcinco" style="background-image: url(images/mcinco.png)"value="<%=cx[10] %>">
                        </li>
                        <li>
                            R$ 0,01 x<input type="number" min="0" max="999999" name="mzeroum" style="background-image: url(images/mzeroum.png)"value="<%=cx[11] %>">
                        </li>
                    </ul>
                </fieldset>
                <div class="buttonsBot">
                    <button class="azulButton leftButton" type="submit" name="salvar">Salvar</button>
                    <button class="cinzaButton rightButton" type="button" name="fechar" onclick="location.href='Principal'">Cancelar</button>
                </div>
            </form>
        </div>
    </div>
</body>

</html>
