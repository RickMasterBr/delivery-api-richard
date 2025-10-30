package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    /**
     * CRIAR restaurante
     * Mapeia para: POST /restaurantes
     */
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Restaurante restaurante) {
        try {
            Restaurante salvo = restauranteService.cadastrar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * LISTAR todos os restaurantes ativos
     * Mapeia para: GET /restaurantes
     */
    @GetMapping
    public ResponseEntity<List<Restaurante>> listarAtivos() {
        return ResponseEntity.ok(restauranteService.listarAtivos());
    }

    /**
     * BUSCAR por ID
     * Mapeia para: GET /restaurantes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> buscarPorId(@PathVariable Long id) {
        return restauranteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * ATUALIZAR restaurante
     * Mapeia para: PUT /restaurantes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
        try {
            Restaurante atualizado = restauranteService.atualizar(id, restaurante);
            return ResponseEntity.ok(atualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * INATIVAR restaurante
     * Mapeia para: DELETE /restaurantes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> inativar(@PathVariable Long id) {
        try {
            restauranteService.inativar(id);
            return ResponseEntity.ok().body("Restaurante inativado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * BUSCAR por Categoria
     * Mapeia para: GET /restaurantes/categoria?nome=Italiana
     * @RequestParam: Pega o valor do parâmetro "nome" da URL.
     */
    @GetMapping("/categoria")
    public ResponseEntity<List<Restaurante>> buscarPorCategoria(@RequestParam("nome") String categoria) {
        return ResponseEntity.ok(restauranteService.buscarPorCategoria(categoria));
    }
}