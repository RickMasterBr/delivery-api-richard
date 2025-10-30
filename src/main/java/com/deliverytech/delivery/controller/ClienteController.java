package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.entity.Cliente;
import com.deliverytech.delivery.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @RestController: Combina @Controller e @ResponseBody.
 * Diz ao Spring que esta classe responderá a requisições HTTP
 * e os retornos dos métodos devem ser convertidos para JSON.
 *
 * @RequestMapping("/clientes"): Define o prefixo da URL para todos
 * os endpoints nesta classe. Todos começarão com /clientes.
 */
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    // Injeta o Service que criamos
    @Autowired
    private ClienteService clienteService;

    /**
     * Endpoint para CRIAR um novo cliente.
     * Mapeia para: POST /clientes
     * @RequestBody: Converte o JSON enviado no corpo da requisição
     * para um objeto Cliente.
     */
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Cliente cliente) {
        try {
            Cliente clienteSalvo = clienteService.cadastrar(cliente);
            // Retorna 201 Created com o cliente salvo no corpo
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
        } catch (IllegalArgumentException e) {
            // Se o Service lançar nossa exceção (ex: email duplicado),
            // retornamos 400 Bad Request com a mensagem de erro.
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint para LISTAR todos os clientes ativos.
     * Mapeia para: GET /clientes
     */
    @GetMapping
    public ResponseEntity<List<Cliente>> listarAtivos() {
        List<Cliente> clientes = clienteService.listarAtivos();
        return ResponseEntity.ok(clientes); // Retorna 200 OK
    }

    /**
     * Endpoint para BUSCAR um cliente por ID.
     * Mapeia para: GET /clientes/{id}
     * @PathVariable: Pega o valor {id} da URL e passa para o método.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id)
                .map(cliente -> ResponseEntity.ok(cliente)) // Se achar, retorna 200 OK
                .orElseGet(() -> ResponseEntity.notFound().build()); // Se não, retorna 404 Not Found
    }

    /**
     * Endpoint para ATUALIZAR um cliente.
     * Mapeia para: PUT /clientes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Cliente clienteAtualizado) {
        try {
            Cliente cliente = clienteService.atualizar(id, clienteAtualizado);
            return ResponseEntity.ok(cliente); // Retorna 200 OK com o cliente atualizado
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint para INATIVAR (deletar) um cliente (Soft Delete).
     * Mapeia para: DELETE /clientes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> inativar(@PathVariable Long id) {
        try {
            clienteService.inativar(id);
            // Retorna 200 OK com uma mensagem
            return ResponseEntity.ok().body("Cliente inativado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}