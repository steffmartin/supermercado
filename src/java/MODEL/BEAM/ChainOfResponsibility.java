/*
 * Trabalho de Programacao Orientada a Objetos 2
 * Grupo:
 * 11511BSI267 - Heitor H. Nunes
 * 11411BSI207 - Matheus Eduardo da S. Ramos
 * 11511BSI257 - Pedro Henrique da Silva
 * 11511BSI215 - Steffan M.  Alves
 */
package MODEL.BEAM;

import java.math.BigDecimal;

/**
 *
 * @author steff
 */
public abstract class ChainOfResponsibility {

   //Sucessores e antecessores, padrão Cadeia de responsabilidade
   //Esta implementação permite seguir duas direções na cadeia
   private ChainOfResponsibility sucessor, antecessor;

   public ChainOfResponsibility getSucessor() {
      return sucessor;
   }

   public void setSucessor(ChainOfResponsibility sucessor) {
      this.sucessor = sucessor;
   }

   public ChainOfResponsibility getAntecessor() {
      return antecessor;
   }

   public void setAntecessor(ChainOfResponsibility antecessor) {
      this.antecessor = antecessor;
   }

   abstract public int[] dicaTroco(BigDecimal valor);

}
