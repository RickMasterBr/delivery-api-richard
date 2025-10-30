package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.entity.Pedido;
import com.deliverytech.delivery.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    /**
     * CRIAR um novo pedido
     * Mapeia para: POST /pedidos?clienteId={id}&restauranteId={id}
     */
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Pedido pedido,
                                   @RequestParam Long clienteId,
                                   @RequestParam Long restauranteId) {
        try {
            Pedido salvo = pedidoService.criar(pedido, clienteId, restauranteId);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * BUSCAR pedido por ID
     * Mapeia para: GET /pedidos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * LISTAR pedidos de um cliente
     * Mapeia para: GET /pedidos/cliente/{id}
     */
    @GetMapping("/cliente/{id}")
    public ResponseEntity<List<Pedido>> listarPorCliente(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.listarPorCliente(id));
    }

    /**
     * ATUALIZAR status do pedido
     * Mapeia para: PATCH /pedidos/{id}/status
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            Pedido pedido = pedidoService.atualizarStatus(id, status);
            return ResponseEntity.ok(pedido);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}