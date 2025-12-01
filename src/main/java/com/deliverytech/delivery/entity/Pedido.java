package com.deliverytech.delivery.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode; 
import lombok.ToString; 

import com.fasterxml.jackson.annotation.JsonIgnore;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_pedido", nullable = false)
    private String numeroPedido;

    @Column(name = "data_pedido")
    private LocalDateTime dataPedido;

    private String status;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    private String observacoes;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude 
    @JsonIgnore
    private Cliente cliente;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurante_id", nullable = false)
    @ToString.Exclude 
    @EqualsAndHashCode.Exclude 
    @JsonIgnore
    private Restaurante restaurante;
    
    private String itens;

    @PrePersist 
    public void prePersist() {
        if (dataPedido == null) {
            dataPedido = LocalDateTime.now();
        }
    }
}