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
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author steff
 */
public class DataBaseDAO {

   private Connection conexaoDB = null;
   private static volatile boolean executado = false;

   public DataBaseDAO() {
      this.conexaoDB = DataBaseConnect.getConexao();
   }

   // Método que cria a base de dados completa (base, tabelas, funções, sequências, etc)
   // Este DAO só será chamado na primeira execução do programa pelo CONTROLLER na inicialização
   public synchronized boolean criaBaseDeDados() {
      
      //Previne reexecução do método sincronizado
      if(executado == true)
         return true;
      
      try
      {
         // Criar a base de dados
         PreparedStatement sql = conexaoDB.prepareStatement("BEGIN; set datestyle to 'DMY';");
         sql.execute();
         //sql.close();
         
         // Criação da tabela usuario
         sql = conexaoDB.prepareStatement(""
                 + "create extension pgcrypto;"
                 + "create table usuario("
                 + "id serial not null primary key,"
                 + "nome varchar(100) not null,"
                 + "usuario varchar(20) not null unique,"
                 + "senha varchar not null,"
                 + "papel varchar not null,"
                 + "objeto_decorado bytea);"
         );
         sql.execute();
         //sql.close();

         // Criação da tabela produto
         sql = conexaoDB.prepareStatement(""
                 + "create table produto("
                 + "id serial not null primary key,"
                 + "descricao varchar(100) not null,"
                 + "preco_venda float not null,"
                 + "preco_compra float not null,"
                 + "un_medida varchar(2) not null,"
                 + "qtd_disponivel float not null,"
                 + "qtd_aviso float,"
                 + "cod_barras varchar(13) not null unique,"
                 + "notificar boolean not null);"
         );
         sql.execute();
         //sql.close();
         
         // Criação da tabela central e o seu array de produtos com estoque baixo
         sql = conexaoDB.prepareStatement(""
                 + "create table central("
                 + "id int primary key default(1),"
                 + "empresa varchar(100) not null,"
                 + "endereco varchar(200) not null,"
                 + "cidade varchar(50) not null,"
                 + "estado varchar(2) not null,"
                 + "cep varchar(11) not null,"
                 + "cnpj varchar(18) not null,"
                 + "ie varchar(20),"
                 + "im varchar(20)"
                 + ");"
                 + "create table estoque_baixo("
                 + "id int primary key references produto(id) on delete cascade"
                 + ");"
         );
         sql.execute();
         //sql.close();

         // Criação da tabela venda
         sql = conexaoDB.prepareStatement(""
                 + "create table venda("
                 + "id serial not null primary key,"
                 + "total_bruto float not null,"
                 + "total_descontos float not null,"
                 + "total_liquido float not null,"
                 + "valor_recebido float not null,"
                 + "troco float not null,"
                 + "quebra_de_caixa float not null,"
                 + "forma_pagamento varchar(50) not null,"
                 + "datahora timestamp not null,"
                 + "cupom varchar not null,"
                 + "vendedor varchar(100) not null,"
                 + "qtd_itens int not null);"
         );
         sql.execute();
         //sql.close();

         // Criação da tabela produtos por venda, para representar o array de produtos da venda
         sql = conexaoDB.prepareStatement(""
                 + "create table vendaXproduto("
                 + "id_venda integer not null references venda(id),"
                 + "id_produto integer not null references produto(id),"
                 + "quantidade float not null,"
                 + "preco_venda float not null,"
                 + "primary key (id_venda,id_produto));"
                 + "COMMIT;"
         );
         sql.execute();
         sql.close();
         //this.conexaoDB = DataBaseConnect.fechaConexao();
         executado = true;
         return true;
      }
      catch(SQLException e)
      {
         return false;
      }
   }
   
    public boolean existeTabelas() {
        try {
            PreparedStatement sql = conexaoDB.prepareStatement("select * from vendaXproduto;");
            ResultSet rs = sql.executeQuery();
            return true;
        } catch (SQLException er) {
            return false;
        }
    }

}
