<%-- 
    Document   : novo_produto
    Created on : 12/11/2016, 11:17:34
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
   else if(!c.getLogado().temPapel("P_GerenciarProduto"))
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
    <div class="container" id="novoProduto">
        <div class="header">
            <h1>Novo Produto</h1> <img src="images/logo-horizontal.png" alt="Logo SiMarket"> </div>
        <div class="section">
            <form action="NovoProduto" method="post">
                <input type="text" name="descricao" placeholder="Descrição" maxlength="100" pattern="[^']+" title="Não pode conter ( ' )." required> <i class="fa fa-font" aria-hidden="true"></i>
                <input type="number" name="cod_barras" min=0 max=9999999999999 placeholder="Código de Barras" required> <i class="fa fa-barcode" aria-hidden="true"></i>
                <select name="un_medida" required>
                    <option value="">Unidade de Medida</option>
                    <option value="CX">Caixa</option>
                    <option value="CM">Centímetros</option>
                    <option value="GR">Gramas</option>
                    <option value="KG">Kilogramas</option>
                    <option value="LT">Litros</option>
                    <option value="ML">Mililitros</option>
                    <option value="MT">Metros</option>
                    <option value="PC">Peça</option>
                    <option value="UN">Unitário</option>
                </select> <i class="fa fa-arrows" aria-hidden="true"></i>
                <input type="number" name="preco_compra" min=0 placeholder="Preço de Compra" step="0.01" required> <i class="fa fa-usd" aria-hidden="true"></i>
                <input type="number" name="preco_venda" min=0 placeholder="Preço de Venda" step="0.01" required> <i class="fa fa-usd" aria-hidden="true"></i>
                <input type="number" name="qtd_disponivel" min=0 placeholder="Estoque Inicial" step="0.01" required> <i class="fa fa-cubes" aria-hidden="true"></i>
                <input type="number" name="qtd_aviso" min=0 placeholder="Estoque Mínimo" step="0.01" required> <i class="fa fa-cube" aria-hidden="true"></i>
                <input type="checkbox" name="notificar" checked>Desejo saber quando o estoque mínimo for atingido.
                <div class="buttonsBot">
                    <button class="azulButton leftButton" type="submit" name="incluir">Salvar</button>
                    <button class="cinzaButton rightButton" type="button" onclick="location.href='Produtos'" name="voltar">Cancelar</button>
                </div>
            </form>
        </div>
    </div>
</body>

</html>
