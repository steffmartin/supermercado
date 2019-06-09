<%-- 
    Document   : primeiro_usuario
    Created on : 08/11/2016, 21:12:05
    Author     : steff
--%>
<%@page import="MODEL.BEAM.Central"%>
<% //Tela de criação do primeiro usuário. Nela o checkbox de Gerenciar Usuários é checked e disabled, pois o primeiro usuário PRECISA ter estes acessos %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 
   String acao = (String)request.getAttribute("acao");

   //Proteger página de usuários não logados ou sem acesso
   Central c = (Central)request.getAttribute("central");
   if(c==null || c.getLogado() == null )
   {
      if(!(acao != null && acao.equals("PrimeiroUsuario")))
         {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Inicializador");
            rd.forward(request, response);
         }
   }
   else if(!c.getLogado().temPapel("P_GerenciarUsuario"))
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
        <div class="container" id="novoUsuario">
            <div class="header">
                <h1>Novo Usuário</h1> <img src="images/logo-horizontal.png" alt="Logo SiMarket"> </div>
            <div class="section">
                <form action="<% if(acao != null && acao.equals("PrimeiroUsuario")){%>PrimeiroUsuario<%;} else {%>NovoUsuario<%;}%>" method="post">
                    <input type="text" name="nome" placeholder="Nome Completo" maxlength="100" pattern="[^']+" title="Não pode conter ( ' )." required> <i class="fa fa-font" aria-hidden="true"></i>
                    <input type="text" name="usuario" placeholder="Usuário" maxlength="20" pattern="[^']+" title="Não pode conter ( ' )." required> <i class="fa fa-user" aria-hidden="true"></i>
                    <input type="password" name="senha" placeholder="Senha" maxlength="10" pattern="[^']+" title="Não pode conter ( ' )." required> <i class="fa fa-lock" aria-hidden="true"></i>
                    <fieldset>
                        <legend>Permissões: </legend>
                        <ul>
                            <li>
                                <input type="checkbox" name="P_ConcederDesconto">Conceder descontos</li>
                            <li>
                                <input type="checkbox" name="P_ConfigurarSistema" <% if(acao != null && acao.equals("PrimeiroUsuario")){%>checked onclick="this.checked=true"<%;}%>>Configurar sistema</li>
                            <li>
                                <input type="checkbox" name="P_EfetuarVenda">Efetuar vendas</li>
                            <li>
                                <input type="checkbox" name="P_GerenciarProduto">Gerenciar produtos</li>
                            <li>
                                <input type="checkbox" name="P_GerenciarUsuario" <% if(acao != null && acao.equals("PrimeiroUsuario")){%>checked onclick="this.checked=true"<%;}%>>Gerenciar usuários</li>
                            <li>
                                <input type="checkbox" name="P_VisualizarRelatorio">Visualizar relatórios</li>
                        </ul>
                    </fieldset>
                    <div class="buttonsBot">
                        <button class="azulButton leftButton" type="submit" name="incluir">Salvar</button>
                        <% if(acao == null || !acao.equals("PrimeiroUsuario")){%><button class="cinzaButton rightButton" type="button" onclick="location.href='Usuarios'" name="voltar">Cancelar</button><%;}%>
                    </div>
                </form>
            </div>
        </div>
    </body>

</html>
