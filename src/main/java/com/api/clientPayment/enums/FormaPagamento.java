package com.api.clientPayment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FormaPagamento {

    DINHEIRO("Dinheiro"),
    CREDITO_ONLINE("Cartão de Crédito Online"),
    CREDITO_PRESENCIAL("Cartão de Crédito Presencialmente"),
    DEBITO_ONLINE("Cartão de Débito Online"),
    DEBITO_PRESENCIAL("Cartão de Débito Presencialmente"),
    PIX_ONLINE("Pix Online"),
    PIX_PRESENCIAL("Pix Presencialmente");

    private final String descricao;
}