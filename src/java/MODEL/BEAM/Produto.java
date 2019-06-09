/*
 * Trabalho de Programacao Orientada a Objetos 2
 * Grupo:
 * 11511BSI267 - Heitor H. Nunes
 * 11411BSI207 - Matheus Eduardo da S. Ramos
 * 11511BSI257 - Pedro Henrique da Silva
 * 11511BSI215 - Steffan M.  Alves
 */
package MODEL.BEAM;

import java.util.ArrayList;

/**
 *
 * @author steff
 */
public class Produto implements Sujeito {

   private int id;
   private float qtd_disponivel, qtd_aviso, preco_venda, preco_compra;
   private String descricao, un_medida, cod_barras;
   private boolean notificar;
   private ArrayList<Observador> observadores;

   public Produto(float qtd_disponivel, float qtd_aviso, String cod_barras, String descricao, String un_medida, float preco_venda, float preco_compra, boolean notificar) {
      this.qtd_disponivel = qtd_disponivel;
      this.qtd_aviso = qtd_aviso;
      this.cod_barras = cod_barras;
      this.descricao = descricao;
      this.un_medida = un_medida;
      this.preco_venda = preco_venda;
      this.preco_compra = preco_compra;
      this.notificar = notificar;
      observadores = new ArrayList<>();
      observadores.add(Central.getInstancia());
   }

   //Gets & Setters
   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
      notificarObservadores();
   }

   public float getQtd_disponivel() {
      return qtd_disponivel;
   }

   public void setQtd_disponivel(float qtd_disponivel) {
      if(this.qtd_disponivel != qtd_disponivel)
      {
         this.qtd_disponivel = qtd_disponivel;
         notificarObservadores();
      }
   }

   public float getQtd_aviso() {
      return qtd_aviso;
   }

   public void setQtd_aviso(float qtd_aviso) {
      if(this.qtd_aviso != qtd_aviso)
      {
         this.qtd_aviso = qtd_aviso;
         notificarObservadores();
      }
   }

   public String getCod_barras() {
      return cod_barras;
   }

   public void setCod_barras(String cod_barras) {
      this.cod_barras = cod_barras;
   }

   public String getDescricao() {
      return descricao;
   }

   public void setDescricao(String descricao) {
      this.descricao = descricao;
   }

   public String getUn_medida() {
      return un_medida;
   }

   public void setUn_medida(String un_medida) {
      this.un_medida = un_medida;
   }

   public float getPreco_venda() {
      return preco_venda;
   }

   public void setPreco_venda(float preco_venda) {
      this.preco_venda = preco_venda;
   }

   public float getPreco_compra() {
      return preco_compra;
   }

   public void setPreco_compra(float preco_compra) {
      this.preco_compra = preco_compra;
   }

   public boolean getNotificar() {
      return notificar;
   }

   public void setNotificar(boolean notificar) {
      if(this.notificar != notificar)
      {
         this.notificar = notificar;
         notificarObservadores();
      }
   }

   @Override
   public void registrarObservador(Observador o) {
      observadores.add(o);
   }

   @Override
   public void removerObservador(Observador o) {
      observadores.remove(o);
   }

   @Override
   public void notificarObservadores() {

      for(Observador o : observadores)
         if(notificar)
            o.atualizar(id, qtd_disponivel, qtd_aviso);
         else
            o.atualizar(id, (float) 1, (float) 0);
   }

}
