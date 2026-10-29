package com.api.clientPayment.service.payment;

import com.api.clientPayment.dto.mercadopago.ConsultarPagamentoResponse;
import com.api.clientPayment.entity.Mercado;
import com.api.clientPayment.repository.MercadoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class ConsultarPagamentoService {

    private final RestTemplate restTemplate;
    private final MercadoRepository mercadoRepository;

    public ConsultarPagamentoResponse consultarPagamento(
            String pagamentoId,
            String lanchoneteId
    ) {

        Mercado mercado = mercadoRepository
                .buscarMercadoPorIdName(lanchoneteId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Mercado não encontrado."
                ));

        String accessToken = mercado.getMercadoPagoAccessToken();

        if (accessToken == null || accessToken.isBlank()) {
            throw new IllegalStateException(
                    "Access Token do Mercado Pago não configurado."
            );
        }

        String url = "https://api.mercadopago.com/v1/payments/"
                + pagamentoId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<ConsultarPagamentoResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        request,
                        ConsultarPagamentoResponse.class
                );

        return response.getBody();
    }
}