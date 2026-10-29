package com.api.clientPayment.service.menssage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class WhatsAppService {

    private final RestTemplate restTemplate;
    private final String instanceId;
    private final String token;
    private final String clientToken;

    public WhatsAppService(
            RestTemplateBuilder builder,
            @Value("${zapi.instance-id}") String instanceId,
            @Value("${zapi.token}") String token,
            @Value("${zapi.client-token}") String clientToken
    ) {
        this.restTemplate = builder.build();
        this.instanceId = instanceId;
        this.token = token;
        this.clientToken = clientToken;
    }

    public void enviarMensagem(String telefone, String mensagem) {

        String url = "https://api.z-api.io/instances/%s/token/%s/send-text"
                .formatted(instanceId, token);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Client-Token", clientToken);

        Map<String, String> corpo = Map.of(
                "phone", telefone,
                "message", mensagem
        );

        HttpEntity<Map<String, String>> requisicao =
                new HttpEntity<>(corpo, headers);

        ResponseEntity<String> resposta = restTemplate.postForEntity(
                url,
                requisicao,
                String.class
        );

        if (!resposta.getStatusCode().is2xxSuccessful()) {
            throw new IllegalStateException(
                    "Não foi possível enviar a mensagem pelo WhatsApp."
            );
        }
    }
}