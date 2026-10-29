package com.api.clientPayment.controller;

import com.api.clientPayment.dto.request.PedidoRequest;
import com.api.clientPayment.dto.response.PixResponse;
import com.api.clientPayment.service.PedidoService;
import com.api.clientPayment.service.menssage.MontarItensService;
import com.api.clientPayment.service.payment.PedidosPendentesService;
import com.api.clientPayment.service.payment.PixService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedido")
@AllArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final PixService pixService;
    private final MontarItensService montarItensService;
    private final PedidosPendentesService pedidosPendentesService;

    @PostMapping
    public ResponseEntity<?> receberPedido(
            @RequestBody PedidoRequest pedido
    ) {

        switch (pedido.getFormaPagamento()) {
            case PIX_ONLINE -> {

                PixResponse resposta = pixService.pixPagamento(
                        pedido,
                        montarItensService.calcularTotal(pedido)
                );
                pedidosPendentesService.adicionar(
                        resposta.getPagamentoId(),
                        pedido
                );

                return ResponseEntity.ok(resposta);
            }

            default -> {
                pedidoService.receberPedido(pedido);
                return ResponseEntity.ok().build();
            }
        }
    }
}