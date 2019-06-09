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
public class RelatorioObservados extends Relatorio {

   public RelatorioObservados(String nome, ArrayList<Produto> lista) {
      this.setNome(nome);
      this.setDatahora(LocalDateTime.now());
      String conteudo = "Relatório: " + this.getNome() + "\n"
              + "Data de Emissão: " + this.getDatahoraFormatada() + "\n\n";

      if(lista.isEmpty())
         conteudo += "Nenhum dado para exibir.";
      else
      {
         conteudo += "ID     DESCRIÇÃO                                                   DISPONÍVEL    Q. MÍNIMA\n\n";

         for(Produto p : lista)
            conteudo += ("0000" + p.getId()).substring(("" + p.getId()).length(), ("" + p.getId()).length() + 4) + "   "
                    + (p.getDescricao() + "                                                                                                    ").substring(0, 57) + "   "
                    + ("           " + String.format("%.4f", p.getQtd_disponivel())).substring(("" + String.format("%.4f", p.getQtd_disponivel())).length(), ("" + String.format("%.4f", p.getQtd_disponivel())).length() + 10) + "   "
                    + ("           " + String.format("%.4f", p.getQtd_aviso())).substring(("" + String.format("%.4f", p.getQtd_aviso())).length(), ("" + String.format("%.4f", p.getQtd_aviso())).length() + 10) + "\n";
      }
      this.setConteudo(conteudo);
   }

}
