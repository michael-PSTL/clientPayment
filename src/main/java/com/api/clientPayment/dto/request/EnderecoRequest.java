
package com.api.clientPayment.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EnderecoRequest {

    private String cidade;
    private String bairro;
    private String rua;
    private String numero;
    private String complemento;
    private String cep;
    private Double latitude;
    private Double longitude;
}
