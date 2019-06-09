<%-- 
    Document   : alterar_produto
    Created on : 13/11/2016, 11:05:52
    Author     : steff
--%>

<%@page import="MODEL.BEAM.Produto"%>
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
   
    Produto p = (Produto) request.getAttribute("produto");
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
            <h1>Editar Produto</h1> <img src="images/logo-horizontal.png" alt="Logo SiMarket"> </div>
        <div class="section">
            <form action="EditarProduto" method="post">
                <input type="text" name="descricao" placeholder="Descrição" maxlength="100" pattern="[^']+" title="Não pode conter ( ' )." required value="<%=p.getDescricao() %>"> <i class="fa fa-font" aria-hidden="true"></i>
                <input type="number" name="cod_barras" min=0 max=9999999999999 placeholder="Código de Barras" required value="<%=p.getCod_barras() %>"> <i class="fa fa-barcode" aria-hidden="true"></i>
                <select name="un_medida" required>
                    <option value="">Unidade de Medida</option>
                    <option value="CX" <% if(p.getUn_medida().equals("CX")){ %> selected <% ;} %> >Caixa</option>
                    <option value="CM" <% if(p.getUn_medida().equals("CM")){ %> selected <% ;} %> >Centímetros</option>
                    <option value="GR" <% if(p.getUn_medida().equals("GR")){ %> selected <% ;} %> >Gramas</option>
                    <option value="KG" <% if(p.getUn_medida().equals("KG")){ %> selected <% ;} %> >Kilogramas</option>
                    <option value="LT" <% if(p.getUn_medida().equals("LT")){ %> selected <% ;} %> >Litros</option>
                    <option value="ML" <% if(p.getUn_medida().equals("ML")){ %> selected <% ;} %> >Mililitros</option>
                    <option value="MT" <% if(p.getUn_medida().equals("MT")){ %> selected <% ;} %> >Metros</option>
                    <option value="PC" <% if(p.getUn_medida().equals("PC")){ %> selected <% ;} %> >Peça</option>
                    <option value="UN" <% if(p.getUn_medida().equals("UN")){ %> selected <% ;} %> >Unitário</option>
                </select> <i class="fa fa-arrows" aria-hidden="true"></i>
                <input type="number" name="preco_compra" min=0 placeholder="Preço de Compra" step="0.01" required value="<%=p.getPreco_compra() %>"> <i class="fa fa-usd" aria-hidden="true"></i>
                <input type="number" name="preco_venda" min=0 placeholder="Preço de Venda" step="0.01" required value="<%=p.getPreco_venda() %>"> <i class="fa fa-usd" aria-hidden="true"></i>
                <input type="number" name="qtd_disponivel" min=0 placeholder="Estoque Disponível" step="0.01" required value="<%=p.getQtd_disponivel() %>"> <i class="fa fa-cubes" aria-hidden="true"></i>
                <input type="number" name="qtd_aviso" min=0 placeholder="Estoque Mínimo" step="0.01" required value="<%=p.getQtd_aviso() %>"> <i class="fa fa-cube" aria-hidden="true"></i>
                <input type="checkbox" name="notificar" <% if(p.getNotificar()){ %>checked<% ;} %>>Desejo saber quando o estoque mínimo for atingido.
                <input type="radio" name="id" value="<%=p.getId() %>" class="ocultar" required checked>
                <div class="buttonsBot">
                    <button class="azulButton leftButton" type="submit" name="salvar">Salvar</button>
                    <button class="cinzaButton rightButton" type="button" onclick="location.href='Produtos'" name="voltar">Cancelar</button>
                </div>
            </form>
        </div>
    </div>
</body>

</html>
