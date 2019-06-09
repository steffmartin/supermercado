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
public class Central implements Observador {

   // Variável com instância única - Padrão SINGLETON
   private volatile static Central instancia;
   private Usuario logado;
   private int[] caixa, arraytroco, arrayrecebido;
   private Venda venda_ativa;
   private ArrayList<Integer> estoque_baixo;
   private String empresa, endereco, cidade, estado, cep, cnpj, ie, im = "";

   // Construtor privado
   private Central() {
      estoque_baixo = new ArrayList<>();
      caixa = new int[]
      {
         0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
      };
   }

   // Método para retornar instância única
   public static Central getInstancia() {
      if(instancia == null)
         synchronized(Central.class)
         {
            if(instancia == null)
               instancia = new Central();
         }
      return instancia;
   }

   // Método para fechar a central
   public static Central fechaInstancia() {
      instancia = null;
      return instancia;
   }

   //Método que atualiza a lista dos produtos com estoque baixo - PADRÃO OBSERVER
   @Override
   public synchronized void atualizar(int id, float qtd_disponivel, float qtd_aviso) {
      if(qtd_disponivel <= qtd_aviso)
      {
         if(!getEstoque_baixo().contains(id))
            getEstoque_baixo().add(id);
      }
      else if(getEstoque_baixo().contains(id))
         getEstoque_baixo().remove((Integer) id);
   }

   //Métodos para sacar e depositar dinheiro no caixa
   public void depositarCaixa(int[] deposito) {
      caixa[0] += deposito[0];
      caixa[1] += deposito[1];
      caixa[2] += deposito[2];
      caixa[3] += deposito[3];
      caixa[4] += deposito[4];
      caixa[5] += deposito[5];
      caixa[6] += deposito[6];
      caixa[7] += deposito[7];
      caixa[8] += deposito[8];
      caixa[9] += deposito[9];
      caixa[10] += deposito[10];
      caixa[11] += deposito[11];
   }

   public void sacarCaixa(int[] saque) {
      caixa[0] -= saque[0];
      caixa[1] -= saque[1];
      caixa[2] -= saque[2];
      caixa[3] -= saque[3];
      caixa[4] -= saque[4];
      caixa[5] -= saque[5];
      caixa[6] -= saque[6];
      caixa[7] -= saque[7];
      caixa[8] -= saque[8];
      caixa[9] -= saque[9];
      caixa[10] -= saque[10];
      caixa[11] -= saque[11];
   }

   //Gets & Setters
   public Usuario getLogado() {
      return logado;
   }

   public void setLogado(Usuario logado) {
      this.logado = logado;
   }

   public ArrayList<Integer> getEstoque_baixo() {
      return estoque_baixo;
   }

   public void setEstoque_baixo(ArrayList<Integer> estoque_baixo) {
      this.estoque_baixo = estoque_baixo;
   }

   public String getEmpresa() {
      return empresa;
   }

   public void setEmpresa(String empresa) {
      this.empresa = empresa;
   }

   public String getEndereco() {
      return endereco;
   }

   public void setEndereco(String endereco) {
      this.endereco = endereco;
   }

   public String getCidade() {
      return cidade;
   }

   public void setCidade(String cidade) {
      this.cidade = cidade;
   }

   public String getEstado() {
      return estado;
   }

   public void setEstado(String estado) {
      this.estado = estado;
   }

   public String getCep() {
      return cep;
   }

   public void setCep(String cep) {
      this.cep = cep;
   }

   public String getCnpj() {
      return cnpj;
   }

   public void setCnpj(String cnpj) {
      this.cnpj = cnpj;
   }

   public String getIe() {
      return ie;
   }

   public void setIe(String ie) {
      this.ie = ie;
   }

   public String getIm() {
      return im;
   }

   public void setIm(String im) {
      this.im = im;
   }

   public int[] getCaixa() {
      return caixa;
   }

   public void setCaixa(int[] caixa) {
      this.caixa = caixa;
   }

   public Venda getVenda_ativa() {
      return venda_ativa;
   }

   public void setVenda_ativa(Venda venda_ativa) {
      this.venda_ativa = venda_ativa;
   }
   
   public int[] getArraytroco() {
      return arraytroco;
   }

   public void setArraytroco(int[] arraytroco) {
      this.arraytroco = arraytroco;
   }

   public int[] getArrayrecebido() {
      return arrayrecebido;
   }

   public void setArrayrecebido(int[] arrayrecebido) {
      this.arrayrecebido = arrayrecebido;
   }

}
