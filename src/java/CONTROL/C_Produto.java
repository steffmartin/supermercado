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
import MODEL.BEAM.SucessoException;
import MODEL.DAO.CentralDAO;
import MODEL.DAO.ProdutoDAO;
import java.io.IOException;
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
public class C_Produto extends HttpServlet {

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

      ProdutoDAO d = new ProdutoDAO();
      CentralDAO cd = new CentralDAO();
      Central c = cd.getCentral();

      String opcao = request.getRequestURI();
      if(opcao.contains("Produtos"))
         opcao = "Produtos";
      else if(opcao.contains("NovoProduto"))
         opcao = "NovoProduto";
      else if(opcao.contains("EditarProduto"))
         opcao = "EditarProduto";
      else if(opcao.contains("ExcluirProduto"))
         opcao = "ExcluirProduto";

      switch(opcao)
      {
         case "Produtos":
         {
            ArrayList<Produto> lista = d.getLista();
            request.setAttribute("lista", lista);
            request.setAttribute("central", c);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/produtos.jsp");
            rd.forward(request, response);
            break;
         }
         case "NovoProduto":
         {
            if(request.getParameter("incluir") == null)
            {
               request.setAttribute("central", c);
               RequestDispatcher rd = getServletContext().getRequestDispatcher("/novo_produto.jsp");
               rd.forward(request, response);
            }
            else
            {
               Produto p = criaProduto(request, response);
               if(d.cadastraProduto(p))
               {
                  cd.atualizaEstoque_Baixo(c);
                  throw new SucessoException("Produtos", "Voltar",
                          "<p>Produto incluído com sucesso!</p>");
               }
               else
                  throw new AlertaException("NovoProduto", "Repetir",
                          "<p>Este código de barras já está sendo utilizado, por favor escolha outro.</p>");
            }
            break;
         }
         case "EditarProduto":
         {
            if(request.getParameter("salvar") == null)
            {
               String id_s = request.getParameter("id");
               if(id_s == null)
                  throw new AlertaException("Produtos", "Voltar", "<p>Escolha um produto para editar.</p>");

               int id = Integer.parseInt(id_s);
               Produto p = d.getProduto(id);
               request.setAttribute("produto", p);
               request.setAttribute("central", c);
               RequestDispatcher rd = getServletContext().getRequestDispatcher("/alterar_produto.jsp");
               rd.forward(request, response);
            }
            else
            {
               int id = Integer.parseInt(request.getParameter("id"));
               Produto p = d.getProduto(id);

               String descricao = request.getParameter("descricao");
               String cod_barras = request.getParameter("cod_barras");
               String un_medida = request.getParameter("un_medida");
               float preco_compra = Float.parseFloat(request.getParameter("preco_compra"));
               float preco_venda = Float.parseFloat(request.getParameter("preco_venda"));
               float qtd_disponivel = Float.parseFloat(request.getParameter("qtd_disponivel"));
               float qtd_aviso = Float.parseFloat(request.getParameter("qtd_aviso"));
               boolean notificar = false;
               if(request.getParameter("notificar") != null)
                  notificar = true;

               p.setDescricao(descricao);
               p.setCod_barras(cod_barras);
               p.setUn_medida(un_medida);
               p.setPreco_venda(preco_venda);
               p.setPreco_compra(preco_compra);
               p.setQtd_disponivel(qtd_disponivel);
               p.setQtd_aviso(qtd_aviso);
               p.setNotificar(notificar);

               if(d.alteraProduto(p))
               {
                  cd.atualizaEstoque_Baixo(c);
                  throw new SucessoException("Produtos", "Fechar",
                          "<p>Produto alterado com sucesso!</p>");
               }
               else
               {
                  c = cd.getCentral();
                  throw new AlertaException("Produtos", "Voltar",
                          "<p>A alteração não foi realizada, este código de barras já está sendo utilizado.</p>");
               }
            }
            break;
         }
         case "ExcluirProduto":
         {
            String id_s = request.getParameter("id");

            if(id_s == null)
               throw new AlertaException("Produtos", "Voltar", "<p>Escolha um produto para excluir.</p>");

            int id = Integer.parseInt(id_s);
            Produto p = d.getProduto(id);
            if(d.excluiProduto(p))
            {
               p.setNotificar(false);//Removerá ele da lista de notificação, caso esteja constando
               throw new SucessoException("Produtos", "Voltar",
                       "<p>Produto excluído com sucesso!</p>");
            }
            else
               throw new AlertaException("Produtos", "Voltar",
                       "<p>Este produto não pode ser excluído pois já está registrado em uma venda.</p>");
            //break;
         }
         default:
            throw new AlertaException("Inicializador", "Voltar", "<p>Você não deveria estar aqui.</p>");
      }
   }

   private Produto criaProduto(HttpServletRequest request, HttpServletResponse response) {

      String descricao = request.getParameter("descricao");
      String cod_barras = request.getParameter("cod_barras");
      String un_medida = request.getParameter("un_medida");
      float preco_compra = Float.parseFloat(request.getParameter("preco_compra"));
      float preco_venda = Float.parseFloat(request.getParameter("preco_venda"));
      float qtd_disponivel = Float.parseFloat(request.getParameter("qtd_disponivel"));
      float qtd_aviso = Float.parseFloat(request.getParameter("qtd_aviso"));
      boolean notificar = false;
      if(request.getParameter("notificar") != null)
         notificar = true;

      Produto p = new Produto(qtd_disponivel, qtd_aviso, cod_barras, descricao, un_medida, preco_venda, preco_compra, notificar);
      return p;
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
