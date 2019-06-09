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
import MODEL.BEAM.Central;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author steff
 */
public class CentralDAO {

   private volatile static Connection conexao = null;

   public CentralDAO() {
      this.conexao = DataBaseConnect.getConexao();
   }

   //Função que preenche a tabela da central
   public boolean cadastraCentral(Central c) {
      try
      {
         //Só se cadastra uma Central, na inicialização. Após o primeiro cadastro, só é permitido alterá-lo, nunca criar um novo
         PreparedStatement sql = conexao.prepareStatement("select count(*)=0 as rs from central;");
         ResultSet rs = sql.executeQuery();
         rs.next();
         //Se não achou a central, pode cadastrar
         if(rs.getBoolean("rs"))
         {
            sql = conexao.prepareStatement("insert into central "
                    + "(empresa,endereco,cidade,estado,cep,cnpj,ie,im) values "
                    + "('" + c.getEmpresa() + "','" + c.getEndereco() + "','" + c.getCidade() + "','" + c.getEstado() + "','" + c.getCep() + "','" + c.getCnpj() + "','" + c.getIe() + "','" + c.getIm() + "');");
            sql.execute();
            sql.close();
         }
         //Se já achou central, editá-la em vez de criar novo registro
         else
            return alteraCentral(c);

         //Passar a vez para a função que gravará a lista de estoque baixo
         return atualizaEstoque_Baixo(c);
      }
      catch(SQLException e)
      {
         throw new RuntimeException(e);
      }
   }

   //Método que lê a central do BD e grava no sistema
   public Central getCentral() {
      Central c = Central.getInstancia();
      try
      {
         PreparedStatement sql = conexao.prepareStatement("select * from central");
         ResultSet rs = sql.executeQuery();
         if(rs.next())
         {
            c.setEmpresa(rs.getString("empresa"));
            c.setEndereco(rs.getString("endereco"));
            c.setCidade(rs.getString("cidade"));
            c.setEstado(rs.getString("estado"));
            c.setCep(rs.getString("cep"));
            c.setCnpj(rs.getString("cnpj"));
            c.setIe(rs.getString("ie"));
            c.setIm(rs.getString("im"));
         }
         //sql.close();
         //rs.close();
         sql = conexao.prepareStatement("select * from estoque_baixo order by id");
         rs = sql.executeQuery();
         
         ArrayList<Integer> estoque_baixo = new ArrayList<>();
         while(rs.next())
            estoque_baixo.add((Integer) rs.getInt("id")); 
         c.setEstoque_baixo(estoque_baixo);

         return c;
      }
      catch(SQLException e)
      {
         throw new RuntimeException(e);
      }
   }

   //Função que altera os dados da central
   public boolean alteraCentral(Central c) {
      try
      {
         //Só se altera uma central se ela já existir
         PreparedStatement sql = conexao.prepareStatement("select count(*)>0 as rs from central;");
         ResultSet rs = sql.executeQuery();
         rs.next();
         //Se achou a central, pode alterar
         if(rs.getBoolean("rs"))
         {
            sql = conexao.prepareStatement("update central set empresa = '" + c.getEmpresa() + "', endereco = '" + c.getEndereco() + "', cidade = '" + c.getCidade() + "', estado = '" + c.getEstado() + "', "
                    + "cep = '" + c.getCep() + "', cnpj = '" + c.getCnpj() + "', ie = '" + c.getIe() + "', im = '" + c.getIm() + "';");
            sql.execute();
            sql.close();
         }
         //Se não achou central,criá-la em vez de alterar
         else
            return cadastraCentral(c);

         //Passar a vez para a função que atualizará a lista de estoque baixo
         return atualizaEstoque_Baixo(c);
      }
      catch(SQLException e)
      {
         throw new RuntimeException(e);
      }
   }

   //Função que atualiza a lista de produtos com estoque baixo
   //Sempre limpa a lista e grava a nova (se existir)
   public boolean atualizaEstoque_Baixo(Central c) {
      try
      {
         PreparedStatement sql = conexao.prepareStatement("delete from estoque_baixo;");
         sql.execute();
         //sql.close();

         if(!c.getEstoque_baixo().isEmpty())
         {
            String values = "";
            for(Integer i : c.getEstoque_baixo())
               values += "(" + i + "),";
            //Remover a última , da string
            values = values.substring(0, values.length() - 1);

            sql = conexao.prepareStatement("insert into estoque_baixo values " + values);
            sql.execute();
         }

         sql.close();
         return true;
      }
      catch(SQLException e)
      {
         throw new RuntimeException(e);
      }

   }

}
