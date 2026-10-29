
package com.api.clientPayment.service.payment;

import com.api.clientPayment.dto.request.PedidoRequest;
import com.api.clientPayment.dto.response.PixResponse;
import com.api.clientPayment.entity.Mercado;
import com.api.clientPayment.enums.StatusPagamento;
import com.api.clientPayment.repository.MercadoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PixService {

    private final RestClient restClient;
    private final MercadoRepository mercadoRepository;

    public PixResponse pixPagamento(
            PedidoRequest pedido,
            BigDecimal valor
    ) {

        Mercado mercado = mercadoRepository
                .buscarMercadoPorIdName(pedido.getLanchoneteId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Mercado não encontrado."
                ));

        if (mercado.getMercadoPagoAccessToken() == null
                || mercado.getMercadoPagoAccessToken().isBlank()) {

            throw new IllegalStateException(
                    "Access Token do Mercado Pago não configurado."
            );
        }

        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "O valor do pagamento deve ser positivo."
            );
        }

        if (
                pedido.getEmailCliente() == null
                        || pedido.getEmailCliente().isBlank()
        ) {
            throw new IllegalArgumentException(
                    "O e-mail do cliente é obrigatório para criar o pagamento."
            );
        }

        Map<String, Object> requisicao = Map.of(
                "transaction_amount", valor,
                "description", "Pedido - " + mercado.getNome(),
                "payment_method_id", "pix",
                "payer", Map.of(
                        "email", pedido.getEmailCliente()
                )
        );

        JsonNode resposta = restClient.post()
                .uri("https://api.mercadopago.com/v1/payments")
                .header(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer " + mercado.getMercadoPagoAccessToken()
                )
                .header(
                        "X-Idempotency-Key",
                        UUID.randomUUID().toString()
                )
                .contentType(MediaType.APPLICATION_JSON)
                .body(requisicao)
                .retrieve()
                .body(JsonNode.class);

        if (resposta == null) {
            throw new IllegalStateException(
                    "O Mercado Pago não retornou uma resposta."
            );
        }

        JsonNode pix = resposta.path("point_of_interaction")
                .path("transaction_data");

        PixResponse response = new PixResponse();

        response.setPagamentoId(
                resposta.path("id").asText()
        );

        response.setStatus(
                converterStatus(resposta.path("status").asText())
        );

        response.setValor(
                resposta.path("transaction_amount").decimalValue()
        );

        response.setQrCode(
                textoOuNull(pix, "qr_code")
        );

        response.setQrCodeBase64(
                textoOuNull(pix, "qr_code_base64")
        );

        response.setTicketUrl(
                textoOuNull(pix, "ticket_url")
        );

        return response;
    }

    private StatusPagamento converterStatus(String status) {

        try {
            return StatusPagamento.valueOf(status.toUpperCase());

        } catch (IllegalArgumentException | NullPointerException exception) {
            return StatusPagamento.UNKNOWN;
        }
    }

    private String textoOuNull(JsonNode node, String campo) {

        JsonNode valor = node.path(campo);

        return valor.isMissingNode() || valor.isNull()
                ? null
                : valor.asText();
    }
}
