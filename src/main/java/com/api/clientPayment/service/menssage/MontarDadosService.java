package com.api.clientPayment.service.menssage;

import com.api.clientPayment.dto.request.PedidoRequest;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class MontarDadosService {

    public String montarDados(PedidoRequest pedido) {
        return """
                #NOVO PEDIDO 🍔

                👤 *CLIENTE*

                Nome: %s
                Telefone: %s
                E-mail: %s
                """.formatted(
                pedido.getNomeCliente(),
                pedido.getTelefoneCliente(),
                pedido.getEmailCliente()
        );
    }

    public String whatsAppLink(PedidoRequest pedido) {

        String mensagem = pedido.getTipoRecebimento().name().equals("ENTREGA")
                ? "Olá, " + pedido.getNomeCliente() + "! Seu pedido saiu para entrega."
                : "Olá, " + pedido.getNomeCliente() + "! Seu pedido está pronto para retirada.";

        return """
                🔗 *AVISAR CLIENTE*
                https://wa.me/%s?text=%s
                """.formatted(
                pedido.getTelefoneCliente(),
                URLEncoder.encode(mensagem, StandardCharsets.UTF_8)
        );
    }
}