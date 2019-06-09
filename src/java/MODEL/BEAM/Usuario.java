/*
 * Trabalho de Programacao Orientada a Objetos 2
 * Grupo:
 * 11511BSI267 - Heitor H. Nunes
 * 11411BSI207 - Matheus Eduardo da S. Ramos
 * 11511BSI257 - Pedro Henrique da Silva
 * 11511BSI215 - Steffan M.  Alves
 */
package MODEL.BEAM;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author steff
 */
public abstract class Usuario implements Serializable {

   private String papel = "Tela principal.";

   public String getPapel() {
      return papel;
   }

   //Implementações abaixo apenas no Usuário Básico. O Decorator terá chamadas para o usuário básico.
   public abstract String getNome();

   public abstract void setNome(String nome);

   public abstract String getUsuario();

   public abstract void setUsuario(String usuario);

   public abstract String getSenha();

   public abstract void setSenha(String senha);

   public abstract int getId();

   public abstract void setId(int id);

   //Método para adicionar uma decoração
   //Exemplo: teste = teste.adicionaPapel(teste, "P_EfetuarVenda");
   public Usuario adicionaPapel(String classe) {
      Object o = null;
      try
      {
         o = Class.forName("MODEL.BEAM." + classe).newInstance();
      }
      catch(ClassNotFoundException | InstantiationException | IllegalAccessException e)
      {
         throw new RuntimeException(e);
      }
      //Evita adicionar o mesmo papel mais de uma vez
      if(!this.temPapel(classe))
      {
         ((Papel) o).setCamada(this);
         return (Papel) o;
      }
      else
         return this;
   }
   
   //Verifica se tem o papel, sobescrito no decorator
   public boolean temPapel(String classe) {
      return false;
   }

   //Método para remover uma decoração qualquer, sobescrito no decorator
   public Usuario removePapel(String classe) {
      return this;
   }

   //O Usuário é decorado, para guardar o objeto decorado no BD é preciso serializá-lo
   //Os métodos abaixo fazem esta serialização. Esta classe precisa implementar Serializable
   //Função adaptada de: http://stackoverflow.com/questions/5837698/converting-any-object-to-a-byte-array-in-java
   public static byte[] serializa(Usuario objeto_decorado) throws IOException {
      try(ByteArrayOutputStream b = new ByteArrayOutputStream())
      {
         try(ObjectOutputStream o = new ObjectOutputStream(b))
         {
            o.writeObject(objeto_decorado);
         }
         return b.toByteArray();
      }
   }

   public static Usuario deserializa(byte[] objeto_decorado) throws IOException, ClassNotFoundException {
      try(ByteArrayInputStream b = new ByteArrayInputStream(objeto_decorado))
      {
         try(ObjectInputStream o = new ObjectInputStream(b))
         {
            return (Usuario) o.readObject();
         }
      }
   }

}
