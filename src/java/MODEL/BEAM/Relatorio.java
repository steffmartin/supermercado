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
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 *
 * @author steff
 */
public abstract class Relatorio {
   
   private String nome, conteudo;
   private LocalDateTime datahora;
   public static final DateTimeFormatter datahorabrasil = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(new Locale("pt", "br"));

   public String getNome() {
      return nome;
   }

   public String getConteudo() {
      return conteudo;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }

   public void setConteudo(String conteudo) {
      this.conteudo = conteudo;
   }

   public LocalDateTime getDatahora() {
      return datahora;
   }
   
   public String getDatahoraFormatada() {
      return datahora.format(datahorabrasil);
   }

   public void setDatahora(LocalDateTime datahora) {
      this.datahora = datahora;
   }
   
}
