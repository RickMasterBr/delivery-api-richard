
package com.deliverytech.delivery.controller;


import com.deliverytech.delivery.dto.PedidoCreateDTO;
import jakarta.validation.Valid;

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

    
    @PostMapping
    public ResponseEntity<Pedido> criar(@Valid @RequestBody PedidoCreateDTO pedidoDTO) {
        Pedido salvo = pedidoService.criar(pedidoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    
    @PatchMapping("/{id}/status")
    public ResponseEntity<Pedido> atualizarStatus(@PathVariable Long id, @RequestParam String status) {
        Pedido pedido = pedidoService.atualizarStatus(id, status);
        return ResponseEntity.ok(pedido);
    }

    

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<List<Pedido>> listarPorCliente(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.listarPorCliente(id));
    }
}