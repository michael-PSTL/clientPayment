
package com.api.clientPayment.controller;

import com.api.clientPayment.service.payment.WebhookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/webhook")
@AllArgsConstructor
public class WebhookController {

    private final WebhookService webhookService;

    @PostMapping("/mercado-pago")
    public ResponseEntity<Void> receberNotificacao(
            @RequestBody Map<String, Object> notificacao
    ) {

        webhookService.processarNotificacao(notificacao);

        return ResponseEntity.ok().build();
    }
}
