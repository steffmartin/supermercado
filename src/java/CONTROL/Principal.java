/*
 * Trabalho de Programacao Orientada a Objetos 2
 * Grupo:
 * 11511BSI267 - Heitor H. Nunes
 * 11411BSI207 - Matheus Eduardo da S. Ramos
 * 11511BSI257 - Pedro Henrique da Silva
 * 11511BSI215 - Steffan M.  Alves
 */
package CONTROL;

import JDBC.DataBaseConnect;
import MODEL.BEAM.AlertaException;
import MODEL.BEAM.Central;
import MODEL.BEAM.Dinheiro;
import MODEL.DAO.CentralDAO;
import MODEL.DAO.DataBaseDAO;
import MODEL.DAO.UsuarioDAO;
import java.io.IOException;
import java.sql.Connection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author steff
 */
public class Principal extends HttpServlet {

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

      //Abre página 
      String opcao = request.getRequestURI();
      if(opcao.contains("Inicializador"))
         opcao = "Inicializador";
      if(opcao.contains("Principal"))
         opcao = "Principal";
      else
         opcao = "";

      switch(opcao)
      {
         case "":
         case "Inicializador":
         {
            //Ao inicializar o sistema, a primeira coisa a fazer é tentar se conectar ao banco de dados
            //E verificar se as tabelas estão criadas
            Connection c = DataBaseConnect.getConexao();
            DataBaseDAO d = new DataBaseDAO();
            if (!d.existeTabelas())
                c = null;
             
            //Conseguindo, irá carregar a central e direcionar para a tela de login
            if(c != null)
            {
               RequestDispatcher rd;
               UsuarioDAO ud = new UsuarioDAO();

               if(ud.getLista().isEmpty())
                  throw new AlertaException("PrimeiroUsuario", "Continuar",
                          "<p>Identificamos que não há nenhum usuário cadastrado!<br><br>"
                          + "Provavelmente a instalação do sistema foi interrompida anteriormente, e o usuário inicial não foi criado. Sem um usuário não há como utilizar o sistema.<br><br>"
                          + "Clique em continuar para criar o primeiro usuário do sistema.</p>");
               else
               {
                  CentralDAO cd = new CentralDAO();
                  Central central = cd.getCentral();
                  request.setAttribute("central", central);
                  rd = getServletContext().getRequestDispatcher("/login.jsp");
               }
               rd.forward(request, response);
            }
            else
            {
               c = DataBaseConnect.getConexao();
               if(c != null)
               {
                  //perguntar ao usuário se pode ou não criar a base de dados
                  request.setAttribute("acao", "CriarBD");
                  throw new AlertaException("CriarBD", "Criar BD",
                          "<p>Não foi possível localizar a base de dados, mas uma conexão com o servidor foi encontrada.<br><br>Clique no botão abaixo para criar a base de dados e efetuar as configurações iniciais do sistema.</p>");
               }
               else
                  throw new AlertaException("Inicializador", "Repetir",
                          "<p>Não foi possível estabelecer uma conexão com o servidor PostgreSQL, verifique se ele está instalado e configurado e tente novamente.</p>");
            }
            break;
         }
         case "Principal":
         {
            Central c = Central.getInstancia();
            float cx = Dinheiro.converteFloat(c.getCaixa());

            request.setAttribute("central", c);
            request.setAttribute("caixa", cx);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/principal.jsp");
            rd.forward(request, response);
            break;
         }
      }
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
