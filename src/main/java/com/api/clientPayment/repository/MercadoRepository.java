package com.api.clientPayment.repository;

import com.api.clientPayment.entity.ItemCardapio;
import com.api.clientPayment.entity.Mercado;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class MercadoRepository {

    private final List<Mercado> mercados = new ArrayList<>();
    private final ObjectMapper objectMapper;

    public MercadoRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        carregarMercados();
    }

    private void carregarMercados() {

        String mercadosJson = System.getenv("MERCADOS_JSON");

        try {

            Map<String, List<Mercado>> dados;

            if (mercadosJson != null && !mercadosJson.isBlank()) {

                dados = objectMapper.readValue(
                        mercadosJson,
                        new TypeReference<>() {}
                );

            } else {

                File arquivo = new File("config/mercados.json");

                dados = objectMapper.readValue(
                        arquivo,
                        new TypeReference<>() {}
                );
            }

            List<Mercado> mercadosArquivo = dados.get("mercados");

            if (mercadosArquivo != null) {
                mercados.addAll(mercadosArquivo);
            }

        } catch (IOException e) {

            throw new RuntimeException(
                    "Erro ao carregar mercados",
                    e
            );
        }
    }

    public void salvarMercado(Mercado mercado) {
        this.mercados.add(mercado);
    }

    public Optional<Mercado> buscarMercadoPorIdName(
            String idName
    ) {

        return mercados.stream()
                .filter(m -> m.getIdName().equals(idName))
                .findFirst();
    }

    public Optional<ItemCardapio> buscarItemCardapioPorId(
            String nameId,
            String itemId
    ) {

        return buscarMercadoPorIdName(nameId)
                .flatMap(mercado ->
                        mercado.getCardapio().stream()
                                .filter(item ->
                                        item.getIdName()
                                                .equalsIgnoreCase(itemId)
                                )
                                .findFirst()
                );
    }
}