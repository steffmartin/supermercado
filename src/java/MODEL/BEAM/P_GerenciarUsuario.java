/*
 * Trabalho de Programacao Orientada a Objetos 2
 * Grupo:
 * 11511BSI267 - Heitor H. Nunes
 * 11411BSI207 - Matheus Eduardo da S. Ramos
 * 11511BSI257 - Pedro Henrique da Silva
 * 11511BSI215 - Steffan M.  Alves
 */
package MODEL.BEAM;

/**
 *
 * @author steff
 */
public class P_GerenciarUsuario extends Papel {
   
   private static final String descricao = "Gerenciar usuários";

   public P_GerenciarUsuario() {
   }

   public String getPapel() {
      return descricao + ", " + this.getCamada().getPapel();
   }
   
   public static String getDescricao(){
      return descricao;
   }

}
