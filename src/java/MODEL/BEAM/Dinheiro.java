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
import java.util.Locale;

/**
 *
 * @author steff
 */
public class Dinheiro extends ChainOfResponsibility {

   private BigDecimal valor;
   private int qtd, indice;
   private static int[] troco;

   public Dinheiro(BigDecimal valor, int qtd, int indice) {
      this.valor = valor;
      this.qtd = qtd;
      this.indice = indice;
      this.setAntecessor(null);
      this.setSucessor(null);
   }

   //Método que retorna um array com o melhor troco possível para o cliente
   public int[] dicaTroco(String valor) {
      return this.dicaTroco(new BigDecimal(valor));
   }

   public int[] dicaTroco(Float valor) {
      return this.dicaTroco(new BigDecimal(String.format(Locale.US, "%.2f", valor)));
   }

   public int[] dicaTroco(BigDecimal valor) {

      //Se é a primeira execução, inicializa com zero o array de troco
      if(this.getAntecessor() == null)
         this.troco = new int[]
         {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
         };

      //Calcula o troco e salva no array
      int resultado = (valor.divide(this.valor)).intValue();
      if(resultado > this.qtd)
         resultado = this.qtd;
      troco[this.indice] = resultado;
      valor = valor.subtract(this.valor.multiply(new BigDecimal(Integer.toString(resultado))));

      // Atingiu o troco, devolve o array 
      if(valor.compareTo(new BigDecimal("0")) <= 0)
         return troco;

      // Não passou todo troco, continua na cadeia
      // No final retorna o array com o troco melhor possível
      if(this.getSucessor() != null)
         return this.getSucessor().dicaTroco(valor);
      else
         return troco;
   }

   public static float converteFloat(int[] valor) {
      BigDecimal soma = new BigDecimal("0");
      soma = soma.add(new BigDecimal("100").multiply(new BigDecimal(valor[0])));
      soma = soma.add(new BigDecimal("50").multiply(new BigDecimal(valor[1])));
      soma = soma.add(new BigDecimal("20").multiply(new BigDecimal(valor[2])));
      soma = soma.add(new BigDecimal("10").multiply(new BigDecimal(valor[3])));
      soma = soma.add(new BigDecimal("5").multiply(new BigDecimal(valor[4])));
      soma = soma.add(new BigDecimal("2").multiply(new BigDecimal(valor[5])));
      soma = soma.add(new BigDecimal("1").multiply(new BigDecimal(valor[6])));
      soma = soma.add(new BigDecimal("0.5").multiply(new BigDecimal(valor[7])));
      soma = soma.add(new BigDecimal("0.25").multiply(new BigDecimal(valor[8])));
      soma = soma.add(new BigDecimal("0.1").multiply(new BigDecimal(valor[9])));
      soma = soma.add(new BigDecimal("0.05").multiply(new BigDecimal(valor[10])));
      soma = soma.add(new BigDecimal("0.01").multiply(new BigDecimal(valor[11])));
      return soma.floatValue();
   }
   
}
