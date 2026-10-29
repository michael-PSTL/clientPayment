package com.api.clientPayment.entity;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemCardapio {

    private String idName;
    private String nome;
    private BigDecimal preco;

}