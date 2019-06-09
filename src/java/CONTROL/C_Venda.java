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
import MODEL.BEAM.Dinheiro;
import MODEL.BEAM.FabricaDeDinheiro;
import MODEL.BEAM.Produto;
import MODEL.BEAM.SucessoException;
import MODEL.BEAM.Usuario;
import MODEL.BEAM.Venda;
import MODEL.DAO.CentralDAO;
import MODEL.DAO.ProdutoDAO;
import MODEL.DAO.UsuarioDAO;
import MODEL.DAO.VendaDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author steff
 */
public class C_Venda extends HttpServlet {

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
      VendaDAO d = new VendaDAO();
      ProdutoDAO pd = new ProdutoDAO();
      Central c = cd.getCentral();
      Venda v = c.getVenda_ativa();

      String opcao = request.getRequestURI();
      if(opcao.contains("NovaVenda"))
         opcao = "NovaVenda";
      else if(opcao.contains("ContinuarVenda"))
         opcao = "ContinuarVenda";
      else if(opcao.contains("VendaRemover"))
         opcao = "VendaRemover";
      else if(opcao.contains("VendaAdicionar"))
         opcao = "VendaAdicionar";
      else if(opcao.contains("Vendas"))
         opcao = "Vendas";
      else if(opcao.contains("VerVenda"))
         opcao = "VerVenda";
      else if(opcao.contains("VendaFormaPagamento"))
         opcao = "VendaFormaPagamento";
      else if(opcao.contains("DescontoVenda"))
         opcao = "DescontoVenda";
      else if(opcao.contains("FinalizarVenda"))
         opcao = "FinalizarVenda";
      else if(opcao.contains("FinalizarEmDinheiro"))
         opcao = "FinalizarEmDinheiro";
      else if(opcao.contains("FinalizarEmCheque"))
         opcao = "FinalizarEmCheque";
      else if(opcao.contains("ConfirmarTroco"))
         opcao = "ConfirmarTroco";
      else if(opcao.contains("VoltarAoDeposito"))
         opcao = "VoltarAoDeposito";
      else if(opcao.contains("CancelarVenda"))
         opcao = "CancelarVenda";

      switch(opcao)
      {
         case "Vendas":
         {
            request.setAttribute("central", c);
            ArrayList<Venda> lista = d.getListaCabecalhoVenda();
            request.setAttribute("lista", lista);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/vendas.jsp");
            rd.forward(request, response);
            break;
         }
         case "NovaVenda":
         {
            if(v != null)
               throw new AlertaException("ContinuarVenda", "Continuar",
                       "<p>Já há uma venda em aberto, você precisa continuá-la.</p>");

            if(c.getLogado() != null && c.getLogado().temPapel("P_EfetuarVenda"))
               c.setVenda_ativa(new Venda(d.nextID(), c.getLogado().getNome()));

            request.setAttribute("central", c);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/venda.jsp");
            rd.forward(request, response);
            break;
         }
         case "ContinuarVenda":
         {
            request.setAttribute("central", c);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/venda.jsp");
            rd.forward(request, response);
            break;
         }
         case "VendaAdicionar":
         {
            String cod_barras = request.getParameter("cod_barras");
            float quantidade = Float.parseFloat(request.getParameter("qtd"));
            Produto p = pd.getProduto(cod_barras);

            if(p == null)
               throw new AlertaException("ContinuarVenda", "Continuar",
                       "<p>Nenhum produto foi encontrado com este código de barras.</p>");

            if(!v.insereProduto(p, quantidade))
               throw new AlertaException("ContinuarVenda", "Continuar",
                       "<p>Não há saldo em estoque suficiente para realizar esta operação.</p>");
            else
            {
               v.setValor_recebido(0);
               v.setTroco(0);
               v.setForma_pagamento("Não definido");
               pd.alteraProduto(p);//Atualiza quantidade no banco de dados
               RequestDispatcher rd = getServletContext().getRequestDispatcher("/ContinuarVenda");
               rd.forward(request, response);
            }
            break;
         }
         case "VendaRemover":
         {
            String cod_barras = request.getParameter("cod_barras");
            float quantidade = Float.parseFloat(request.getParameter("qtd"));
            Produto p = pd.getProduto(cod_barras);

            if(p == null)
               throw new AlertaException("ContinuarVenda", "Continuar",
                       "<p>Nenhum produto foi encontrado com este código de barras.</p>");

            if(!v.removeProduto(p, quantidade))
               throw new AlertaException("ContinuarVenda", "Continuar",
                       "<p>Este produto já foi removido ou não foi registrado na venda atual.</p>");
            else
            {
               v.setValor_recebido(0);
               v.setTroco(0);
               v.setForma_pagamento("Não definido");
               pd.alteraProduto(p);//Atualiza quantidade no banco de dados
               RequestDispatcher rd = getServletContext().getRequestDispatcher("/ContinuarVenda");
               rd.forward(request, response);
            }
            break;
         }
         case "DescontoVenda":
         {
            float desconto = Float.parseFloat(request.getParameter("desconto"));

            if(!c.getLogado().temPapel("P_ConcederDesconto"))
            {
               String usuario = request.getParameter("usuario");
               String senha = request.getParameter("senha");
               UsuarioDAO ud = new UsuarioDAO();
               Usuario supervisor = ud.logarUsuario(usuario, senha);

               if(supervisor == null)
                  throw new AlertaException("ContinuarVenda", "Continuar",
                          "<p>Usuário ou senha inválido, desconto não concedido.</p>");
               else if(!supervisor.temPapel("P_ConcederDesconto"))
                  throw new AlertaException("ContinuarVenda", "Continuar",
                          "<p>Usuário sem permissão para conceder descontos, desconto não concedido.</p>");
            }
            v.concedeDesconto(desconto);
            v.setValor_recebido(0);
            v.setTroco(0);
            v.setForma_pagamento("Não definido");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/ContinuarVenda");
            rd.forward(request, response);
            break;
         }
         case "VendaFormaPagamento":
         {
            String forma = request.getParameter("forma");

            float valor;
            if(forma.equalsIgnoreCase("Dinheiro") || forma.equalsIgnoreCase("Cheque"))
               valor = Float.parseFloat(request.getParameter("numero"));
            else
            {
               valor = Float.parseFloat(String.format(Locale.US, "%.2f", v.getTotal_liquido()));
               if(request.getParameter("numero").equalsIgnoreCase("1"))
                  forma += " (à vista)";
               else
               {
                  v.setParcelas(Integer.parseInt(request.getParameter("numero")));
                  forma += " (" + v.getParcelas() + " vezes)";
               }
            }
            v.setForma_pagamento(forma);
            v.setTotal_liquido(Float.parseFloat(String.format(Locale.US, "%.2f", v.getTotal_liquido())));
            v.setValor_recebido(valor);
            v.setTroco(Float.parseFloat(String.format(Locale.US, "%.2f", v.getValor_recebido() - v.getTotal_liquido())));
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/ContinuarVenda");
            rd.forward(request, response);
            break;
         }
         case "FinalizarVenda":
         {
            if(v.getForma_pagamento().equalsIgnoreCase("Não definido"))
               throw new AlertaException("ContinuarVenda", "Continuar",
                       "<p>Defina a forma de pagamento antes de finalizar a venda.</p>");

            if(v.getValor_recebido() >= v.getTotal_liquido())
               salvarVenda();

            break;
         }
         case "FinalizarEmCheque":
         {
            if(v.getValor_recebido() < v.getTotal_liquido())
               throw new AlertaException("ContinuarVenda", "Continuar",
                       "<p>A venda não pode ser finalizada ainda, pois o valor do cheque é inferior ao total da venda.</p>");

            if(v.getValor_recebido() == v.getTotal_liquido())
               salvarVenda();
            else
            {
               int[] saque;
               float quebra;

               FabricaDeDinheiro f = new FabricaDeDinheiro();
               Dinheiro din = f.criaDinheiro(c.getCaixa());

               if(din != null)
               {
                  saque = din.dicaTroco(v.getTroco());
                  quebra = Float.parseFloat(String.format(Locale.US, "%.2f", v.getTroco() - Dinheiro.converteFloat(saque)));
               }
               else
               {
                  saque = new int[]
                  {
                     0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                  };
                  quebra = v.getTroco();
               }

               c.setArraytroco(saque);
               v.setQuebra_de_caixa(quebra);

               request.setAttribute("quebra", quebra);
               request.setAttribute("saque", saque);
               request.setAttribute("troco", v.getTroco());
               request.setAttribute("central", c);
               RequestDispatcher rd = getServletContext().getRequestDispatcher("/troco.jsp");
               rd.forward(request, response);
            }
            break;
         }
         case "FinalizarEmDinheiro":
         {
            if(request.getParameter("confirmardeposito") == null)
            {
               if(v.getValor_recebido() < v.getTotal_liquido())
                  throw new AlertaException("ContinuarVenda", "Continuar",
                          "<p>A venda não pode ser finalizada ainda, pois o valor recebido é inferior ao total da venda.</p>");

               request.setAttribute("central", c);
               request.setAttribute("recebido", v.getValor_recebido());
               RequestDispatcher rd = getServletContext().getRequestDispatcher("/deposito.jsp");
               rd.forward(request, response);
            }
            else
            {
               int[] deposito = new int[]
               {
                  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
               };
               deposito[0] = Integer.parseInt(request.getParameter("ncem"));
               deposito[1] = Integer.parseInt(request.getParameter("ncinquenta"));
               deposito[2] = Integer.parseInt(request.getParameter("nvinte"));
               deposito[3] = Integer.parseInt(request.getParameter("ndez"));
               deposito[4] = Integer.parseInt(request.getParameter("ncinco"));
               deposito[5] = Integer.parseInt(request.getParameter("ndois"));
               deposito[6] = Integer.parseInt(request.getParameter("mum"));
               deposito[7] = Integer.parseInt(request.getParameter("mcinquenta"));
               deposito[8] = Integer.parseInt(request.getParameter("mvintecinco"));
               deposito[9] = Integer.parseInt(request.getParameter("mdez"));
               deposito[10] = Integer.parseInt(request.getParameter("mcinco"));
               deposito[11] = Integer.parseInt(request.getParameter("mzeroum"));
               c.depositarCaixa(deposito);

               FabricaDeDinheiro f = new FabricaDeDinheiro();
               Dinheiro din = f.criaDinheiro(c.getCaixa());

               if(din == null || Dinheiro.converteFloat(deposito) != v.getValor_recebido())
               {
                  c.sacarCaixa(deposito);
                  throw new AlertaException("FinalizarEmDinheiro", "Voltar",
                          "<p>A quantidade informada não é igual ao valor recebido, verifique.</p>");
               }
               else
               {
                  c.setArrayrecebido(deposito);

                  int[] saque;
                  float quebra;

                  saque = din.dicaTroco(v.getTroco());
                  quebra = Float.parseFloat(String.format(Locale.US, "%.2f", v.getTroco() - Dinheiro.converteFloat(saque)));

                  c.setArraytroco(saque);
                  v.setQuebra_de_caixa(quebra);

                  request.setAttribute("quebra", quebra);
                  request.setAttribute("saque", saque);
                  request.setAttribute("troco", v.getTroco());
                  request.setAttribute("central", c);
                  request.setAttribute("emdinheiro", true);
                  RequestDispatcher rd = getServletContext().getRequestDispatcher("/troco.jsp");
                  rd.forward(request, response);
               }
            }
            break;
         }
         case "VoltarAoDeposito":
         {
            c.sacarCaixa(c.getArrayrecebido());
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/FinalizarEmDinheiro");
            rd.forward(request, response);
            break;
         }
         case "ConfirmarTroco":
         {
            c.sacarCaixa(c.getArraytroco());
            salvarVenda();
            break;
         }
         case "CancelarVenda":
         {
            ArrayList<Object[]> listatemp = new ArrayList<>(v.getLista());

            v.cancelaVenda();

            for(Object[] o : listatemp)
            {
               Produto p = ((Produto) o[0]);
               //float qtd = ((float) o[1]);
               //p.setQtd_disponivel(p.getQtd_disponivel() + qtd);
               pd.alteraProduto(p);
            }

            salvarVenda();

            break;
         }
         case "VerVenda":
         {
            String id_s = request.getParameter("id");
            if(id_s == null)
               throw new AlertaException("Vendas", "Voltar", "<p>Escolha uma venda para visualizar.</p>");

            int id = Integer.parseInt(id_s);
            Venda ver = d.getVenda(id);
            request.setAttribute("venda", ver);
            request.setAttribute("central", c);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/ver_venda.jsp");
            rd.forward(request, response);
            break;
         }
         default:
            throw new AlertaException("Inicializador", "Voltar", "<p>Você não deveria estar aqui.</p>");
      }
   }

   private void salvarVenda() {

      CentralDAO cd = new CentralDAO();
      VendaDAO d = new VendaDAO();
      Central c = cd.getCentral();
      Venda v = c.getVenda_ativa();

      v.fechaVenda();

      if(d.insereVenda(v))
      {
         c.setVenda_ativa(null);
         if(v.getForma_pagamento().equalsIgnoreCase("Cancelada"))
            throw new SucessoException("Principal", "Fechar",
                    "<p>Venda cancelada com sucesso.</p>");
         else
            throw new SucessoException("Principal", "Fechar",
                    "<p>Venda finalizada com sucesso.</p>");
      }
      else
         throw new AlertaException("ContinuarVenda", "Continuar",
                 "<p>Houve um erro inesperado ao finalizar a venda, tente novamente.</p>");
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
