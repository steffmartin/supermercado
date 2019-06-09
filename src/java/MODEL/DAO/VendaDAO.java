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
import MODEL.BEAM.Produto;
import MODEL.BEAM.Venda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author steff
 */
public class VendaDAO {

   private volatile static Connection conexao = null;

   public VendaDAO() {
      this.conexao = DataBaseConnect.getConexao();
   }

   //Método para pegar o ID para se iniciar uma nova venda
   public int nextID() {
      try
      {
         PreparedStatement sql = conexao.prepareStatement("select nextval('venda_id_seq') as id");
         ResultSet rs = sql.executeQuery();
         if(rs.next())
            return rs.getInt("id");
         sql.close();
         rs.close();
      }
      catch(SQLException e)
      {
         throw new RuntimeException(e);
      }
      return -1;
   }

   //Método para inserir a venda efetivada ou anulada na base
   public boolean insereVenda(Venda v) {
      //Não há condição especial para inserir a venda, tudo já foi tratado anteriormente nas diversas checagens do BEAM
      try
      {
         //Insere a venda
         PreparedStatement sql = conexao.prepareStatement("insert into venda (id,total_bruto,total_descontos,total_liquido,valor_recebido,troco,quebra_de_caixa,forma_pagamento,datahora,cupom,vendedor,qtd_itens) values "
                 + "(" + v.getId() + "," + v.getTotal_bruto() + "," + v.getTotal_descontos() + "," + v.getTotal_liquido() + "," + v.getValor_recebido() + "," + v.getTroco() + "," + v.getQuebra_de_caixa() + ",'" + v.getForma_pagamento() + "',?,'" + v.getCupom() + "','" + v.getVendedor() + "'," + v.getQtd_itens() + ")");
         sql.setObject(1, Timestamp.valueOf(v.getDatahora()), java.sql.Types.TIMESTAMP);
         sql.execute();
         //sql.close();

         //Insere os produtos da venda na tabela vendaXproduto
         //Se o arraylist está vazio trata-se de uma venda nula/cancelada, neste caso pula esse passo
         if(!v.getLista().isEmpty())
         {
            String values = "";
            for(Object[] p : v.getLista())
               values += "(" + v.getId() + "," + ((Produto) p[0]).getId() + "," + ((Float) p[1]) + "," + ((Produto) p[0]).getPreco_venda() + "),";
            //Remover a última , da string
            values = values.substring(0, values.length() - 1);
            sql = conexao.prepareStatement("insert into vendaXproduto (id_venda,id_produto,quantidade,preco_venda) values " + values);
            sql.execute();
         }
         sql.close();

         return true;
      }
      catch(SQLException e)
      {
         return false;
      }

   }

   //Capturar a venda consistirá em 2 passos: cabeçalho + lista de produtos.
   //Método para pegar o cabeçalho da venda
   public Venda getCabecalhoVenda(int id) {
      Venda v = null;
      try
      {
         //Preencher a venda com todos os dados recuperados
         PreparedStatement sql = conexao.prepareStatement("select * from venda where id = " + id);
         ResultSet rs = sql.executeQuery();
         //sql.close();
         //rs.close();
         if(rs.next())
         {
            v = new Venda(rs.getInt("id"), rs.getString("vendedor"));
            v.setTotal_bruto(rs.getFloat("total_bruto"));
            v.setTotal_descontos(rs.getFloat("total_descontos"));
            v.setTotal_liquido(rs.getFloat("total_liquido"));
            v.setValor_recebido(rs.getFloat("valor_recebido"));
            v.setTroco(rs.getFloat("troco"));
            v.setQuebra_de_caixa(rs.getFloat("quebra_de_caixa"));
            v.setForma_pagamento(rs.getString("forma_pagamento"));
            Timestamp t = rs.getTimestamp("datahora");
            v.setDatahora(t.toLocalDateTime());
            v.setQtd_itens(rs.getInt("qtd_itens"));
            v.setCupom(rs.getString("cupom"));
         }

         sql.close();
         rs.close();

         return v;
      }
      catch(SQLException e)
      {
         throw new RuntimeException(e);
      }
   }

   //Método para pegar a lista de produtos da venda
   public ArrayList<Object[]> getListaProdutosVenda(int id) {
      ArrayList<Object[]> lista = new ArrayList<>();
      ProdutoDAO pd = new ProdutoDAO();
      try
      {
         //Preencher o Array dos produtos que foram vendidos
         PreparedStatement sql = conexao.prepareStatement("select * from vendaXproduto where id_venda = " + id);
         ResultSet rs = sql.executeQuery();

         while(rs.next())
         {
            Produto p = pd.getProduto(rs.getInt("id_produto"));
            p.setPreco_venda(rs.getFloat("preco_venda"));
            Object[] o =
            {
               p, rs.getFloat("quantidade")
            };
            lista.add(o);
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

   //Método para pegar uma venda completa (Cabeçalho + produtos)
   public Venda getVenda(int id) {

      Venda v = null;
      v = this.getCabecalhoVenda(id);
      v.setLista(this.getListaProdutosVenda(id));
      return v;

   }

   //Método que retorna uma lista com todas as vendas, porém sem os produtos no Array, para ficar mais leve
   public ArrayList<Venda> getListaCabecalhoVenda() {
      ArrayList<Venda> lista = new ArrayList<>();
      try
      {
         PreparedStatement sql = conexao.prepareStatement("select id from venda order by id");
         ResultSet rs = sql.executeQuery();
         while(rs.next())
         {
            Venda v = this.getCabecalhoVenda(rs.getInt("id"));
            lista.add(v);
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

   //Método que retornar uma lista com todas as vendas (completo, incluindo os produtos)
   public ArrayList<Venda> getLista() {
      ArrayList<Venda> lista = this.getListaCabecalhoVenda();// new ArrayList<>();
      for(Venda v : lista)
         v.setLista(this.getListaProdutosVenda(v.getId()));
      return lista;
   }

   //Não há métodos para alterar ou excluir uma venda, as regras de negócio não permitem estas operações
}
