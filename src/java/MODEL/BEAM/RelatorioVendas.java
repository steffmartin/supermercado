/*
 * Trabalho de Programacao Orientada a Objetos 2
 * Grupo:
 * 11511BSI267 - Heitor H. Nunes
 * 11411BSI207 - Matheus Eduardo da S. Ramos
 * 11511BSI257 - Pedro Henrique da Silva
 * 11511BSI215 - Steffan M.  Alves
 */
package MODEL.BEAM;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.function.Predicate;

/**
 *
 * @author steff
 */
public class RelatorioVendas extends Relatorio {
   
   private VendasCanceladas<Venda> filtro1;
   private VendaDentroPeriodo<Venda> filtro2;

   public RelatorioVendas(String nome, ArrayList<Venda> lista, LocalDateTime de, LocalDateTime ate, boolean canceladas) {
      this.setNome(nome);
      this.setDatahora(LocalDateTime.now());
      String conteudo = "Relatório: " + this.getNome() + "\n"
              + "Data de Emissão: " + this.getDatahoraFormatada() + "\n\n";

      if(!canceladas)
      {
         filtro1 = new VendasCanceladas<>();
         lista.removeIf(filtro1);
      }

      filtro2 = new VendaDentroPeriodo<>();
      filtro2.de = de;
      filtro2.ate = ate;
      lista.removeIf(filtro2);

      if(lista.isEmpty())
         conteudo += "Nenhum dado para exibir.";
      else
      {
         conteudo += "ID        DATA/HORA      QTD. VL. BRUTO DESCONTOS V.LIQUIDO QUEBRA CX VENDEDOR\n\n";

         float b, d, l, q;
         b = d = l = q = 0;

         for(Venda v : lista)
         {
            conteudo += ("000000000" + v.getId()).substring(("" + v.getId()).length(), ("" + v.getId()).length() + 9) + " "
                    + v.getDatahoraFormatada() + " "
                    + ("    " + v.getQtd_itens()).substring(("" + v.getQtd_itens()).length(), ("" + v.getQtd_itens()).length() + 4) + " "
                    + ("         " + String.format("%.2f", v.getTotal_bruto())).substring(("" + String.format("%.2f", v.getTotal_bruto())).length(), ("" + String.format("%.2f", v.getTotal_bruto())).length() + 9) + " "
                    + ("         " + String.format("%.2f", v.getTotal_descontos())).substring(("" + String.format("%.2f", v.getTotal_descontos())).length(), ("" + String.format("%.2f", v.getTotal_descontos())).length() + 9) + " "
                    + ("         " + String.format("%.2f", v.getTotal_liquido())).substring(("" + String.format("%.2f", v.getTotal_liquido())).length(), ("" + String.format("%.2f", v.getTotal_liquido())).length() + 9) + " "
                    + ("         " + String.format("%.2f", v.getQuebra_de_caixa())).substring(("" + String.format("%.2f", v.getQuebra_de_caixa())).length(), ("" + String.format("%.2f", v.getQuebra_de_caixa())).length() + 9) + " "
                    + (v.getVendedor().toUpperCase() + "                    ").substring(0, 20) + "\n";
            b += v.getTotal_bruto();
            d += v.getTotal_descontos();
            l += v.getTotal_liquido();
            q += v.getQuebra_de_caixa();
         }

         conteudo += "\nTOTAIS                        "
                 + ("         " + String.format("%.2f", b)).substring(("" + String.format("%.2f", b)).length(), ("" + String.format("%.2f", b)).length() + 9) + " "
                 + ("         " + String.format("%.2f", d)).substring(("" + String.format("%.2f", d)).length(), ("" + String.format("%.2f", d)).length() + 9) + " "
                 + ("         " + String.format("%.2f", l)).substring(("" + String.format("%.2f", l)).length(), ("" + String.format("%.2f", l)).length() + 9) + " "
                 + ("         " + String.format("%.2f", q)).substring(("" + String.format("%.2f", q)).length(), ("" + String.format("%.2f", q)).length() + 9) + " ";
      }
      this.setConteudo(conteudo);
   }

}

class VendasCanceladas<t> implements Predicate<t> {

   @Override
   public boolean test(t x) {
      return ((Venda) x).getForma_pagamento().equalsIgnoreCase("Cancelada");
   }

}

class VendaDentroPeriodo<t> implements Predicate<t> {

   LocalDateTime de;
   LocalDateTime ate;

   @Override
   public boolean test(t x) {
      return ((Venda) x).getDatahora().isBefore(de) || ((Venda) x).getDatahora().isAfter(ate);
   }

}
