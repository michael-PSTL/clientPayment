
package com.api.clientPayment.service.menssage;

import com.api.clientPayment.dto.request.EnderecoRequest;
import com.api.clientPayment.dto.request.PedidoRequest;
import com.api.clientPayment.enums.TipoRecebimento;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Service
public class MontarEnderecoService {

    public String montarEndereco(PedidoRequest pedido) {

        if (pedido.getTipoRecebimento() == TipoRecebimento.ENTREGA) {
            EnderecoRequest endereco = pedido.getEndereco();

            return """
                    📍 *ENDEREÇO*
                    %s, %s
                    %s — %s
                    CEP: %s
                    Complemento: %s
                    """.formatted(
                    endereco.getRua(),
                    endereco.getNumero(),
                    endereco.getBairro(),
                    endereco.getCidade(),
                    endereco.getCep(),
                    endereco.getComplemento()
            );
        }

        return """
                🛵 *RECEBIMENTO*
                Tipo: RETIRADA
                """;
    }

    public String mapsLink(PedidoRequest pedido) {

        if (pedido.getTipoRecebimento() == TipoRecebimento.RETIRADA) {
            return "";
        }

        EnderecoRequest endereco = pedido.getEndereco();

        if (endereco.getLatitude() != null && endereco.getLongitude() != null) {

            String coordenadas = String.format(
                    Locale.US,
                    "%f,%f",
                    endereco.getLatitude(),
                    endereco.getLongitude()
            );

            return """
                🗺️ *ABRIR LOCALIZAÇÃO*
                https://www.google.com/maps/search/?api=1&query=%s
                """.formatted(coordenadas);
        }

        String enderecoCompleto = "%s, %s, %s, %s, %s".formatted(
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getCep()
        );

        return """
            🗺️ *ABRIR LOCALIZAÇÃO*
            https://www.google.com/maps/search/?api=1&query=%s
            """.formatted(
                URLEncoder.encode(enderecoCompleto, StandardCharsets.UTF_8)
        );
    }
}
