
package com.deliverytech.delivery.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.util.List;


public record PedidoCreateDTO(
    
    @NotNull(message = "ID do cliente é obrigatório.")
    Long clienteId,

    @NotNull(message = "ID do restaurante é obrigatório.")
    Long restauranteId,

    @NotNull(message = "Valor total é obrigatório.")
    @DecimalMin(value = "0.01", message = "Valor total deve ser positivo.")
    BigDecimal valorTotal, 

    String observacoes,

    
    @NotEmpty(message = "Pedido deve conter pelo menos um item.")
    @Size(min = 1, message = "Pedido deve conter pelo menos um item.")
    @Valid 
    List<ItemPedidoDTO> itens,

    String enderecoEntrega 
) {}