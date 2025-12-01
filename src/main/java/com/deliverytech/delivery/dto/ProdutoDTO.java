
package com.deliverytech.delivery.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;


public record ProdutoDTO(
    
    @NotBlank(message = "Nome é obrigatório.")
    @Size(min = 2, max = 50, message = "Nome deve ter entre 2 e 50 caracteres.")
    String nome,

    @Size(min = 10, message = "Descrição deve ter pelo menos 10 caracteres.")
    String descricao,

    @NotNull(message = "Preço é obrigatório.")
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero.")
    @DecimalMax(value = "500.00", message = "Preço não pode exceder R$ 500,00.")  
    BigDecimal preco,

    @NotBlank(message = "Categoria é obrigatória.")
    String categoria
) {}