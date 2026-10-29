package com.api.clientPayment.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartaoRequest {

    private String token;
    private String metodoPagamento;
    private Integer parcelas;
    private String email;
}