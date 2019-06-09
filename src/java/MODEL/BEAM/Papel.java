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
public abstract class Papel extends Usuario {

   //Atributo do tipo PAI - Usando aqui o padrão decorator
   private Usuario camada;

   public void setCamada(Usuario camada) {
      this.camada = camada;
   }

   public Usuario getCamada() {
      return camada;
   }

   //Método para remover uma decoração qualquer
   //Exemplo de chamada do método: usuarioteste = usuarioteste.removePapel("P_EfetuarVenda");
   public Usuario removePapel(String classe) {

      if(this.getClass().getSimpleName().equalsIgnoreCase(classe))
         return this.camada;
      else
      {
         this.camada = this.camada.removePapel(classe);
         return this;
      }

   }

   //Método para ver se o usuário tem um determinad papel
   public boolean temPapel(String classe) {
      if(this.getClass().getSimpleName().equalsIgnoreCase(classe))
         return true;
      return this.camada.temPapel(classe);
   }

   //Gets & Setters do usuário básico, chamar a camada até chegar nele!
   public String getNome() {
      return camada.getNome();
   }

   public void setNome(String nome) {
      camada.setNome(nome);
   }

   public String getUsuario() {
      return camada.getUsuario();
   }

   public void setUsuario(String usuario) {
      camada.setUsuario(usuario);
   }

   public String getSenha() {
      return camada.getSenha();
   }

   public void setSenha(String senha) {
      camada.setSenha(senha);
   }

   public int getId() {
      return camada.getId();
   }

   public void setId(int id) {
      camada.setId(id);
   }

}
