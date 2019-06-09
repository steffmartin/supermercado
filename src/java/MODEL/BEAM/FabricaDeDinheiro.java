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
import java.util.ArrayList;

/**
 *
 * @author steff
 */
public class FabricaDeDinheiro {
   
   private ArrayList<Dinheiro> disponibilidade;
   private Dinheiro n_cem, n_cinquenta, n_vinte, n_dez, n_cinco, n_dois,
           m_um, m_cinquenta, m_vintecinco, m_dez, m_cinco, m_umcentavo;

   //Método para criar a Cadeia de Responsabilidade para cálculo do troco
   public Dinheiro criaDinheiro(int[] disponibilidade) {
      
      this.disponibilidade = new ArrayList<>();
      
      if(disponibilidade[0] > 0)
      {
         n_cem = new Dinheiro(new BigDecimal("100"), disponibilidade[0], 0);
         this.disponibilidade.add(n_cem);
      }
      if(disponibilidade[1] > 0)
      {
         n_cinquenta = new Dinheiro(new BigDecimal("50"), disponibilidade[1], 1);
         this.disponibilidade.add(n_cinquenta);
      }
      if(disponibilidade[2] > 0)
      {
         n_vinte = new Dinheiro(new BigDecimal("20"), disponibilidade[2], 2);
         this.disponibilidade.add(n_vinte);
      }
      if(disponibilidade[3] > 0)
      {
         n_dez = new Dinheiro(new BigDecimal("10"), disponibilidade[3], 3);
         this.disponibilidade.add(n_dez);
      }
      if(disponibilidade[4] > 0)
      {
         n_cinco = new Dinheiro(new BigDecimal("5"), disponibilidade[4], 4);
         this.disponibilidade.add(n_cinco);
      }
      if(disponibilidade[5] > 0)
      {
         n_dois = new Dinheiro(new BigDecimal("2"), disponibilidade[5], 5);
         this.disponibilidade.add(n_dois);
      }
      if(disponibilidade[6] > 0)
      {
         m_um = new Dinheiro(new BigDecimal("1"), disponibilidade[6], 6);
         this.disponibilidade.add(m_um);
      }
      if(disponibilidade[7] > 0)
      {
         m_cinquenta = new Dinheiro(new BigDecimal("0.5"), disponibilidade[7], 7);
         this.disponibilidade.add(m_cinquenta);
      }
      if(disponibilidade[8] > 0)
      {
         m_vintecinco = new Dinheiro(new BigDecimal("0.25"), disponibilidade[8], 8);
         this.disponibilidade.add(m_vintecinco);
      }
      if(disponibilidade[9] > 0)
      {
         m_dez = new Dinheiro(new BigDecimal("0.1"), disponibilidade[9], 9);
         this.disponibilidade.add(m_dez);
      }
      if(disponibilidade[10] > 0)
      {
         m_cinco = new Dinheiro(new BigDecimal("0.05"), disponibilidade[10], 10);
         this.disponibilidade.add(m_cinco);
      }
      if(disponibilidade[11] > 0)
      {
         m_umcentavo = new Dinheiro(new BigDecimal("0.01"), disponibilidade[11], 11);
         this.disponibilidade.add(m_umcentavo);
      }
      
      if(this.disponibilidade.isEmpty())
         return null;

      //Determinar os sucessores
      for(Dinheiro d : this.disponibilidade)
         if(this.disponibilidade.indexOf(d) > 0)
            this.disponibilidade.get(this.disponibilidade.indexOf(d) - 1).setSucessor(d);

      //Determinar os antecessores
      for(Dinheiro d : this.disponibilidade)
         if(this.disponibilidade.indexOf(d) < this.disponibilidade.size()-1)
            this.disponibilidade.get(this.disponibilidade.indexOf(d) + 1).setAntecessor(d);

      //Retornar o primeiro 'dinheiro' da cadeia (o de maior valor)
      return this.disponibilidade.get(0);
   }
   
}
