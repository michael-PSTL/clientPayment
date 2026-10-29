
package com.api.clientPayment.dto.response;

import com.api.clientPayment.enums.StatusPagamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class PixResponse {

    private String pagamentoId;
    private StatusPagamento status;
    private BigDecimal valor;
    private String qrCode;
    private String qrCodeBase64;
    private String ticketUrl;
}
