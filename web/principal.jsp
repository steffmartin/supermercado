<%-- 
    Document   : principal
    Created on : 12/11/2016, 08:19:38
    Author     : steff
--%>

<%@page import="MODEL.DAO.CentralDAO"%>
<%@page import="MODEL.BEAM.Usuario"%>
<%@page import="MODEL.BEAM.Central"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
   Central c = (Central)request.getAttribute("central");
   Usuario u = c.getLogado();
   
   //Proteger página de usuários não logados
   if(c==null || u == null)
   {
      RequestDispatcher rd = getServletContext().getRequestDispatcher("/Inicializador");
      rd.forward(request, response);
   }
   String cx = String.format("%.2f", request.getAttribute("caixa"));
    
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
        <div id="principal"> <img src="images/logo.png" alt="Logo SiMarket" class="fade-in two">
            <div class="mainSection">
                <ul  class="fade-in one">
                    <% if(u.temPapel("P_EfetuarVenda")){  %>
                    <% if(c.getVenda_ativa()==null){ %>
                    <li>
                        <a href="NovaVenda" class="round ">
                            <p>Nova Venda</p><span class="round">Iniciar uma nova venda.</span></a>
                    </li>
                    <% ;} else { %>
                    <li>
                        <a href="ContinuarVenda" class="round ">
                            <p>Continuar Venda</p><span class="round">Continuar a venda em aberto.</span></a>
                    </li>
                    <% ;} %>
                    <li>
                        <a href="Vendas" class="round">
                            <p>Vendas</p><span class="round">Ver todas as vendas efetuadas.</span></a>
                    </li>
                    <% ;} %>
                    <% if(u.temPapel("P_GerenciarUsuario")){ %>
                    <li>
                        <a href="Usuarios" class="round">
                            <p>Usuários</p><span class="round">Incluir, alterar ou excluir usuários.</span></a>
                    </li>
                    <% ;} %>
                    <% if(u.temPapel("P_GerenciarProduto")){ %>
                    <li>
                        <a href="Produtos" class="round">
                            <p>Produtos</p><span class="round">Incluir, alterar ou excluir produtos.</span></a>
                    </li>
                    <% ;} %>
                    <% if(u.temPapel("P_VisualizarRelatorio")){ %>
                    <li>
                        <a href="Relatorios" class="round">
                            <p>Relatórios</p><span class="round">Visualizar todos os relatórios gerenciais.</span></a>
                    </li>
                    <% ;} %>
                    <li>
                        <a href="AlterarSenha" class="round">
                            <p>Trocar Senha</p><span class="round">Altere sua senha de acesso ao sistema.</span></a>
                    </li>
                    <% if(u.temPapel("P_EfetuarVenda")){ %>
                    <li>
                        <a href="ConfigurarCaixa" class="round">
                            <p>Config. Caixa</p><span class="round">Ajuste o saldo do caixa.<% if(u.temPapel("P_VisualizarRelatorio")){ %><br>Saldo atual: R$<%=cx %><% ;} %></span></a>
                    </li>
                    <% ;} %>
                    <% if(u.temPapel("P_ConfigurarSistema")){ %>
                    <li>
                        <a href="ConfigurarEmpresa" class="round">
                            <p>Config. Empresa</p><span class="round">Altere os dados cadastrais da empresa.</span></a>
                    </li>
                    <% ;} %>
                </ul>
            </div>
            <div class="footer fade-in two">
                <p class="bemvindo">Usuário: <%=u.getNome() %></p>
                <p class="sair"><a href="Sair"><i class="fa fa-power-off" aria-hidden="true"></i> Sair</a></p>
                <p>Copyright © 2017 SiMarket Fic. Todos os direitos reservados.</p>
                <p>By: Heitor Nunes, Rhaniel Christian, Steffan Martins.</p> <i class="fa fa-facebook-square" aria-hidden="true"></i> <i class="fa fa-twitter-square" aria-hidden="true"></i> <i class="fa fa-youtube-play" aria-hidden="true"></i> </div>
        </div>
    </body>

</html>
