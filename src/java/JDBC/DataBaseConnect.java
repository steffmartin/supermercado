/*
 * Trabalho de Programacao Orientada a Objetos 2
 * Grupo:
 * 11511BSI267 - Heitor H. Nunes
 * 11411BSI207 - Matheus Eduardo da S. Ramos
 * 11511BSI257 - Pedro Henrique da Silva
 * 11511BSI215 - Steffan M.  Alves
 */
package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author steff
 */
public class DataBaseConnect {

   // Variáveis voláteis e estáticas, apenas uma instância para todos os objetos e threads
   private volatile static Connection conexao = null;
     
   // Conexão Local
   private volatile static String servidor = "localhost";
   private volatile static String porta = "5432";
   private volatile static String base = "postgres";
   private volatile static String usuario = "postgres";
   private volatile static String senha = "root";
   private volatile static boolean ssl = false;

   // Conexão Heroku
   /*
   private volatile static String servidor = "ec2-23-23-225-12.compute-1.amazonaws.com";
   private volatile static String porta = "5432";
   private volatile static String base = "d68sk9rov7i3bp";
   private volatile static String usuario = "moyzeqnzkqpsvp";
   private volatile static String senha = "7120b221260407e692f6f44a2e35f24d7119d3950c4d98a15e5171f14048431e";
   private volatile static boolean ssl = true;
   */
   
   // Construtor privado - Utilizando padrão Singleton
   private DataBaseConnect() {
   }

   public static Connection getConexao() {
      if(conexao == null)
         synchronized(DataBaseConnect.class)
         {
            if(conexao == null)
               try
               {
                    // Registra o driver do PostgreSQL e tenta se conectar à BASE DE DADOS
                    Class.forName("org.postgresql.Driver");
                    if (ssl)
                        conexao = DriverManager.getConnection("jdbc:postgresql://" + servidor + ":" + porta + "/" + base + "?user=" + usuario + "&password=" + senha + "&ssl=true");
                    else
                        conexao = DriverManager.getConnection("jdbc:postgresql://" + servidor + ":" + porta + "/" + base + "?user=" + usuario + "&password=" + senha);
                }
               catch(ClassNotFoundException er)
               {
                  // Não achou o driver
                  throw new RuntimeException(er);
               }
               catch(SQLException e)
               {
                  //Não conseguiu se conectar ao BD, retornará uma conexão nula.
               }
         }
      return conexao;
   }

   public static Connection fechaConexao() {
      if(conexao != null)
         try
         {
            conexao.close();
            conexao = null;
         }
         catch(SQLException ex)
         {
            throw new RuntimeException(ex);
         }
      return conexao;
   }

}
