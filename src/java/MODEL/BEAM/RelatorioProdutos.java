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

/**
 *
 * @author steff
 */
public class RelatorioProdutos extends Relatorio {

   public RelatorioProdutos(String nome, ArrayList<Produto> lista) {
      this.setNome(nome);
      this.setDatahora(LocalDateTime.now());
      String conteudo = "Relatório: " + this.getNome() + "\n"
              + "Data de Emissão: " + this.getDatahoraFormatada() + "\n\n";

      if(lista.isEmpty())
         conteudo += "Nenhum dado para exibir.";
      else
      {
         conteudo += "ID   COD.BARRAS    DESCRIÇÃO                  UN DISPONIVEL Q. MINIMA  PÇ.COMPRA  PÇ.VENDA\n\n";

         for(Produto p : lista)
            conteudo += ("0000" + p.getId()).substring(("" + p.getId()).length(), ("" + p.getId()).length() + 4) + " "
                    + ("             " + p.getCod_barras()).substring(("" + p.getCod_barras()).length(), ("" + p.getCod_barras()).length() + 13) + " "
                    + (p.getDescricao().toUpperCase() + "                          ").substring(0, 26) + " "
                    + p.getUn_medida() + " "
                    + ("           " + String.format("%.4f", p.getQtd_disponivel())).substring(("" + String.format("%.4f", p.getQtd_disponivel())).length(), ("" + String.format("%.4f", p.getQtd_disponivel())).length() + 10) + " "
                    + ("           " + String.format("%.4f", p.getQtd_aviso())).substring(("" + String.format("%.4f", p.getQtd_aviso())).length(), ("" + String.format("%.4f", p.getQtd_aviso())).length() + 10) + " "
                    + ("         " + String.format("%.2f", p.getPreco_compra())).substring(("" + String.format("%.2f", p.getPreco_compra())).length(), ("" + String.format("%.2f", p.getPreco_compra())).length() + 9) + " "
                    + ("         " + String.format("%.2f", p.getPreco_venda())).substring(("" + String.format("%.2f", p.getPreco_venda())).length(), ("" + String.format("%.2f", p.getPreco_venda())).length() + 9) + "\n";
      }
      this.setConteudo(conteudo);
   }

}
