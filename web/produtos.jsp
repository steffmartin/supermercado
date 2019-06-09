<%-- 
    Document   : produtos
    Created on : 12/11/2016, 11:09:06
    Author     : steff
--%>

<%@page import="MODEL.BEAM.Produto"%>
<%@page import="MODEL.BEAM.Central"%>
<%@page import="java.util.ArrayList"%>
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
   
   ArrayList<Produto> lista = (ArrayList<Produto>)request.getAttribute("lista");
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
    <div class="container" id="produtos">
        <div class="header">
            <h1>Produtos</h1> <img src="images/logo-horizontal.png" alt="Logo SiMarket"> </div>
        <div class="section">
            <div class="buttonsTop">
                <input id="search" type="search" placeholder="Pesquisar por ID, Descrição ou Código de Barras..." maxlength="100" onsearch="filtroProdutos()">
                <button class="azulButton rightButton" type="button" onclick="filtroProdutos()"> <i class="fa fa-search" aria-hidden="true"></i> </button>
            </div>
            <form action="" method="post">
                <div class="table">
                    <table id="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Descrição</th>
                                <th>Unidade de Medida</th>
                                <th>Código de Barras</th>
                                <th>Preço de Compra</th>
                                <th>Preço de Venda</th>
                                <th>Quantidade Disponível</th>
                                <th>Quantidade Mínima</th>
                                <th>Avisar</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if(lista.isEmpty()){ %>
                            <tr>
                                <td colspan="8">Não há produtos cadastrados.</td>
                            </tr>
                            <% ;} else { for(Produto p : lista) { %>
                            <tr <% if(c.getEstoque_baixo().contains(p.getId())){ %>class="cancelada"<% ;} %>>
                                <td><input type="radio" name="id" required value="<%=p.getId() %>"><%=p.getId() %></td>
                                <td><%=p.getDescricao() %></td>
                                <td><%=p.getUn_medida() %></td>
                                <td><%=p.getCod_barras() %></td>
                                <td dir="rtl"><%=String.format("%.2f", p.getPreco_compra()) %></td>
                                <td dir="rtl"><%=String.format("%.2f", p.getPreco_venda()) %></td>
                                <td dir="rtl"><%=String.format("%.3f", p.getQtd_disponivel()) %></td>
                                <td dir="rtl"><%=String.format("%.3f", p.getQtd_aviso()) %></td>
                                <td><i class="fa <% if(p.getNotificar()){ %>fa-check<% ;} else { %>fa-times<% ;} %>" aria-hidden="true"></i></td>
                            </tr>
                            <% ;} } %>
                        </tbody>
                    </table>
                </div>
                <button class="azulButton" type="button" onclick="location.href='NovoProduto'" name="novo"> <i class="fa fa-plus-square-o" aria-hidden="true"></i> Incluir </button>
                <% if(!lista.isEmpty()){ %>
                <button class="azulButton" type="submit" formaction="EditarProduto" name="editar"> <i class="fa fa-pencil-square-o" aria-hidden="true"></i> Editar </button>
                <button class="vermelhoButton" formaction="ExcluirProduto" type="submit" name="excluir"> <i class="fa fa-minus-square-o" aria-hidden="true"></i> Excluir </button>
                <% ;} %>
            </form>
            <div class="buttonsBot">
                <button class="cinzaButton rightButton" type="button" onclick="location.href='Principal'">Fechar</button>
            </div>
        </div>
    </div>
</body>

</html>