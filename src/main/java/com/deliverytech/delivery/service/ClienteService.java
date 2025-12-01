 

package com.deliverytech.delivery.service;

import com.deliverytech.delivery.dto.ClienteDTO;  
import com.deliverytech.delivery.entity.Cliente;
import com.deliverytech.delivery.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente cadastrar(ClienteDTO clienteDTO) {  
        
        if (clienteRepository.existsByEmail(clienteDTO.email())) {  
            throw new IllegalArgumentException("E-mail já cadastrado: " + clienteDTO.email());
        }

        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.nome());
        cliente.setEmail(clienteDTO.email());
        cliente.setTelefone(clienteDTO.telefone());
        cliente.setEndereco(clienteDTO.endereco());

        return clienteRepository.save(cliente);
    }

  
    public Cliente atualizar(Long id, ClienteDTO clienteAtualizado) {  
        Cliente clienteExistente = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + id));

        
        if (!clienteExistente.getEmail().equals(clienteAtualizado.email()) &&
                clienteRepository.existsByEmail(clienteAtualizado.email())) {
            throw new IllegalArgumentException("E-mail já cadastrado: " + clienteAtualizado.email());
        }

        
        clienteExistente.setNome(clienteAtualizado.nome());
        clienteExistente.setEmail(clienteAtualizado.email());
        clienteExistente.setTelefone(clienteAtualizado.telefone());
        clienteExistente.setEndereco(clienteAtualizado.endereco());

        return clienteRepository.save(clienteExistente);
    }

    
    public void inativar(Long id) {
        Cliente cliente = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + id));
        cliente.inativar();
        clienteRepository.save(cliente);
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarAtivos() {
        return clienteRepository.findByAtivoTrue();
    }

    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }
}