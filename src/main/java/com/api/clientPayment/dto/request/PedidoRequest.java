package com.api.clientPayment.dto.request;

import com.api.clientPayment.enums.FormaPagamento;
import com.api.clientPayment.enums.TipoRecebimento;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PedidoRequest {

    private String lanchoneteId;
    private String nomeCliente;
    private String telefoneCliente;
    private String emailCliente;
    private FormaPagamento formaPagamento;
    private TipoRecebimento tipoRecebimento;
    private String observacao;
    private EnderecoRequest endereco;
    private List<ItemPedidoRequest> itens;

}