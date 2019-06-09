<%-- 
    Document   : venda
    Created on : 15/11/2016, 14:34:46
    Author     : steff
--%>

<%@page import="MODEL.BEAM.Usuario"%>
<%@page import="MODEL.BEAM.Venda"%>
<%@page import="MODEL.BEAM.Central"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
   Central c = (Central)request.getAttribute("central");
   if(c==null || c.getLogado() == null || c.getVenda_ativa() == null)
   {
      RequestDispatcher rd = getServletContext().getRequestDispatcher("/Inicializador");
      rd.forward(request, response);
   }
   else if(!c.getLogado().temPapel("P_EfetuarVenda"))
   {
      RequestDispatcher rd = getServletContext().getRequestDispatcher("/Principal");
      rd.forward(request, response);
   }
   Venda v = c.getVenda_ativa();
   Usuario u = c.getLogado();
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
    <div class="container" id="novaVenda">
        <div class="header">
            <h1>Nova Venda</h1> <img src="images/logo-horizontal.png" alt="Logo SiMarket"> </div>
        <div class="section">
            <!-- LADO ESQUERDO 0-->
            <div class="subContainer">
                <!--INSERIR PRODUTO / REMOVER -->
                <form method="post" name="FormAddDelProd">
                    <input type="number" onfocus="this.value=''" name="qtd" min=0 max=999 step="0.001" value="1" required>
                    <input type="number" autofocus name="cod_barras" min=0 max=9999999999999 placeholder="Código de Barras" required> <span>*</span> <i class="fa fa-barcode" aria-hidden="true"></i>
                    <button class="verdeButton" type="submit" formaction="VendaAdicionar"> <i class="fa fa-plus-circle" aria-hidden="true"></i></button>
                    <button class="vermelhoButton" type="submit" formaction="VendaRemover"> <i class="fa fa-minus-circle" aria-hidden="true"></i></button>
                </form>
                <!--OPÇÕES (DESCONTO, FORMA DE PAGAMENTO) -->
                <fieldset>
                    <legend>Outras Opções:</legend>
                    <button class="azulButton" id="desconto" name="desconto" type="button" onclick="mostraOpvenda('desconto')"><i class="fa fa-tag" aria-hidden="true"></i>Desconto</button>
                    <select name="pagamento" id="pagamento" onclick="mostraOpvenda(this.value)" required>
                        <option value="">Inserir Forma de Pagamento</option>
                        <option value="dinheiro" <% if(v.getForma_pagamento().contains("Dinheiro")||v.getForma_pagamento().contains("Cheque")){ %>selected> <% ; %><%=v.getForma_pagamento() %><% ;} else{ %> > Dinheiro / Cheque<% ;} %></option>
                        <option value="cartaocredito" <% if(v.getForma_pagamento().contains("Crédito")){ %>selected> <% ; %><%=v.getForma_pagamento() %><% ;} else{ %> >Cartão de Crédito<% ;} %></option>
                        <option value="cartaodebito" <% if(v.getForma_pagamento().contains("Débito")){ %>selected> <% ; %><%=v.getForma_pagamento() %><% ;} else{ %> >Cartão de Débito<% ;} %></option>
                        <option value="ticket" <% if(v.getForma_pagamento().contains("Ticket")){ %>selected> <% ; %><%=v.getForma_pagamento() %><% ;} else{ %> >Ticket<% ;} %></option>
                    </select><i class="fa fa-list-ul" aria-hidden="true"></i> </fieldset>
                <!-- FORMULARIOS OCULTOS -->
                <div class="opvenda desconto ocultar">
                    <form method="post" name="FormDesconto" action="DescontoVenda">
                        <% if(!u.temPapel("P_ConcederDesconto")){ %>
                        <p>Você não tem permissão para conceder descontos. Solicite liberação a um supervisor:</p>
                        <input type="text" name="usuario" placeholder="Usuário" pattern="[^']+" title="Não pode conter ( ' )." maxlength="20" required> <i class="fa fa-user" aria-hidden="true"></i>
                        <input type="password" name="senha" placeholder="Senha" pattern="[^']+" title="Não pode conter ( ' )." maxlength="10" required> <i class="fa fa-lock" aria-hidden="true"></i>
                        <% ;} %>
                        <input type="number" name="desconto" min=0 placeholder="Valor de Desconto a Adicionar (Cumulativo)" step="0.01" required> <i class="fa fa-usd" aria-hidden="true"></i>
                        <button class="verdeButton" type="submit"> <i class="fa fa-plus-circle" aria-hidden="true"></i></button>
                    </form>
                </div>
                    <div class="opvenda dinheiro ocultar">
                        <form method="post" action="VendaFormaPagamento">
                        <ul>
                            <li> <i class="fa fa-money" aria-hidden="true"></i>
                                <input type="radio" value="Dinheiro" name="forma" required <% if(v.getForma_pagamento().contains("Dinheiro")){ %>checked<% ;} %>>Dinheiro </li>
                            <li> <i class="fa fa-university" aria-hidden="true"></i>
                                <input type="radio" value="Cheque" name="forma" required <% if(v.getForma_pagamento().contains("Cheque")){ %>checked<% ;} %>>Cheque </li>
                        </ul>
                        <div>
                            <input required type="number" name="numero" min=0 step="0.01" <% if(v.getValor_recebido()>0 && (v.getForma_pagamento().contains("Dinheiro") || v.getForma_pagamento().contains("Cheque"))){ %>value="<%=v.getValor_recebido() %>"<% ;}else{ %>placeholder="Valor Recebido em Dinheiro / Cheque"<% ;} %>><i class="fa fa-usd" aria-hidden="true"></i>
                            <button class="verdeButton" type="submit" name="gravar1"><i class="fa fa-floppy-o" aria-hidden="true"></i></button>
                        </div>
                        </form>
                    </div>
                    <div class="opvenda cartaocredito ocultar">
                        <form method="post" action="VendaFormaPagamento">
                        <ul>
                            <li> <i class="fa fa-cc-visa" aria-hidden="true"></i>
                                <input type="radio" value="Cartão de Crédito Visa" name="forma" required <% if(v.getForma_pagamento().contains("Crédito Visa")){ %>checked<% ;} %>>Visa </li>
                            <li> <i class="fa fa-cc-mastercard" aria-hidden="true"></i>
                                <input type="radio" value="Cartão de Crédito MasterCard" name="forma" required <% if(v.getForma_pagamento().contains("Crédito Master")){ %>checked<% ;} %>>MasterCard </li>
                            <li> <i class="fa fa-cc-amex" aria-hidden="true"></i>
                                <input type="radio" value="Cartão de Crédito American Express" name="forma" required <% if(v.getForma_pagamento().contains("Crédito American")){ %>checked<% ;} %>>American Express </li>
                            <li> <i class="fa fa-credit-card-alt" aria-hidden="true"></i>
                                <input type="radio" value="Cartão de Crédito Alelo" name="forma" required <% if(v.getForma_pagamento().contains("Crédito Alelo")){ %>checked<% ;} %>>Alelo </li>
                        </ul>
                        <div>
                            <input required type="number" name="numero" min=1 max=12 <% if(v.getForma_pagamento().contains("Crédito")){ %>value="<%=v.getParcelas() %>"<% ;} else{ %>placeholder="Quantidade de Parcelas"<% ;} %>>
                            <button class="verdeButton" type="submit" name="gravar2"><i class="fa fa-floppy-o" aria-hidden="true"></i></button>
                        </div>
                            </form>
                    </div>
                    <div class="opvenda cartaodebito ocultar">
                        <form method="post" action="VendaFormaPagamento">
                        <ul>
                            <li> <i class="fa fa-cc-visa" aria-hidden="true"></i>
                                <input type="radio" value="Cartão de Débito Visa Electron" name="forma" required <% if(v.getForma_pagamento().contains("Débito Visa")){ %>checked<% ;} %>>Visa Electron</li>
                            <li> <i class="fa fa-cc-mastercard" aria-hidden="true"></i>
                                <input type="radio" value="Cartão de Débito MasterCard" name="forma" required <% if(v.getForma_pagamento().contains("Débito Master")){ %>checked<% ;} %>>MasterCard </li>
                            <li> <i class="fa fa-credit-card-alt" aria-hidden="true"></i>
                                <input type="radio" value="Cartão de Débito Alelo" name="forma" required <% if(v.getForma_pagamento().contains("Débito Alelo")){ %>checked<% ;} %>>Alelo </li>
                        </ul>
                        <div>
                            <input required type="number" name="numero" min=1 max=12 placeholder="Quantidade de Parcelas" value="1" class="invisivel">
                            <button class="verdeButton" type="submit" name="gravar3"><i class="fa fa-floppy-o" aria-hidden="true"></i></button>
                        </div>
                        </form>
                    </div>
                    <div class="opvenda ticket ocultar">
                        <form method="post" action="VendaFormaPagamento">
                        <ul>
                            <li> <i class="fa fa-ticket" aria-hidden="true"></i>
                                <input type="radio" value="Ticket Policard" name="forma" required <% if(v.getForma_pagamento().contains("Ticket Poli")){ %>checked<% ;} %>>Policard </li>
                            <li> <i class="fa fa-ticket" aria-hidden="true"></i>
                                <input type="radio" value="Ticket Alelo Alimentação" name="forma" required <% if(v.getForma_pagamento().contains("Ticket Alelo")){ %>checked<% ;} %>>Alelo Alimentação</li>
                            <li> <i class="fa fa-ticket" aria-hidden="true"></i>
                                <input type="radio" value="Ticket Sodexo Alimentação" name="forma" required <% if(v.getForma_pagamento().contains("Ticket Sodexo")){ %>checked<% ;} %>>Sodexo Alimentação</li>
                            <li> <i class="fa fa-ticket" aria-hidden="true"></i>
                                <input type="radio" value="Ticket Alimentação" name="forma" required <% if(v.getForma_pagamento().contains("Ticket Alim")){ %>checked<% ;} %>>Ticket Alimentação</li>
                        </ul>
                        <div>
                            <input required type="number" name="numero" min=1 max=12 placeholder="Quantidade de Parcelas" value="1" class="invisivel">
                            <button class="verdeButton" type="submit" name="gravar4"><i class="fa fa-floppy-o" aria-hidden="true"></i></button>
                        </div>
                        </form>
                    </div>
                <!-- Totais da compra -->
                <div class="informacoes">
                    <div class="qitens">Qtd.<br><%=v.getQtd_itens() %></div>
                    <div class="total">Total<br><%=String.format("%.2f",v.getTotal_liquido()) %></div>
                    <% if(v.getValor_recebido()>0){ %>
                    <div class="recebido">Pago<br><%=String.format("%.2f",v.getValor_recebido()) %></div>
                    <% ;} %>
                    <% if(v.getTroco()>0){ %>
                    <div class="troco">Troco<br><%=String.format("%.2f",v.getTroco()) %></div>
                    <% ;} %>
                </div>
            </div>
            <!-- CUPOM FISCAL (LADO DIREITO) --><pre class="cupom"><%=v.getCupom() %></pre>
            <div class="buttonsBot">
                <form method="post">
                    <button class="azulButton" type="submit" name="finalizar" formaction="<% if(v.getForma_pagamento().equalsIgnoreCase("Dinheiro")){ %>FinalizarEmDinheiro<% ;} else if(v.getForma_pagamento().equalsIgnoreCase("Cheque")){ %>FinalizarEmCheque<% ;} else { %>FinalizarVenda<% ;} %>">Finalizar</button>
                <button class="vermelhoButton" type="submit" name="cancelar" formaction="CancelarVenda">Cancelar</button>
                <button class="cinzaButton rightButton" type="button" onclick="location.href='Principal'" name="voltar">Fechar</button>
                </form>
            </div>
        </div>
    </div>
</body>

</html>
