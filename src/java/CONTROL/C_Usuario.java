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
import MODEL.BEAM.SucessoException;
import MODEL.BEAM.Usuario;
import MODEL.BEAM.UsuarioBasico;
import MODEL.DAO.CentralDAO;
import MODEL.DAO.UsuarioDAO;
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
public class C_Usuario extends HttpServlet {

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

      UsuarioDAO d = new UsuarioDAO();
      CentralDAO cd = new CentralDAO();
      Central c = cd.getCentral();

      String opcao = request.getRequestURI();
      if(opcao.contains("PrimeiroUsuario"))
         opcao = "PrimeiroUsuario";
      else if(opcao.contains("AtenticarUsuario"))
         opcao = "AtenticarUsuario";
      else if(opcao.contains("Usuarios"))
         opcao = "Usuarios";
      else if(opcao.contains("NovoUsuario"))
         opcao = "NovoUsuario";
      else if(opcao.contains("ExcluirUsuario"))
         opcao = "ExcluirUsuario";
      else if(opcao.contains("AlterarSenha"))
         opcao = "AlterarSenha";
      else if(opcao.contains("EditarUsuario"))
         opcao = "EditarUsuario";
      else if(opcao.contains("Sair"))
         opcao = "Sair";

      switch(opcao)
      {
         case "PrimeiroUsuario":
         {
            if(request.getParameter("incluir") == null)//Se não tem este parâmetro no request é porque o acesso veio da tela de confirmação do 'Criar BD'
            {
               ArrayList<Usuario> lista = d.getLista();
               if(lista.isEmpty())
               {
                  request.setAttribute("acao", "PrimeiroUsuario");
                  request.setAttribute("central", c);
                  RequestDispatcher rd = getServletContext().getRequestDispatcher("/novo_usuario.jsp");
                  rd.forward(request, response);
               }
               else
                  throw new AlertaException("Inicializador", "Voltar",
                          "<p>O primeiro usuário do sistema já foi criado!</p>");
            }
            else//Cai aqui quando o acesso vem criar o primeiro uusário a partir da tela de incluir usuário
            {
               Usuario u = criarUsuario(request, response);

               if(d.cadastraUsuario(u))
               {
                  c.setLogado(u);
                  throw new SucessoException("CriarEmpresa", "Continuar",
                          "<p>O primeiro usuário foi criado com sucesso!<br><br>"
                          + "Clique em continuar para inserir os dados da sua empresa no sistema. Após isto, o sistema estará pronto para ser utilizado.</p>");
               }
               else
                  throw new AlertaException("PrimeiroUsuario", "Repetir",
                          "<p>Houve um erro ao criar o usuário, tente novamente. Caso o problema persista, entre em contato com o suporte.</p>");
            }
            break;
         }
         case "AtenticarUsuario":
         {
            String usuario = request.getParameter("usuario");
            String senha = request.getParameter("senha");
            Usuario u = d.logarUsuario(usuario, senha);

            if(u == null)
               throw new AlertaException("Inicializador", "Voltar", "<p>Usuário ou senha inválido, tente novamente.</p>");

            c.setLogado(u);
            request.setAttribute("central", c);

            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Principal");
            rd.forward(request, response);
            break;
         }
         case "AlterarSenha":
         {
            if(request.getParameter("alterar") == null)
            {
               request.setAttribute("central", c);
               RequestDispatcher rd = getServletContext().getRequestDispatcher("/alterar_senha.jsp");
               rd.forward(request, response);
            }
            else
            {
               String senhaatual = request.getParameter("senhaatual");
               Usuario u = d.logarUsuario(c.getLogado().getUsuario(), senhaatual);

               if(u == null)
                  throw new AlertaException("AlterarSenha", "Voltar", "<p>A senha atual informada não é válida.</p>");

               String novasenha = request.getParameter("novasenha");
               String novasenha2 = request.getParameter("novasenha2");

               if(novasenha.equals(novasenha2))
               {
                  u.setSenha(novasenha);
                  if(d.alteraUsuario(u))
                     throw new SucessoException("Principal", "Fechar",
                             "<p>Senha alterada com sucesso!</p>");
                  else
                     throw new AlertaException("AlterarSenha", "Voltar",
                             "<p>Houve um erro ao atualizar a senha, tente novamente.</p>");
               }
               else
                  throw new AlertaException("AlterarSenha", "Voltar", "<p>As novas senhas estão divergentes.</p>");
            }
            break;
         }
         case "Usuarios":
         {
            ArrayList<Usuario> lista = d.getLista();
            request.setAttribute("lista", lista);
            request.setAttribute("central", c);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/usuarios.jsp");
            rd.forward(request, response);
            break;
         }
         case "NovoUsuario":
         {
            if(request.getParameter("incluir") == null)
            {
               request.setAttribute("central", c);
               RequestDispatcher rd = getServletContext().getRequestDispatcher("/novo_usuario.jsp");
               rd.forward(request, response);
            }
            else
            {
               Usuario u = criarUsuario(request, response);
               if(d.cadastraUsuario(u))
                  throw new SucessoException("Usuarios", "Voltar",
                          "<p>Usuário incluído com sucesso!</p>");
               else
                  throw new AlertaException("NovoUsuario", "Repetir",
                          "<p>Este nome de usuário já está sendo utilizado, por favor escolha outro.</p>");
            }
            break;
         }
         case "ExcluirUsuario":
         {
            String id_s = request.getParameter("id");

            if(id_s == null)
               throw new AlertaException("Usuarios", "Voltar", "<p>Escolha um usuário para excluir.</p>");

            int id = Integer.parseInt(id_s);
            if(id == c.getLogado().getId() && c.getVenda_ativa() != null)
               throw new AlertaException("Usuarios", "Voltar", "<p>Não é permitido excluir este usuário agora, pois há uma venda não finalizada.</p>");
            else
            {
               Usuario u = d.getUsuario(id);
               if(d.excluiUsuario(u))
               {
                  if(id == c.getLogado().getId())
                     c = Central.fechaInstancia();
                  throw new SucessoException("Usuarios", "Voltar", "<p>Usuário excluído com sucesso!</p>");
               }
               else
                  throw new AlertaException("Usuarios", "Voltar", "<p>Este usuário não pode ser excluído, pois é o único com permissão para Gerenciar Usuários.</p>");
            }
            //break;
         }
         case "EditarUsuario":
         {
            if(request.getParameter("salvar") == null)
            {
               String id_s = request.getParameter("id");
               if(id_s == null)
                  throw new AlertaException("Usuarios", "Voltar", "<p>Escolha um usuário para editar.</p>");

               int id = Integer.parseInt(id_s);
               Usuario u = d.getUsuario(id);
               request.setAttribute("usuario", u);
               request.setAttribute("central", c);
               RequestDispatcher rd = getServletContext().getRequestDispatcher("/alterar_usuario.jsp");
               rd.forward(request, response);
            }
            else
            {
               int id = Integer.parseInt(request.getParameter("id"));
               Usuario u = d.getUsuario(id);

               String nome = request.getParameter("nome");
               String usuario = request.getParameter("usuario");
               String senha = request.getParameter("senha");
               u.setNome(nome);
               u.setUsuario(usuario);
               u.setSenha(senha);

               u = decorarUsuario(u, request, response);

               if(d.alteraUsuario(u))
               {
                  if(u.getId() == c.getLogado().getId())
                     c.setLogado(u);

                  throw new SucessoException("Usuarios", "Fechar",
                          "<p>Usuário alterado com sucesso!</p>");
               }
               else
                  throw new AlertaException("Usuarios", "Voltar",
                          "<p>A alteração não foi realizada. Os motivos possíveis são:<br><br></p>"
                          + "<ul>"
                          + "<li>O novo nome de usuário já está sendo usado.</li>"
                          + "<li>A permissão de Gerenciar Usuários foi removida, e este é o último usuário com tal permissão.</li>"
                          + "</ul>");
            }
            break;
         }
         case "Sair":
         {
            if(c.getVenda_ativa() == null)
            {
               c = Central.fechaInstancia();
               DataBaseConnect.fechaConexao();
               RequestDispatcher rd = getServletContext().getRequestDispatcher("/Inicializador");
               rd.forward(request, response);
            }
            else
               throw new AlertaException("Principal", "Voltar", "<p>Há uma venda iniciada. Finalize ou cancela a venda antes de fazer log-off.</p>");
            break;
         }
         default:
            throw new AlertaException("Inicializador", "Voltar", "<p>Você não deveria estar aqui.</p>");
      }

   }

   //Método para criar o usuário
   private Usuario criarUsuario(HttpServletRequest request, HttpServletResponse response) {
      String nome = request.getParameter("nome");
      String usuario = request.getParameter("usuario");
      String senha = request.getParameter("senha");

      Usuario u = new UsuarioBasico(nome, usuario, senha);
      u = decorarUsuario(u, request, response);
      return u;
   }

   //Método para decorar (inserir ou remover) decorações de acordo com oo preenchimento
   //do formulário na página de cadastros
   private Usuario decorarUsuario(Usuario u, HttpServletRequest request, HttpServletResponse response) {

      if(request.getParameter("P_ConcederDesconto") != null)
         u = u.adicionaPapel("P_ConcederDesconto");
      else
         u = u.removePapel("P_ConcederDesconto");

      if(request.getParameter("P_ConfigurarSistema") != null)
         u = u.adicionaPapel("P_ConfigurarSistema");
      else
         u = u.removePapel("P_ConfigurarSistema");

      if(request.getParameter("P_EfetuarVenda") != null)
         u = u.adicionaPapel("P_EfetuarVenda");
      else
         u = u.removePapel("P_EfetuarVenda");

      if(request.getParameter("P_GerenciarProduto") != null)
         u = u.adicionaPapel("P_GerenciarProduto");
      else
         u = u.removePapel("P_GerenciarProduto");

      if(request.getParameter("P_GerenciarUsuario") != null)
         u = u.adicionaPapel("P_GerenciarUsuario");
      else
         u = u.removePapel("P_GerenciarUsuario");

      if(request.getParameter("P_VisualizarRelatorio") != null)
         u = u.adicionaPapel("P_VisualizarRelatorio");
      else
         u = u.removePapel("P_VisualizarRelatorio");

      return u;
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
