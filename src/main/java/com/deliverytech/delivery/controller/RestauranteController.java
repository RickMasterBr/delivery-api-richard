package com.deliverytech.delivery.controller;


import com.deliverytech.delivery.dto.RestauranteDTO;
import jakarta.validation.Valid;

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


    @PostMapping
    public ResponseEntity<Restaurante> cadastrar(@Valid @RequestBody RestauranteDTO restauranteDTO) {
        
        Restaurante salvo = restauranteService.cadastrar(restauranteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Restaurante> atualizar(@PathVariable Long id, @Valid @RequestBody RestauranteDTO restauranteDTO) {
        Restaurante atualizado = restauranteService.atualizar(id, restauranteDTO);
        return ResponseEntity.ok(atualizado);
    }

    
    @GetMapping
    public ResponseEntity<List<Restaurante>> listarAtivos() {
        return ResponseEntity.ok(restauranteService.listarAtivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> buscarPorId(@PathVariable Long id) {
        return restauranteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> inativar(@PathVariable Long id) {
        restauranteService.inativar(id);
        return ResponseEntity.ok().body("Restaurante inativado com sucesso.");
    }

    @GetMapping("/categoria")
    public ResponseEntity<List<Restaurante>> buscarPorCategoria(@RequestParam("nome") String categoria) {
        return ResponseEntity.ok(restauranteService.buscarPorCategoria(categoria));
    }
}