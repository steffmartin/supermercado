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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import MODEL.BEAM.Produto;
import java.util.ArrayList;

/**
 *
 * @author steff
 */
public class ProdutoDAO {

   private volatile static Connection conexao = null;

   public ProdutoDAO() {
      this.conexao = DataBaseConnect.getConexao();
   }

   // Método para cadastrar o Produto na base de dados
   public boolean cadastraProduto(Produto p) {
      try
      {
         //O código de barras não pode exisitir em nenhum produto já cadastrado
         PreparedStatement sql = conexao.prepareStatement("select count(*)=0 as rs from (select cod_barras from produto where cod_barras = '" + p.getCod_barras() + "') as rs");
         ResultSet rs = sql.executeQuery();
         rs.next();
         //Se não achou o código de barras no BD, pode cadastrar
         if(rs.getBoolean("rs"))
            try
            {
               sql = conexao.prepareStatement("insert into produto "
                       + "(qtd_disponivel, qtd_aviso, cod_barras, descricao, un_medida, preco_venda, preco_compra, notificar) values "
                       + "(" + p.getQtd_disponivel() + "," + p.getQtd_aviso() + ",'" + p.getCod_barras() + "','" + p.getDescricao() + "','" + p.getUn_medida() + "'," + p.getPreco_venda() + "," + p.getPreco_compra() + "," + p.getNotificar() + ")");
               sql.execute();
               //sql.close();

               // O ID do Produto é controlado pela base de dados, este trecho gravará o ID do Produto no objeto deste Produto
               sql = conexao.prepareStatement("select currval('produto_id_seq')");
               rs = sql.executeQuery();
               rs.next();
               p.setId(rs.getInt("currval"));
               sql.close();
               rs.close();
            }
            catch(SQLException er)
            {
               //Se houve exception nesta etapa, pode ser que a sequência de IDs foi comprometida
               //Este query corrige a sequência para o próximo cadastro
               sql = conexao.prepareStatement("select setval('produto_id_seq',(select coalesce(max(id),1) from produto))");
               sql.execute();
               sql.close();
               throw new RuntimeException(er);
            }
         //Caso achar o código de barras no BD, não cadastrar
         else
            return false;
         return true;
      }
      catch(SQLException e)
      {
         throw new RuntimeException(e);
      }
   }

   //Método para retornar um produto pelo ID
   //Se não achar o produto retornará null, portanto precisa tratar na chamada este caso null
   public Produto getProduto(int id) {
      Produto p = null;
      try
      {
         PreparedStatement sql = conexao.prepareStatement("select * from produto where id = " + id);
         ResultSet rs = sql.executeQuery();
         if(rs.next())
         {
            p = new Produto(rs.getFloat("qtd_disponivel"), rs.getFloat("qtd_aviso"), rs.getString("cod_barras"), rs.getString("descricao"), rs.getString("un_medida"), rs.getFloat("preco_venda"), rs.getFloat("preco_compra"), rs.getBoolean("notificar"));
            p.setId(rs.getInt("id"));
         }
         sql.close();
         rs.close();
         return p;
      }
      catch(SQLException e)
      {
         throw new RuntimeException(e);
      }
   }

   //Método para retornar um produto pelo código de barras
   //Se não achar o produto retornará null, portanto precisa tratar na chamada este caso null
   public Produto getProduto(String cod_barras) {
      Produto p = null;
      try
      {
         PreparedStatement sql = conexao.prepareStatement("select * from produto where cod_barras = '" + cod_barras + "'");
         ResultSet rs = sql.executeQuery();
         if(rs.next())
         {
            p = new Produto(rs.getFloat("qtd_disponivel"), rs.getFloat("qtd_aviso"), rs.getString("cod_barras"), rs.getString("descricao"), rs.getString("un_medida"), rs.getFloat("preco_venda"), rs.getFloat("preco_compra"), rs.getBoolean("notificar"));
            p.setId(rs.getInt("id"));
         }
         sql.close();
         rs.close();
         return p;
      }
      catch(SQLException e)
      {
         throw new RuntimeException(e);
      }
   }

   //Método para retornar todos os produtos como um arraylist
   //Se não tiver cadastros, retorna a lista vazia
   public ArrayList<Produto> getLista() {
      ArrayList<Produto> lista = new ArrayList<>();
      try
      {
         PreparedStatement sql = conexao.prepareStatement("select id from produto order by id");
         ResultSet rs = sql.executeQuery();
         while(rs.next())
         {
            Produto p = this.getProduto(rs.getInt("id"));
            lista.add(p);
         }
         sql.close();
         rs.close();
         return lista;
      }
      catch(SQLException e)
      {
         throw new RuntimeException(e);
      }
   }

   //Método para alterar os dados de um produto já cadastrado
   //Mesmo que somente uma informação foi alterada, tudo será atualizado!
   public boolean alteraProduto(Produto p) {
      try
      {
         //O novo código de barras não pode exisitir em outro produto
         PreparedStatement sql = conexao.prepareStatement("select count(*)=0 as rs from (select cod_barras from produto where cod_barras = '" + p.getCod_barras() + "' and id <> " + p.getId() + ") as rt");
         ResultSet rs = sql.executeQuery();
         rs.next();
         if(rs.getBoolean("rs"))
            try
            {
               sql = conexao.prepareStatement("update produto set descricao = '" + p.getDescricao() + "',preco_venda = " + p.getPreco_venda() + ",preco_compra = " + p.getPreco_compra() + ",un_medida = '" + p.getUn_medida() + "',qtd_disponivel = " + p.getQtd_disponivel() + ",qtd_aviso = " + p.getQtd_aviso() + ",cod_barras = '" + p.getCod_barras() + "',notificar = ? where id = " + p.getId());
               sql.setBoolean(1, p.getNotificar());
               sql.execute();
               sql.close();
               rs.close();
            }
            catch(SQLException er)
            {
               throw new RuntimeException(er);
            }
         //Se algum produto já tem este codigo de barras, não alterar
         else
            return false;
         return true;
      }
      catch(SQLException e)
      {
         throw new RuntimeException(e);
      }
   }

   //Método para excluir um produto
   public boolean excluiProduto(Produto p) {
      try
      {
         //Não pode excluir produto que está relacionado com alguma venda
         PreparedStatement sql = conexao.prepareStatement("select count(*)=0 as rs from (select id_produto from vendaXproduto where id_produto = " + p.getId() + ") as rt");
         ResultSet rs = sql.executeQuery();
         rs.next();
         if(rs.getBoolean("rs"))
            try
            {
               sql = conexao.prepareStatement("delete from produto where id = " + p.getId());
               sql.execute();
               sql.close();
               rs.close();
            }
            catch(SQLException er)
            {
               throw new RuntimeException(er);
            }
         //Se o produto já foi vendido anteriormente, nao excluir
         else
            return false;
         return true;
      }
      catch(SQLException e)
      {
         throw new RuntimeException(e);
      }
   }

}
