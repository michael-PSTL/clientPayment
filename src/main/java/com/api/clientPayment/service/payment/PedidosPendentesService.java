package com.api.clientPayment.service.payment;

import com.api.clientPayment.dto.request.PedidoRequest;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class PedidosPendentesService {

    private final ConcurrentMap<String, PedidoRequest> pedidos =
            new ConcurrentHashMap<>();

    public void adicionar(String pagamentoId, PedidoRequest pedido) {
        pedidos.put(pagamentoId, pedido);
    }

    public PedidoRequest buscar(String pagamentoId) {
        return pedidos.get(pagamentoId);
    }

    public void remover(String pagamentoId) {
        pedidos.remove(pagamentoId);
    }
}