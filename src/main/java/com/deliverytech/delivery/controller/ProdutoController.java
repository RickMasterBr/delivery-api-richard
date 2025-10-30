package com.deliverytech.delivery.controller;

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

    /**
     * LISTAR produtos por restaurante
     * Mapeia para: GET /produtos/restaurante/{id}
     */
    @GetMapping("/restaurante/{id}")
    public ResponseEntity<List<Produto>> listarPorRestaurante(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.listarPorRestaurante(id));
    }

    /**
     * LISTAR produtos disponíveis por restaurante
     * Mapeia para: GET /produtos/restaurante/{id}/disponiveis
     */
    @GetMapping("/restaurante/{id}/disponiveis")
    public ResponseEntity<List<Produto>> listarDisponiveis(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.listarDisponiveisPorRestaurante(id));
    }

    /**
     * CRIAR produto para um restaurante
     * Mapeia para: POST /produtos/restaurante/{id}
     */
    @PostMapping("/restaurante/{id}")
    public ResponseEntity<?> cadastrar(@RequestBody Produto produto, @PathVariable Long id) {
        try {
            Produto salvo = produtoService.cadastrar(produto, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * BUSCAR produto por ID
     * Mapeia para: GET /produtos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        return produtoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * ATUALIZAR produto
     * Mapeia para: PUT /produtos/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Produto produto) {
        try {
            Produto atualizado = produtoService.atualizar(id, produto);
            return ResponseEntity.ok(atualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * ATUALIZAR disponibilidade (PATCH é mais semântico para atualizações parciais)
     * Mapeia para: PATCH /produtos/{id}/disponibilidade
     */
    @PatchMapping("/{id}/disponibilidade")
    public ResponseEntity<?> atualizarDisponibilidade(@PathVariable Long id, @RequestParam boolean disponivel) {
        try {
            produtoService.atualizarDisponibilidade(id, disponivel);
            return ResponseEntity.ok().body("Disponibilidade atualizada com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}