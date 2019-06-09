<%-- 
    Document   : configuracoes
    Created on : 11/11/2016, 18:43:24
    Author     : steff
--%>

<%@page import="MODEL.BEAM.Central"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
   //Proteger página de usuários não logados ou sem acesso
   Central c = (Central)request.getAttribute("central");
   if(c == null || c.getLogado() == null)
   {
      RequestDispatcher rd = getServletContext().getRequestDispatcher("/Inicializador");
      rd.forward(request, response);
   }
   else if(!c.getLogado().temPapel("P_ConfigurarSistema"))
   {
      RequestDispatcher rd = getServletContext().getRequestDispatcher("/Principal");
      rd.forward(request, response);
   }
   String acao = (String) request.getAttribute("acao");
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>SiMarket</title>
    <link rel="stylesheet" href="css/season.css">
    <link rel="stylesheet" href="css/font-awesome.min.css"> </head>

<body oncontextmenu="return false">
    <div class="container" id="configEmpresa">
        <div class="header">
            <h1>Configurações</h1> <img src="images/logo-horizontal.png" alt="Logo SiMarket"> </div>
        <div class="section">
            <form action="<% if(acao != null && acao.equals("CriarEmpresa")){%>CriarEmpresa<%;} else {%>ConfigurarEmpresa<%;}%>" method="post">
                <input type="text" name="empresa" placeholder="Razão Social" maxlength="100" pattern="[^']+" title="Não pode conter ( ' )." required <% if(c.getEmpresa()!=null){ %> value="<%=c.getEmpresa() %>" <% ;} %>  > <i class="fa fa-building" aria-hidden="true" ></i>
                <input type="text" name="endereco" placeholder="Endereço Completo" maxlength="200" pattern="[^']+" title="Não pode conter ( ' )." required <% if(c.getEndereco()!=null){ %> value="<%=c.getEndereco() %>" <% ;} %> > <i class="fa fa-map-marker" aria-hidden="true"></i>
                <input type="text" name="cidade" placeholder="Cidade" maxlength="50" pattern="[^']+" title="Não pode conter ( ' )." required <% if(c.getCidade()!=null){ %> value="<%=c.getCidade() %>" <% ;} %> > <i class="fa fa-map-marker" aria-hidden="true"></i>
                <select name="estado" required>
                    <option value="">Estado</option>
                    <option value="AC" <% if(c.getEstado()!=null && c.getEstado().equalsIgnoreCase("AC")){ %> selected <% ;} %> >Acre</option>
                    <option value="AL" <% if(c.getEstado()!=null && c.getEstado().equalsIgnoreCase("AL")){ %> selected <% ;} %> >Alagoas</option>
                    <option value="AP" <% if(c.getEstado()!=null && c.getEstado().equalsIgnoreCase("AP")){ %> selected <% ;} %> >Amapá</option>
                    <option value="AM" <% if(c.getEstado()!=null && c.getEstado().equalsIgnoreCase("AM")){ %> selected <% ;} %> >Amazonas</option>
                    <option value="BA" <% if(c.getEstado()!=null && c.getEstado().equalsIgnoreCase("BA")){ %> selected <% ;} %> >Bahia</option>
                    <option value="CE" <% if(c.getEstado()!=null && c.getEstado().equalsIgnoreCase("CE")){ %> selected <% ;} %> >Ceará</option>
                    <option value="DF" <% if(c.getEstado()!=null && c.getEstado().equalsIgnoreCase("DF")){ %> selected <% ;} %> >Distrito Federal</option>
                    <option value="ES" <% if(c.getEstado()!=null && c.getEstado().equalsIgnoreCase("ES")){ %> selected <% ;} %> >Espirito Santo</option>
                    <option value="GO" <% if(c.getEstado()!=null && c.getEstado().equalsIgnoreCase("GO")){ %> selected <% ;} %> >Goiás</option>
                    <option value="MA" <% if(c.getEstado()!=null && c.getEstado().equalsIgnoreCase("MA")){ %> selected <% ;} %> >Maranhão</option>
                    <option value="MS" <% if(c.getEstado()!=null && c.getEstado().equalsIgnoreCase("MS")){ %> selected <% ;} %> >Mato Grosso do Sul</option>
                    <option value="MT" <% if(c.getEstado()!=null && c.getEstado().equalsIgnoreCase("MT")){ %> selected <% ;} %> >Mato Grosso</option>
                    <option value="MG" <% if(c.getEstado()!=null && c.getEstado().equalsIgnoreCase("MG")){ %> selected <% ;} %> >Minas Gerais</option>
                    <option value="PA" <% if(c.getEstado()!=null && c.getEstado().equalsIgnoreCase("PA")){ %> selected <% ;} %> >Pará</option>
                    <option value="PB" <% if(c.getEstado()!=null && c.getEstado().equalsIgnoreCase("PB")){ %> selected <% ;} %> >Paraíba</option>
                    <option value="PR" <% if(c.getEstado()!=null && c.getEstado().equalsIgnoreCase("PR")){ %> selected <% ;} %> >Paraná</option>
                    <option value="PE" <% if(c.getEstado()!=null && c.getEstado().equalsIgnoreCase("PE")){ %> selected <% ;} %> >Pernambuco</option>
                    <option value="PI" <% if(c.getEstado()!=null && c.getEstado().equalsIgnoreCase("PI")){ %> selected <% ;} %> >Piauí</option>
                    <option value="RJ" <% if(c.getEstado()!=null && c.getEstado().equalsIgnoreCase("RJ")){ %> selected <% ;} %> >Rio de Janeiro</option>
                    <option value="RN" <% if(c.getEstado()!=null && c.getEstado().equalsIgnoreCase("RN")){ %> selected <% ;} %> >Rio Grande do Norte</option>
                    <option value="RS" <% if(c.getEstado()!=null && c.getEstado().equalsIgnoreCase("RS")){ %> selected <% ;} %> >Rio Grande do Sul</option>
                    <option value="RO" <% if(c.getEstado()!=null && c.getEstado().equalsIgnoreCase("RO")){ %> selected <% ;} %> >Rondônia</option>
                    <option value="RR" <% if(c.getEstado()!=null && c.getEstado().equalsIgnoreCase("RR")){ %> selected <% ;} %> >Roraima</option>
                    <option value="SC" <% if(c.getEstado()!=null && c.getEstado().equalsIgnoreCase("SC")){ %> selected <% ;} %> >Santa Catarina</option>
                    <option value="SP" <% if(c.getEstado()!=null && c.getEstado().equalsIgnoreCase("SP")){ %> selected <% ;} %> >São Paulo</option>
                    <option value="SE" <% if(c.getEstado()!=null && c.getEstado().equalsIgnoreCase("SE")){ %> selected <% ;} %> >Sergipe</option>
                    <option value="TO" <% if(c.getEstado()!=null && c.getEstado().equalsIgnoreCase("TO")){ %> selected <% ;} %> >Tocantins</option>
                </select> <i class="fa fa-map-marker" aria-hidden="true"></i>
                <input type="text" name="cep" placeholder="CEP" maxlength="11" pattern="[^']+" title="Não pode conter ( ' )." required <% if(c.getCep()!=null){ %> value="<%=c.getCep() %>" <% ;} %> > <i class="fa fa-map-marker" aria-hidden="true"></i>
                <input type="text" name="cnpj" placeholder="CNPJ" maxlength="18" pattern="[^']+" title="Não pode conter ( ' )." required <% if(c.getCnpj()!=null){ %> value="<%=c.getCnpj() %>" <% ;} %> > <i class="fa fa-briefcase" aria-hidden="true"></i>
                <input type="text" name="ie" placeholder="Inscrição Estadual" maxlength="20" pattern="[^']+" title="Não pode conter ( ' )." required <% if(c.getIe()!=null){ %> value="<%=c.getIe() %>" <% ;} %> > <i class="fa fa-briefcase" aria-hidden="true"></i>
                <input type="text" name="im" placeholder="Inscrição Municipal" maxlength="20" pattern="[^']+" title="Não pode conter ( ' )." required <% if(c.getIm()!=null){ %> value="<%=c.getIm() %>" <% ;} %> > <i class="fa fa-briefcase" aria-hidden="true"></i>
                <div class="buttonsBot">
                    <button class="azulButton leftButton" type="submit" name="salvar">Salvar</button>
                    <% if(acao == null || !acao.equals("CriarEmpresa")){%><button class="cinzaButton rightButton" type="button" onclick="location.href='Principal'" name="voltar">Cancelar</button><%;}%>
                </div>
            </form>
        </div>
    </div>
</body>

</html>