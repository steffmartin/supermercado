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
import java.util.ArrayList;
import java.util.Locale;

/**
 *
 * @author steff
 */
public class Venda {

   private int id, qtd_itens, seq, parcelas;
   private float total_bruto, total_descontos, total_liquido, valor_recebido, troco, quebra_de_caixa;
   private String forma_pagamento, cupom, vendedor;
   private LocalDateTime datahora;
   public static final DateTimeFormatter datahorabrasil = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(new Locale("pt", "br"));
   private ArrayList<Object[]> lista; // Lista de Object[] pois quero guardar Produto e Float representando a qtde vendida

   //Método para abrir uma venda, já precisa saber de início o ID que esta venda terá para imprimir isto no cupom
   public Venda(int id, String vendedor) {
      this.id = id;
      datahora = LocalDateTime.now();
      lista = new ArrayList<>();
      total_bruto = total_descontos = total_liquido = valor_recebido = troco = quebra_de_caixa = 0;
      qtd_itens = seq = 0;
      parcelas = 1;
      this.vendedor = vendedor;
      this.forma_pagamento = "Não definido";
      Central central = Central.getInstancia();
      //Cuidado com o ESPAÇAMENTO de caracteres abaixo para não estragar a formatação do cupom
      cupom = ""
              + central.getEmpresa() + "\n"
              + "\n"
              + central.getEndereco() + "\n"
              + central.getCidade() + " - " + central.getEstado() + " - CEP " + central.getCep() + "\n"
              + "\n"
              + "CNPJ: " + central.getCnpj() + "\n"
              + "IE: " + central.getIe() + "\n"
              + "IM: " + central.getIm() + "\n"
              + "_______________________________________________\n\n"
              + "" + datahora.format(datahorabrasil) + "                   COO: " + ("000000000" + id).substring(("" + id).length(), ("" + id).length() + 9) + "\n"
              + "\n"
              + "*** CUPOM SEM VALOR FISCAL ***\n"
              + "\n"
              + "SEQ.  CÓDIGO  DESCRIÇÃO                        \n"
              + "QTD.     UN.  VL.UNIT(R$)      VL.ITEM(R$)     \n"
              + "_______________________________________________\n\n";
   }

   //Métodos para a operação de venda
   //Inserir produto na venda
   public boolean insereProduto(Produto p, float quantidade) {
      if(p.getQtd_disponivel() >= quantidade)
      {
         p.setQtd_disponivel(p.getQtd_disponivel() - quantidade);

         //Verifica se já existe, atualiza ou insere um novo
         boolean existe = false;

         for(Object[] o : lista)
            if(((Produto) o[0]).getId() == p.getId())
            {
               int i = lista.indexOf(o);
               o[0] = p;
               o[1] = ((float) o[1]) + quantidade;
               lista.set(i, o);
               existe = true;
               break;
            }

         if(!existe)
            lista.add(new Object[]
            {
               p, quantidade
            });

         this.setQtd_itens(this.lista.size());
         seq++;
         this.atualizaCupom(toCupom(p, quantidade));
         this.setTotal_bruto(total_bruto + (p.getPreco_venda() * quantidade));
         this.setTotal_liquido(total_liquido + (p.getPreco_venda() * quantidade));
         return true;
      }
      else
         return false;
   }

   //Remover produto na venda (só remove caso ele exista na venda)
   public boolean removeProduto(Produto p, float quantidade) {
      if(!lista.isEmpty())
      {
         //Se solicitou remoção de mais do que foi inserido, remove tudo que está inserido e não retorna erro
         for(Object[] o : lista)
            if(((Produto) o[0]).getId() == p.getId())
            {
               int i = lista.indexOf(o);
               if(((float) o[1]) <= quantidade)
               {
                  quantidade = ((float) o[1]);
                  p.setQtd_disponivel(p.getQtd_disponivel() + quantidade);
                  lista.remove(i);
               }
               else
               {
                  p.setQtd_disponivel(p.getQtd_disponivel() + quantidade);
                  o[0] = p;
                  o[1] = ((float) o[1]) - quantidade;
                  lista.set(i, o);
               }
               this.setQtd_itens(this.lista.size());
               seq++;
               this.atualizaCupom("CANCELAMENTO:                                  \n" + toCupom(p, -quantidade));
               this.setTotal_bruto(total_bruto - (p.getPreco_venda() * quantidade));
               this.setTotal_liquido(total_liquido - (p.getPreco_venda() * quantidade));
               return true;
            }
         return false;
      }
      else
         return false;
   }

   //Método que imprime o produto no formato do cupom fiscal
   public String toCupom(Produto p, float qtd) {

      return (seq + "    ").substring(0, 4) + "  " + (p.getId() + "      ").substring(0, 6) + "  " + (p.getDescricao() + "                                 ").substring(0, 33) + "\n"
              + (qtd + "       ").substring(0, 7) + "  " + p.getUn_medida() + " * " + (p.getPreco_venda() + "                       ").substring(0, 15) + "  " + (String.format("%.2f", (p.getPreco_venda() * qtd)) + "                ").substring(0, 16) + "\n";
   }

   //Método para conceder desconto
   //Desconto maior que o valor da compra, concede desconto no valor da compra e não retorna erro
   public void concedeDesconto(float desconto) {
      if(total_liquido < desconto)
         desconto = total_liquido;

      if(desconto == 0)
         return;

      if(desconto<0)
         this.atualizaCupom("CANCELAMENTO DESCONTO:         " + (String.format("%.2f", -desconto) + "                ").substring(0, 16) + "\n");
      else
         this.atualizaCupom("DESCONTO:                      " + (String.format("%.2f", -desconto) + "                ").substring(0, 16) + "\n");
      this.setTotal_descontos(total_descontos + desconto);
      this.setTotal_liquido(total_liquido - desconto);
   }

   //Método para cancelar a venda
   public void cancelaVenda() {//LEMBRAR DE REGULARIZAR A QUANTIDADE DOS PRODUTOS NO BANCO DE DADOS
      
      //Desfaz descontos concedidos
      this.concedeDesconto(-total_descontos);
      
      //Remove todos produtos da venda
      ArrayList<Object[]> listatemp = new ArrayList<>(this.getLista());
      for(Object[] o : listatemp)
         removeProduto(((Produto) o[0]), (float) o[1]);

      //Zera os totais (previnir erro no float) e seta restante dos dados
      this.setQtd_itens(0);
      this.setTotal_bruto(0);
      this.setTotal_descontos(0);
      this.setTotal_liquido(0);
      this.setValor_recebido(0);
      this.setTroco(0);
      this.setQuebra_de_caixa(0);
      this.setForma_pagamento("Cancelada");
   }

   //Método para imprimir dados no cupom
   public void atualizaCupom(String texto) {
      this.cupom += texto;
   }

   //Método para fechar a venda
   public void fechaVenda() {
      String rodape = ""
              + "_______________________________________________\n\n"
              + "TOTAL BRUTO (R$)               " + (String.format("%.2f", total_bruto) + "                ").substring(0, 16) + "\n"
              + "DESCONTOS (R$)                 " + (String.format("%.2f", total_descontos) + "                ").substring(0, 16) + "\n"
              + "TOTAL A PAGAR (R$)             " + (String.format("%.2f", total_liquido) + "                ").substring(0, 16) + "\n"
              + "VALOR RECEBIDO (R$)            " + (String.format("%.2f", valor_recebido) + "                ").substring(0, 16) + "\n"
              + "TROCO (R$)                     " + (String.format("%.2f", troco) + "                ").substring(0, 16) + "\n"
              + "\n"
              + "FORMA DE PAGAMENTO: " + forma_pagamento + "\n"
              + "_______________________________________________\n\n"
              + "Vendedor: " + vendedor + "\n\n"
              + "Obrigado, volte sempre!";
      this.atualizaCupom(rodape);
   }

   //Gets & Setters
   public int getId() {
      return id;
   }

   public float getTotal_bruto() {
      return total_bruto;
   }

   public void setTotal_bruto(float total_bruto) {
      this.total_bruto = total_bruto;
   }

   public float getTotal_descontos() {
      return total_descontos;
   }

   public void setTotal_descontos(float total_descontos) {
      this.total_descontos = total_descontos;
   }

   public float getTotal_liquido() {
      return total_liquido;
   }

   public void setTotal_liquido(float total_liquido) {
      this.total_liquido = total_liquido;

      if(total_liquido < 0)
      {
         this.atualizaCupom("CANCELAMENTO:                                  \n");
         this.concedeDesconto(total_liquido);
      }

   }

   public float getValor_recebido() {
      return valor_recebido;
   }

   public void setValor_recebido(float valor_recebido) {
      this.valor_recebido = valor_recebido;
   }

   public float getTroco() {
      return troco;
   }

   public void setTroco(float troco) {
      this.troco = troco;
   }

   public float getQuebra_de_caixa() {
      return quebra_de_caixa;
   }

   public void setQuebra_de_caixa(float quebra_de_caixa) {
      this.quebra_de_caixa = quebra_de_caixa;
   }

   public String getForma_pagamento() {
      return forma_pagamento;
   }

   public void setForma_pagamento(String forma_pagamento) {
      this.forma_pagamento = forma_pagamento;
   }

   public String getCupom() {
      return cupom;
   }

   public void setCupom(String cupom) {
      this.cupom = cupom;
   }

   public String getDatahoraFormatada() {
      return datahora.format(datahorabrasil);
   }

   public LocalDateTime getDatahora() {
      return datahora;
   }

   public void setDatahora(LocalDateTime datahora) {
      this.datahora = datahora;
   }

   public ArrayList<Object[]> getLista() {
      return lista;
   }

   public void setLista(ArrayList<Object[]> lista) {
      this.lista = lista;
   }

   public int getQtd_itens() {
      return qtd_itens;
   }

   public void setQtd_itens(int qtd_itens) {
      this.qtd_itens = qtd_itens;
   }

   public String getVendedor() {
      return vendedor;
   }

   public void setVendedor(String vendedor) {
      this.vendedor = vendedor;
   }

   public int getParcelas() {
      return parcelas;
   }

   public void setParcelas(int parcelas) {
      this.parcelas = parcelas;
   }

}
