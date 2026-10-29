package com.api.clientPayment.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PagamentoResponse {

    private PixResponse pix;
    private CartaoResponse cartao;
}