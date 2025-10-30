package com.deliverytech.delivery.service;

import com.deliverytech.delivery.entity.Cliente;
import com.deliverytech.delivery.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @Service: Marca a classe como um componente de serviço do Spring.
 * @Transactional: Garante que todos os métodos aqui sejam executados
 * dentro de uma transação com o banco de dados. Se algo falhar,
 * a transação é revertida (rollback).
 */
@Service
@Transactional
public class ClienteService {

    // Injeção de Dependência: O Spring vai nos dar uma
    // instância do ClienteRepository quando esta classe for criada.
    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * REGRA DE NEGÓCIO: Cadastrar um novo cliente.
     * Valida se o e-mail já existe antes de salvar.
     */
    public Cliente cadastrar(Cliente cliente) {
        // Regra: E-mail deve ser único
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado: " + cliente.getEmail());
        }

        // Regra: Validações de dados básicos
        validarDadosCliente(cliente);

        // Se o cliente foi salvo sem ID, ele já seta 'ativo = true'
        // e 'dataCadastro' via @PrePersist na entidade.
        return clienteRepository.save(cliente);
    }

    /**
     * REGRA DE NEGÓCIO: Atualizar um cliente existente.
     */
    public Cliente atualizar(Long id, Cliente clienteAtualizado) {
        // 1. Busca o cliente no banco
        Cliente clienteExistente = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + id));

        // 2. Regra: Verifica se o novo e-mail já está em uso por OUTRO cliente
        if (!clienteExistente.getEmail().equals(clienteAtualizado.getEmail()) &&
                clienteRepository.existsByEmail(clienteAtualizado.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado: " + clienteAtualizado.getEmail());
        }

        // 3. Valida os novos dados
        validarDadosCliente(clienteAtualizado);

        // 4. Atualiza os campos permitidos
        clienteExistente.setNome(clienteAtualizado.getNome());
        clienteExistente.setEmail(clienteAtualizado.getEmail());
        clienteExistente.setTelefone(clienteAtualizado.getTelefone());
        clienteExistente.setEndereco(clienteAtualizado.getEndereco());

        // 5. Salva (o JPA entende que é um update, pois o clienteExistente tem ID)
        return clienteRepository.save(clienteExistente);
    }

    /**
     * REGRA DE NEGÓCIO: Inativar um cliente (Soft Delete).
     */
    public void inativar(Long id) {
        Cliente cliente = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + id));

        // Chama o método que definimos na entidade
        cliente.inativar();
        clienteRepository.save(cliente);
    }

    /**
     * BUSCA: Lista todos os clientes que estão ativos.
     */
    @Transactional(readOnly = true) // Otimização: Apenas leitura, não modifica dados
    public List<Cliente> listarAtivos() {
        return clienteRepository.findByAtivoTrue();
    }

    /**
     * BUSCA: Busca um cliente pelo seu ID.
     */
    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    /**
     * BUSCA: Busca um cliente pelo seu e-mail.
     */
    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    /**
     * Método de validação interna.
     */
    private void validarDadosCliente(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("E-mail é obrigatório.");
        }
        if (cliente.getNome().length() < 2) {
            throw new IllegalArgumentException("Nome deve ter pelo menos 2 caracteres.");
        }
    }
}