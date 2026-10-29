
package com.api.clientPayment.dto.mercadopago;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ConsultarPagamentoResponse {

    private Long id;

    private String status;

    @JsonProperty("status_detail")
    private String statusDetail;

    @JsonProperty("external_reference")
    private String externalReference;

    @JsonProperty("transaction_amount")
    private BigDecimal transactionAmount;

    @JsonProperty("transaction_amount_refunded")
    private BigDecimal transactionAmountRefunded;

    @JsonProperty("currency_id")
    private String currencyId;

    @JsonProperty("date_approved")
    private String dateApproved;

    @JsonProperty("payment_method_id")
    private String paymentMethodId;
}
