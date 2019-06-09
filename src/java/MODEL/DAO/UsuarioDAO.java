/*
 * Trabalho de Programacao Orientada a Objetos 2
 * Grupo:
 * 11511BSI267 - Heitor H. Nunes
 * 11411BSI207 - Matheus Eduardo da S. Ramos
 * 11511BSI257 - Pedro Henrique da Silva
 * 11511BSI215 - Steffan M.  Alves
 */
package MODEL.DAO;

import JDBC.DataBaseConnect;
import MODEL.BEAM.P_GerenciarUsuario;
import MODEL.BEAM.Usuario;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author steff
 */
public class UsuarioDAO {

   private volatile static Connection conexao = null;

   public UsuarioDAO() {
      this.conexao = DataBaseConnect.getConexao();
   }

   // Método para cadastrar o Usuário (e suas decorações) na base de dados
   //A senha é gravada com hash md5
   public boolean cadastraUsuario(Usuario u) {

      try
      {
         //O nome de usuário (login) não pode exisitir em nenhum outro usuário já cadastrado
         PreparedStatement sql = conexao.prepareStatement("select count(*)=0 as rs from (select usuario from usuario where usuario = '" + u.getUsuario() + "') as rs");
         ResultSet rs = sql.executeQuery();
         rs.next();
         //Se não achou o usuário (login) no BD, pode cadastrar
         if(rs.getBoolean("rs"))
            try
            {
               sql = conexao.prepareStatement("insert into usuario "
                       + "(nome, usuario, senha, papel) values "
                       + "('" + u.getNome() + "','" + u.getUsuario() + "',crypt('" + u.getSenha() + "',gen_salt('md5')),'" + u.getPapel() + "')");
               sql.execute();
               //sql.close();

               // O ID do Usuário é controlado pela base de dados, este trecho gravará o ID do Usuário no objeto deste Usuário
               sql = conexao.prepareStatement("select currval('usuario_id_seq')");
               rs = sql.executeQuery();
               rs.next();
               u.setId(rs.getInt("currval"));
               //sql.close();
               //rs.close();

               // A senha é criptografada no Banco de Dados, não é interessante ter a senha 'aberta' no objeto
               // Salvar a senha criptografada no objeto
               sql = conexao.prepareStatement("select senha from usuario where id = " + u.getId());
               rs = sql.executeQuery();
               rs.next();
               u.setSenha(rs.getString("senha"));
               //sql.close();
               //rs.close();

               // Agora gravar o objeto decorado no banco, para recuperar as decorações posteriormente
               sql = conexao.prepareStatement("update usuario set objeto_decorado = ? where id = " + u.getId() + "");
               sql.setBytes(1, Usuario.serializa(u));
               sql.execute();
               sql.close();
               rs.close();
            }
            catch(SQLException er)
            {
               //Se houve exception nesta etapa, pode ser que a sequência de IDs foi comprometida
               //Este query corrige a sequência para o próximo cadastro
               sql = conexao.prepareStatement("select setval('usuario_id_seq',(select coalesce(max(id),1) from usuario))");
               sql.execute();
               sql.close();
               throw new RuntimeException(er);
            }
         //Se achou este login no bd, não pode cadastrar
         else
            return false;
         return true;
      }
      catch(SQLException | IOException e)
      {
         throw new RuntimeException(e);
      }

   }

   //Método que retorna um usuário pelo seu ID
   //Como o próprio objeto do usuário é guardado no BD, já será capturado diretamente tal objeto
   //Se não achar o usuário retornará null, portanto precisa tratar na chamada este caso null
   public Usuario getUsuario(int id) {
      Usuario u = null;
      try
      {
         PreparedStatement sql = conexao.prepareStatement("select objeto_decorado from usuario where id = " + id);
         ResultSet rs = sql.executeQuery();
         if(rs.next())
            u = Usuario.deserializa(rs.getBytes("objeto_decorado"));
         sql.close();
         rs.close();
         return u;
      }
      catch(SQLException | IOException | ClassNotFoundException e)
      {
         throw new RuntimeException(e);
      }

   }

   //Método que retorna um usuário pelo seu 'nome de usuário (login)'
   //Como o próprio objeto do usuário é guardado no BD, já será capturado diretamente tal objeto
   //Se não achar o usuário retornará null, portanto precisa tratar na chamada este caso null
   public Usuario getUsuario(String usuario) {
      Usuario u = null;
      try
      {
         PreparedStatement sql = conexao.prepareStatement("select objeto_decorado from usuario where usuario = '" + usuario + "'");
         ResultSet rs = sql.executeQuery();
         if(rs.next())
            u = Usuario.deserializa(rs.getBytes("objeto_decorado"));
         sql.close();
         rs.close();
      }
      catch(SQLException | IOException | ClassNotFoundException e)
      {
         throw new RuntimeException(e);
      }
      return u;
   }

   //Método para retornar todos os usuários como um arraylist
   //Se não tiver cadastros, retorna a lista vazia
   public ArrayList<Usuario> getLista() {
      ArrayList<Usuario> lista = new ArrayList<>();
      try
      {
         PreparedStatement sql = conexao.prepareStatement("select objeto_decorado from usuario order by id");
         ResultSet rs = sql.executeQuery();
         while(rs.next())
         {
            Usuario u = Usuario.deserializa(rs.getBytes("objeto_decorado"));
            lista.add(u);
         }
         sql.close();
         rs.close();
         return lista;
      }
      catch(SQLException | IOException | ClassNotFoundException e)
      {
         throw new RuntimeException(e);
      }
   }

   //Método para alterar os dados de um usuário já cadastrado
   //Mesmo que somente uma informação foi alterada, tudo será atualizado!
   public boolean alteraUsuario(Usuario u) {
      try
      {
         //O novo nome de usuário (login) não pode exisitir em nenhum outro usuário já cadastrado
         PreparedStatement sql = conexao.prepareStatement("select count(*)=0 as rs from (select usuario from usuario where usuario = '" + u.getUsuario() + "' and id <> " + u.getId() + ") as rs;");
         ResultSet rs = sql.executeQuery();
         rs.next();
         //Devemos tomar cuidado para que o papel de Gerenciar Usuários continue para pelo menos um novo usuário
         sql = conexao.prepareStatement("drop table if exists papeis; create temp table papeis as (select distinct papel from usuario where id <> " + u.getId() + "); insert into papeis values ('" + u.getPapel() + "');");
         sql.execute();
         sql = conexao.prepareStatement("select count(*)>0 as rt from (select papel from papeis where papel like '%" + P_GerenciarUsuario.getDescricao() + "%') as rs;");
         ResultSet rt = sql.executeQuery();
         rt.next();
         sql = conexao.prepareStatement("drop table if exists papeis;");
         sql.execute();
         //Se não achou o usuário (login) no BD, e se os novos papeis não compromente a integridade, pode alterar o usuario
         if(rs.getBoolean("rs") & rt.getBoolean("rt"))
            try
            {
               sql = conexao.prepareStatement("update usuario set nome = '" + u.getNome() + "', usuario = '" + u.getUsuario() + "', papel = '" + u.getPapel() + "' where id = " + u.getId());
               sql.execute();
               //sql.close();

               // A senha só pode ser regravada no BD se ela foi alterada, por causa da criptografia, senão haveria dupla criptografia
               sql = conexao.prepareStatement("select senha = '" + u.getSenha() + "' as senha from usuario where id = " + u.getId());
               rs = sql.executeQuery();
               rs.next();
               // Se a senha não bate com o que está no BD, então foi alterada, deve ser regravada
               if(!rs.getBoolean("senha"))
               {
                  sql = conexao.prepareStatement("update usuario set senha = crypt('" + u.getSenha() + "',gen_salt('md5')) where id =" + u.getId());
                  sql.execute();
                  sql.close();
                  // Trocar a senha 'normal' pela criptografada no objeto atualizado
                  sql = conexao.prepareStatement("select senha from usuario where id = " + u.getId());
                  rs = sql.executeQuery();
                  rs.next();
                  u.setSenha(rs.getString("senha"));
                  //sql.close();
                  //rs.close();
               }
               //Se a senha não foi alterada, não fazer nada.
               // sql.close();

               // Agora vamos regravar o objeto decorado atualizado no banco, para recuperar as decorações posteriormente
               sql = conexao.prepareStatement("update usuario set objeto_decorado = ? where id = " + u.getId() + "");
               sql.setBytes(1, Usuario.serializa(u));
               sql.execute();
               sql.close();
               rs.close();
               rt.close();
            }
            catch(SQLException | IOException e)
            {
               throw new RuntimeException(e);
            }
         //Se alguém já tem este login, ou se com esta altetação não sobra gerente de usuários, não alterar
         else
            return false;
         return true;
      }
      catch(SQLException e)
      {
         throw new RuntimeException(e);
      }
   }

   //Método para excluir usuário
   //Não pode excluir o próprio usuário?
   public boolean excluiUsuario(Usuario u) {

      try
      {
         //Não pode excluir o último usuário com acesso a gerenciar usuarios, senão fica impossível criar outros
         PreparedStatement sql = conexao.prepareStatement("select count(*)>1 as rs from (select papel from usuario where papel like '%" + P_GerenciarUsuario.getDescricao() + "%') as rs");
         System.out.println(sql);
         ResultSet rs = sql.executeQuery();
         rs.next();
         if(rs.getBoolean("rs") || !u.temPapel("P_GerenciarUsuario"))
            try
            {
               sql = conexao.prepareStatement("delete from usuario where id = " + u.getId());
               sql.execute();
               sql.close();
               rs.close();
            }
            catch(SQLException e)
            {
               throw new RuntimeException(e);
            }
         //Se está tentando excluir o último Gerente de Usuários, não excluir!
         else
            return false;

         return true;
      }
      catch(SQLException e)
      {
         throw new RuntimeException(e);
      }
   }

   //Método para autenticar o usuário. Retorna o usuário ou retorna nulo
   public Usuario logarUsuario(String usuario, String senha) {

      try
      {
         PreparedStatement sql = conexao.prepareStatement("select count(*)=1 as rs from usuario where usuario = '" + usuario + "' and senha = crypt('" + senha + "',senha)");
         ResultSet rs = sql.executeQuery();
         rs.next();
         if(rs.getBoolean("rs"))
         {
            sql.close();
            rs.close();
            Usuario u = this.getUsuario(usuario);
            return u;
         }
         sql.close();
         rs.close();
      }
      catch(SQLException e)
      {
         throw new RuntimeException(e);
      }
      //Se não autenticar retorna null
      return null;
   }

}
