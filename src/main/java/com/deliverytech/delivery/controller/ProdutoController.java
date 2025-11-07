
package com.deliverytech.delivery.controller;


import com.deliverytech.delivery.dto.ProdutoDTO;
import jakarta.validation.Valid;

import com.deliverytech.delivery.entity.Produto;
import com.deliverytech.delivery.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;


    @PostMapping("/restaurante/{id}")
    public ResponseEntity<Produto> cadastrar(@Valid @RequestBody ProdutoDTO produtoDTO, @PathVariable Long id) {
       
        Produto salvo = produtoService.cadastrar(produtoDTO, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoDTO produtoDTO) {
        Produto atualizado = produtoService.atualizar(id, produtoDTO);
        return ResponseEntity.ok(atualizado);
    }


    @GetMapping("/restaurante/{id}")
    public ResponseEntity<List<Produto>> listarPorRestaurante(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.listarPorRestaurante(id));
    }

    @GetMapping("/restaurante/{id}/disponiveis")
    public ResponseEntity<List<Produto>> listarDisponiveis(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.listarDisponiveisPorRestaurante(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        return produtoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/disponibilidade")
    public ResponseEntity<?> atualizarDisponibilidade(@PathVariable Long id, @RequestParam boolean disponivel) {
        
        produtoService.atualizarDisponibilidade(id, disponivel);
        return ResponseEntity.ok().body("Disponibilidade atualizada com sucesso.");
    }
}