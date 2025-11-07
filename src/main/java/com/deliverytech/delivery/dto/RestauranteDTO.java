
package com.deliverytech.delivery.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;


public record RestauranteDTO(
    
    @NotBlank(message = "Nome é obrigatório.")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres.")
    String nome,

    @NotBlank(message = "Categoria é obrigatória.")
    String categoria,
    
    String endereco,
    String telefone,

    @NotNull(message = "Taxa de entrega é obrigatória.")
    @DecimalMin(value = "0.0", inclusive = true, message = "Taxa de entrega não pode ser negativa.")
    BigDecimal taxaEntrega

    
) {}