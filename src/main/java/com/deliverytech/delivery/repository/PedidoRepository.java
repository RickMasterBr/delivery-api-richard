package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

     
    List<Pedido> findByClienteId(Long clienteId);  

     
    List<Pedido> findByRestauranteId(Long restauranteId);

     
    List<Pedido> findByClienteIdAndStatusIgnoreCase(Long clienteId, String status);  

     
    List<Pedido> findByDataPedidoBetween(LocalDateTime dataInicio, LocalDateTime dataFim);  
}