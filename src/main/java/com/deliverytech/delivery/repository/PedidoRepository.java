package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    /**
     * Busca todos os pedidos de um cliente específico.
     */
    List<Pedido> findByClienteId(Long clienteId); // [cite: 242]

    /**
     * Busca todos os pedidos de um restaurante específico.
     */
    List<Pedido> findByRestauranteId(Long restauranteId);

    /**
     * Busca pedidos de um cliente por um status específico (ex: "PENDENTE").
     */
    List<Pedido> findByClienteIdAndStatusIgnoreCase(Long clienteId, String status); // [cite: 242]

    /**
     * Busca pedidos entre duas datas.
     */
    List<Pedido> findByDataPedidoBetween(LocalDateTime dataInicio, LocalDateTime dataFim); // [cite: 242]
}