package com.api.clientPayment.service.menssage;

import com.api.clientPayment.dto.request.ItemPedidoRequest;
import com.api.clientPayment.dto.request.PedidoRequest;
import com.api.clientPayment.entity.ItemCardapio;
import com.api.clientPayment.repository.MercadoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@Service
@AllArgsConstructor
public class MontarItensService {

    private final MercadoRepository mercadoRepository;

    public String montarItens(PedidoRequest pedido) {

        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(
                Locale.forLanguageTag("pt-BR")
        );

        StringBuilder texto = new StringBuilder("🛒 *ITENS*\n");

        for (ItemPedidoRequest itemPedido : pedido.getItens()) {

            ItemCardapio itemCardapio = buscarItem(pedido, itemPedido);

            BigDecimal subtotal = itemCardapio.getPreco()
                    .multiply(BigDecimal.valueOf(itemPedido.getQuantidade()));

            texto.append("%dx %s — %s%n".formatted(
                    itemPedido.getQuantidade(),
                    itemCardapio.getNome(),
                    formatoMoeda.format(subtotal)
            ));
        }

        return texto.toString().stripTrailing();
    }

    public BigDecimal calcularTotal(PedidoRequest pedido) {

        BigDecimal total = BigDecimal.ZERO;

        for (ItemPedidoRequest itemPedido : pedido.getItens()) {

            ItemCardapio itemCardapio = buscarItem(pedido, itemPedido);

            BigDecimal subtotal = itemCardapio.getPreco()
                    .multiply(BigDecimal.valueOf(itemPedido.getQuantidade()));

            total = total.add(subtotal);
        }

        return total;
    }

    private ItemCardapio buscarItem(
            PedidoRequest pedido,
            ItemPedidoRequest itemPedido
    ) {
        return mercadoRepository.buscarItemCardapioPorId(
                        pedido.getLanchoneteId(),
                        itemPedido.getProdutoId()
                )
                .orElseThrow(() -> new IllegalArgumentException(
                        "Item não encontrado: " + itemPedido.getProdutoId()
                ));
    }
}