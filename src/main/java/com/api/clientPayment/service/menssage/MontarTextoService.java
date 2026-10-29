package com.api.clientPayment.service.menssage;

import com.api.clientPayment.dto.request.PedidoRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.Locale;

@Service
@AllArgsConstructor
public class MontarTextoService {

    private final MontarDadosService montarDadosService;
    private final MontarEnderecoService montarEnderecoService;
    private final MontarItensService montarItensService;

    public String montarTexto(PedidoRequest pedido) {

        String dados = montarDadosService.montarDados(pedido);
        String endereco = montarEnderecoService.montarEndereco(pedido);
        String itens = montarItensService.montarItens(pedido);

        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(
                Locale.forLanguageTag("pt-BR")
        );

        String pagamento = """
                💳 *PAGAMENTO*
                Forma: %s
                Valor: %s
                """.formatted(
                pedido.getFormaPagamento().getDescricao(),
                formatoMoeda.format(montarItensService.calcularTotal(pedido))
        ).stripTrailing();

        String observacao = """
                📝 *OBSERVAÇÃO*
                %s
                """.formatted(
                pedido.getObservacao() == null || pedido.getObservacao().isBlank()
                        ? "Nenhuma"
                        : pedido.getObservacao()
        );

        String mapsLink = montarEnderecoService.mapsLink(pedido).strip();
        String whatsAppLink = montarDadosService.whatsAppLink(pedido).strip();

        return """
                %s

                %s

                %s

                %s

                %s

                %s

                %s
                """.formatted(
                dados.strip(),
                endereco.strip(),
                itens.strip(),
                pagamento.strip(),
                observacao.strip(),
                mapsLink,
                whatsAppLink
        ).strip();
    }
}