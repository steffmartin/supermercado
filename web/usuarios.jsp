<%-- 
    Document   : usuarios
    Created on : 12/11/2016, 10:11:45
    Author     : steff
--%>

<%@page import="MODEL.BEAM.Central"%>
<%@page import="java.util.ArrayList"%>
<%@page import="MODEL.BEAM.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
   //Proteger página de usuários não logados ou sem acesso
   Central c = (Central)request.getAttribute("central");
   if(c==null || c.getLogado() == null)
   {
      RequestDispatcher rd = getServletContext().getRequestDispatcher("/Inicializador");
      rd.forward(request, response);
   }
   else if(!c.getLogado().temPapel("P_GerenciarUsuario"))
   {
      RequestDispatcher rd = getServletContext().getRequestDispatcher("/Principal");
      rd.forward(request, response);
   }
   
   ArrayList<Usuario> lista = (ArrayList<Usuario>)request.getAttribute("lista");
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
    <div class="container" id="usuarios">
        <div class="header">
            <h1>Usuários</h1> <img src="images/logo-horizontal.png" alt="Logo SiMarket"> </div>
        <div class="section">
            <div class="buttonsTop">
                <input id="search" type="search" placeholder="Pesquisar por ID, Nome Completo ou Permissões..." maxlength="100" onsearch="filtroUsuarios()">
                <button class="azulButton rightButton" type="button" onclick="filtroUsuarios()"> <i class="fa fa-search" aria-hidden="true"></i> </button>
            </div>
            <form method="post">
                <div class="table">
                    <table id="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nome Completo</th>
                                <th>Permissões</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if(lista.isEmpty()){ %>
                            <tr>
                                <td colspan="3">Não há usuários cadastrados.</td>
                            </tr>
                            <% ;} else { for(Usuario u : lista) { %>
                            <tr <% if(u.getPapel().equalsIgnoreCase("Tela principal.")){ %>class="cancelada"<% ;} %>>
                                <td><input type="radio" name="id" required value="<%=u.getId() %>"><%=u.getId() %></td>
                                <td><%=u.getNome() %></td>
                                <td><%=u.getPapel() %></td>
                            </tr>
                            <% ;} } %>
                        </tbody>
                    </table>
                </div>
                <button class="azulButton" type="button" onclick="location.href='NovoUsuario'"  name="novo"> <i class="fa fa-plus-square-o" aria-hidden="true"></i> Incluir </button>
                <% if(!lista.isEmpty()){ %>
                <button class="azulButton" formaction="EditarUsuario" type="submit" name="editar"> <i class="fa fa-pencil-square-o" aria-hidden="true"></i> Editar </button>
                <button class="vermelhoButton" formaction="ExcluirUsuario" type="submit" name="excluir"> <i class="fa fa-minus-square-o" aria-hidden="true"></i> Excluir </button>
                <% ;} %>
            </form>
            <div class="buttonsBot">
                <button class="cinzaButton rightButton" type="button" onclick="location.href='Principal'">Fechar</button>
            </div>
        </div>
    </div>
</body>

</html>