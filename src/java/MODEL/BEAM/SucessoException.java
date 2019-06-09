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

//Classe criada para personalizar confirmações de processos para o usuário
public class SucessoException extends RuntimeException {

    private String url1;
    private String texto1;
   
   public SucessoException(String url1, String texto1, String mensagem) {
      super(mensagem);
      this.url1 = url1;
      this.texto1 = texto1;
   }

   public String getUrl1() {
      return url1;
   }

   public String getTexto1() {
      return texto1;
   }

}
