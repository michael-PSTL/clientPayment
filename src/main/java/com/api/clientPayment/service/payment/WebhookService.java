package com.api.clientPayment.service.payment;

import com.api.clientPayment.dto.request.PedidoRequest;
import com.api.clientPayment.dto.mercadopago.ConsultarPagamentoResponse;
import com.api.clientPayment.service.PedidoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class WebhookService {

    private final ConsultarPagamentoService consultarPagamentoService;
    private final PedidosPendentesService pedidosPendentesService;
    private final PedidoService pedidoService;

    public void processarNotificacao(
            Map<String, Object> notificacao
    ) {

        // 1. Recuperar data da notificação
        Object dataObject = notificacao.get("data");

        if (!(dataObject instanceof Map<?, ?> data)) {
            throw new IllegalArgumentException(
                    "Dados do pagamento não encontrados na notificação"
            );
        }

        // 2. Recuperar ID do pagamento
        Object pagamentoIdObject = data.get("id");

        if (pagamentoIdObject == null) {
            throw new IllegalArgumentException(
                    "ID do pagamento não encontrado na notificação"
            );
        }

        String pagamentoId = pagamentoIdObject.toString();

        // 3. Recuperar pedido pendente
        PedidoRequest pedido =
                pedidosPendentesService.buscar(pagamentoId);

        if (pedido == null) {
            return;
        }

        // 4. Recuperar ID da lanchonete através do pedido
        String lanchoneteId = pedido.getLanchoneteId();

        // 5. Consultar pagamento no Mercado Pago
        ConsultarPagamentoResponse pagamento =
                consultarPagamentoService.consultarPagamento(
                        pagamentoId,
                        lanchoneteId
                );

        // 6. Confirmar ID do pagamento
        if (pagamento == null ||
                pagamento.getId() == null ||
                !pagamentoId.equals(pagamento.getId().toString())) {

            throw new IllegalStateException(
                    "ID do pagamento retornado não corresponde à notificação"
            );
        }

        // 7. Verificar aprovação
        if (!"approved".equals(pagamento.getStatus())) {
            return;
        }

        // 8. Processar pedido aprovado
        pedidoService.receberPedido(pedido);

        // 9. Remover pedido pendente após processamento
        pedidosPendentesService.remover(pagamentoId);
    }
}