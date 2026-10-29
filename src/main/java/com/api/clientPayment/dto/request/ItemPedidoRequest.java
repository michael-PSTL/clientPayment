package com.api.clientPayment.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemPedidoRequest {

    private String produtoId;
    private Integer quantidade;

}