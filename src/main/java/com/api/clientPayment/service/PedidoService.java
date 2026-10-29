package com.api.clientPayment.service;

import com.api.clientPayment.dto.request.PedidoRequest;
import com.api.clientPayment.entity.Mercado;
import com.api.clientPayment.repository.MercadoRepository;
import com.api.clientPayment.service.menssage.MontarTextoService;
import com.api.clientPayment.service.menssage.WhatsAppService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PedidoService {

    private final MercadoRepository mercadoRepository;
    private final MontarTextoService montarTextoService;
    private final WhatsAppService whatsAppService;

    public void receberPedido(PedidoRequest pedido) {

        Mercado mercado = mercadoRepository
                .buscarMercadoPorIdName(pedido.getLanchoneteId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Mercado não encontrado."
                ));

        String mensagem = montarTextoService.montarTexto(pedido);
        String telefone = mercado.getPhone();

        whatsAppService.enviarMensagem(telefone, mensagem);
    }
}