package com.api.clientPayment.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mercado {

    private String idName;
    private String phone;
    private String nome;

    private String mercadoPagoAccessToken;
    private String mercadoPagoPublicKey;

    private List<ItemCardapio> cardapio = new ArrayList<>();
}