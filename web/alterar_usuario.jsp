<%-- 
    Document   : altera_usuario
    Created on : 12/11/2016, 18:39:25
    Author     : steff
--%>

<%@page import="MODEL.BEAM.Usuario"%>
<%@page import="MODEL.BEAM.Central"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 

   //Proteger página de usuários não logados ou sem acesso
      Central c = (Central) request.getAttribute("central");
      if(c == null || c.getLogado() == null)
      {
         RequestDispatcher rd = getServletContext().getRequestDispatcher("/Inicializador");
         rd.forward(request, response);
      }
      else if(!c.getLogado().temPapel("P_GerenciarUsuario"))
      {
         RequestDispatcher rd = getServletContext().getRequestDispatcher("/Principal");
         rd.forward(request, response);
      }
      
    Usuario u = (Usuario) request.getAttribute("usuario");
    
%>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="UTF-8">
        <title>SiMarket</title>
        <link rel="stylesheet" href="css/season.css">
        <link rel="stylesheet" href="css/font-awesome.min.css"> </head>

    <body oncontextmenu="return false">
        <div class="container" id="novoUsuario">
            <div class="header">
                <h1>Editar Usuário</h1> <img src="images/logo-horizontal.png" alt="Logo SiMarket"> </div>
            <div class="section">
                <form action="EditarUsuario" method="post">
                    <input type="text" name="nome" placeholder="Nome Completo" maxlength="100" pattern="[^']+" title="Não pode conter ( ' )." required value="<%=u.getNome() %>" > <i class="fa fa-font" aria-hidden="true"></i>
                    <input type="text" name="usuario" placeholder="Usuário" maxlength="20" pattern="[^']+" title="Não pode conter ( ' )." required value="<%=u.getUsuario() %>"> <i class="fa fa-user" aria-hidden="true"></i>
                    <input type="password" name="senha" placeholder="Senha" maxlength="10" pattern="[^']+" title="Não pode conter ( ' )." required value="<%=u.getSenha() %>"> <i class="fa fa-lock" aria-hidden="true"></i>
                    <fieldset>
                        <legend>Permissões: </legend>
                        <ul>
                            <li>
                                <input type="checkbox" name="P_ConcederDesconto" <% if(u.temPapel("P_ConcederDesconto")){ %>checked<% ;} %> >Conceder descontos</li>
                            <li>
                                <input type="checkbox" name="P_ConfigurarSistema" <% if(u.temPapel("P_ConfigurarSistema")){ %>checked<% ;} %> >Configurar sistema</li>
                            <li>
                                <input type="checkbox" name="P_EfetuarVenda" <% if(u.temPapel("P_EfetuarVenda")){ %>checked<% ;} %> >Efetuar vendas</li>
                            <li>
                                <input type="checkbox" name="P_GerenciarProduto" <% if(u.temPapel("P_GerenciarProduto")){ %>checked<% ;} %> >Gerenciar produtos</li>
                            <li>
                                <input type="checkbox" name="P_GerenciarUsuario" <% if(u.temPapel("P_GerenciarUsuario")){ %>checked<% ;} %> >Gerenciar usuários</li>
                            <li>
                                <input type="checkbox" name="P_VisualizarRelatorio" <% if(u.temPapel("P_VisualizarRelatorio")){ %>checked<% ;} %> >Visualizar relatórios</li>
                        </ul>
                    </fieldset>
                    <input type="radio" name="id" value="<%=u.getId() %>" class="ocultar" required checked>
                    <div class="buttonsBot">
                        <button class="azulButton leftButton" type="submit" name="salvar">Salvar</button>
                        <button class="cinzaButton rightButton" type="button" onclick="location.href='Usuarios'" name="voltar">Cancelar</button>
                    </div>
                </form>
            </div>
        </div>
    </body>

</html>

