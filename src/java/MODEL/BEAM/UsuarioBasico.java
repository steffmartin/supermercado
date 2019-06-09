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
public class UsuarioBasico extends Usuario {
   
   private String nome, usuario, senha;
   private int id;

   public UsuarioBasico(String nome, String usuario, String senha) {
      this.nome = nome;
      this.usuario = usuario;
      this.senha = senha;
   }

   public String getNome() {
      return nome;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }

   public String getUsuario() {
      return usuario;
   }

   public void setUsuario(String usuario) {
      this.usuario = usuario;
   }

   public String getSenha() {
      return senha;
   }

   public void setSenha(String senha) {
      this.senha = senha;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }
   
}
