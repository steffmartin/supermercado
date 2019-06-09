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
import MODEL.BEAM.Produto;
import MODEL.BEAM.Relatorio;
import MODEL.BEAM.RelatorioObservados;
import MODEL.BEAM.RelatorioProdutos;
import MODEL.BEAM.RelatorioUsuarios;
import MODEL.BEAM.RelatorioVendas;
import MODEL.BEAM.RelatorioZerados;
import MODEL.BEAM.Usuario;
import MODEL.BEAM.Venda;
import MODEL.DAO.CentralDAO;
import MODEL.DAO.ProdutoDAO;
import MODEL.DAO.UsuarioDAO;
import MODEL.DAO.VendaDAO;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author steff
 */
public class C_Relatorio extends HttpServlet {

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
      if(opcao.contains("Relatorios"))
         opcao = "Relatorios";
      else if(opcao.contains("VerRelatorio"))
         opcao = "VerRelatorio";

      switch(opcao)
      {
         case "Relatorios":
         {
            request.setAttribute("central", c);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/relatorios.jsp");
            rd.forward(request, response);
            break;
         }
         case "VerRelatorio":
         {

            Relatorio r = null;

            if(request.getParameter("estoque") != null && request.getParameter("opcao").equalsIgnoreCase("observados"))
            {
               ProdutoDAO pd = new ProdutoDAO();
               ArrayList<Produto> lista = new ArrayList<>();
               for(int i : c.getEstoque_baixo())
                  lista.add(pd.getProduto(i));

               r = new RelatorioObservados("Produtos observados e com estoque mínimo atingido", lista);
            }
            else if(request.getParameter("estoque") != null && request.getParameter("opcao").equalsIgnoreCase("zerados"))
            {
               ArrayList<Produto> lista = new ArrayList<>();
               ProdutoDAO pd = new ProdutoDAO();
               lista = pd.getLista();

               r = new RelatorioZerados("Produtos sem saldo em estoque", lista);
            }
            else if(request.getParameter("vendas") != null)
            {
               ArrayList<Venda> lista = new ArrayList<>();
               VendaDAO vd = new VendaDAO();
               lista = vd.getListaCabecalhoVenda();
               boolean canceladas = false;
               if(request.getParameter("canceladas") != null)
                  canceladas = true;
               
               DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
               LocalDateTime de = LocalDateTime.parse(request.getParameter("de") + " 00:00",formato);
               LocalDateTime ate = LocalDateTime.parse(request.getParameter("ate") + " 23:59",formato);

               r = new RelatorioVendas("Vendas por período", lista, de, ate, canceladas);
            }
            else if(request.getParameter("cadastro") != null && request.getParameter("tipo").equalsIgnoreCase("usuarios")){
               ArrayList<Usuario> lista = new ArrayList<>();
               UsuarioDAO ud = new UsuarioDAO();
               lista = ud.getLista();
               
               r = new RelatorioUsuarios("Cadastro de Usuários",lista);
            }
            else if (request.getParameter("cadastro") != null && request.getParameter("tipo").equalsIgnoreCase("produtos")){
               ArrayList<Produto> lista = new ArrayList<>();
               ProdutoDAO pd = new ProdutoDAO();
               lista = pd.getLista();
               
               r = new RelatorioProdutos("Cadastro de Produtos",lista);
            }
            else
               throw new AlertaException("Relatorios", "Voltar", "<p>Não há relatório disponível com estes parâmetros.</p>");

            request.setAttribute("relatorio", r);
            request.setAttribute("central", c);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/ver_relatorio.jsp");
            rd.forward(request, response);
            break;
         }
         default:
            throw new AlertaException("Inicializador", "Voltar", "<p>Você não deveria estar aqui.</p>");
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
