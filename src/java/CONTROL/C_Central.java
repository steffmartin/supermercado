/*
 * Trabalho de Programacao Orientada a Objetos 2
 * Grupo:
 * 11511BSI267 - Heitor H. Nunes
 * 11411BSI207 - Matheus Eduardo da S. Ramos
 * 11511BSI257 - Pedro Henrique da Silva
 * 11511BSI215 - Steffan M.  Alves
 */
package CONTROL;

import MODEL.BEAM.AlertaException;
import MODEL.BEAM.Central;
import MODEL.BEAM.SucessoException;
import MODEL.DAO.CentralDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author steff
 */
public class C_Central extends HttpServlet {

   /**
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
    * methods.
    *
    * @param request  servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException      if an I/O error occurs
    */
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
      request.setCharacterEncoding("UTF-8");

      CentralDAO cd = new CentralDAO();
      Central c = cd.getCentral();

      String opcao = request.getRequestURI();
      if(opcao.contains("CriarEmpresa"))
         opcao = "CriarEmpresa";
      else if(opcao.contains("ConfigurarEmpresa"))
         opcao = "ConfigurarEmpresa";
      else if(opcao.contains("ConfigurarCaixa"))
         opcao = "ConfigurarCaixa";

      switch(opcao)
      {
         case "CriarEmpresa":
         {
            if(request.getParameter("salvar") == null)//Se não tem este parâmetro no request é porque o acesso veio da tela de confirmação do 'Usuário inicial criado'
               if(c.getEmpresa() == null)
               {
                  request.setAttribute("central", c);
                  request.setAttribute("acao", "CriarEmpresa");
                  RequestDispatcher rd = getServletContext().getRequestDispatcher("/configuracoes.jsp");
                  rd.forward(request, response);
               }
               else
                  throw new AlertaException("Inicializador", "Voltar",
                          "<p>A configuração inicial da empresa já foi feita!</p>");
            else
            {
               setarCentral(c, request, response);
               if(cd.cadastraCentral(c))
                  throw new SucessoException("Inicializador", "Continuar",
                          "<p>A empresa foi configurada com sucesso!<br><br>"
                          + "Clique em continuar para começar a utilizar o sistema.</p>");
               else
                  throw new AlertaException("CriarEmpresa", "Repetir",
                          "<p>Houve um erro ao configurar a empresa, tente novamente. Caso o problema persista, entre em contato com o suporte.</p>");
            }
            break;
         }
         case "ConfigurarEmpresa":
         {
            if(request.getParameter("salvar") == null)
            {
               request.setAttribute("central", c);
               RequestDispatcher rd = getServletContext().getRequestDispatcher("/configuracoes.jsp");
               rd.forward(request, response);
            }
            else
            {
               setarCentral(c, request, response);
               cd.alteraCentral(c);
               throw new SucessoException("Principal", "Fechar", "<p>Configurações atualizadas com sucesso!</p>");
            }
            break;
         }
         case "ConfigurarCaixa":
         {
            if(request.getParameter("salvar")==null){
               request.setAttribute("central", c);
               RequestDispatcher rd = getServletContext().getRequestDispatcher("/configcaixa.jsp");
               rd.forward(request, response);
            }
            else
            {
               int[] cx = new int[12];
               cx[0] = Integer.parseInt(request.getParameter("ncem"));
               cx[1] = Integer.parseInt(request.getParameter("ncinquenta"));
               cx[2] = Integer.parseInt(request.getParameter("nvinte"));
               cx[3] = Integer.parseInt(request.getParameter("ndez"));
               cx[4] = Integer.parseInt(request.getParameter("ncinco"));
               cx[5] = Integer.parseInt(request.getParameter("ndois"));
               cx[6] = Integer.parseInt(request.getParameter("mum"));
               cx[7] = Integer.parseInt(request.getParameter("mcinquenta"));
               cx[8] = Integer.parseInt(request.getParameter("mvintecinco"));
               cx[9] = Integer.parseInt(request.getParameter("mdez"));
               cx[10] = Integer.parseInt(request.getParameter("mcinco"));
               cx[11] = Integer.parseInt(request.getParameter("mzeroum"));
               c.setCaixa(cx);
               throw new SucessoException("Principal", "Fechar", "<p>Configurações atualizadas com sucesso!</p>");
            }
            break;
         }
         default:
            throw new AlertaException("Inicializador", "Voltar", "<p>Você não deveria estar aqui.</p>");
      }

   }

   private Central setarCentral(Central c, HttpServletRequest request, HttpServletResponse response) {

      String empresa = request.getParameter("empresa");
      String endereco = request.getParameter("endereco");
      String cidade = request.getParameter("cidade");
      String estado = request.getParameter("estado");
      String cep = request.getParameter("cep");
      String cnpj = request.getParameter("cnpj");
      String ie = request.getParameter("ie");
      String im = request.getParameter("im");

      c.setEmpresa(empresa);
      c.setEndereco(endereco);
      c.setCidade(cidade);
      c.setEstado(estado);
      c.setCep(cep);
      c.setCnpj(cnpj);
      c.setIe(ie);
      c.setIm(im);

      return c;
   }

   // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
   /**
    * Handles the HTTP <code>GET</code> method.
    *
    * @param request  servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException      if an I/O error occurs
    */
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
      processRequest(request, response);
   }

   /**
    * Handles the HTTP <code>POST</code> method.
    *
    * @param request  servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException      if an I/O error occurs
    */
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
      processRequest(request, response);
   }

   /**
    * Returns a short description of the servlet.
    *
    * @return a String containing servlet description
    */
   @Override
   public String getServletInfo() {
      return "Short description";
   }// </editor-fold>

}
