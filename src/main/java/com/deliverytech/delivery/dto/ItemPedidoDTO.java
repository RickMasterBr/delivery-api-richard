
package com.deliverytech.delivery.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record ItemPedidoDTO(
    
    @NotNull(message = "ID do produto é obrigatório.")
    Long produtoId,

    @NotNull(message = "Quantidade é obrigatória.")
    @Positive(message = "Quantidade deve ser maior que zero.")
    Integer quantidade
) {}