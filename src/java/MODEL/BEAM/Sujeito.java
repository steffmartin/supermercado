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
public interface Sujeito {

   public void registrarObservador(Observador o);

   public void removerObservador(Observador o);

   public void notificarObservadores();

}
