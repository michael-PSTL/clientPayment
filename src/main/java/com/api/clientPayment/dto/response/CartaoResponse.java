package com.api.clientPayment.dto.response;

import com.api.clientPayment.enums.StatusPagamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CartaoResponse {

    private String pagamentoId;
    private StatusPagamento status;
    private BigDecimal valor;
    private String urlPagamento;
    private String publicKey;
}