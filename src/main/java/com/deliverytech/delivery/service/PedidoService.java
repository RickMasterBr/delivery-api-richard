
package com.deliverytech.delivery.service;

import com.deliverytech.delivery.dto.PedidoCreateDTO; 
import com.deliverytech.delivery.entity.Cliente;
import com.deliverytech.delivery.entity.Pedido;
import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.repository.ClienteRepository;
import com.deliverytech.delivery.repository.PedidoRepository;
import com.deliverytech.delivery.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors; 

@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    
    public Pedido criar(PedidoCreateDTO pedidoDTO) {
        
        Cliente cliente = clienteRepository.findById(pedidoDTO.clienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + pedidoDTO.clienteId()));
        
        if (!cliente.getAtivo()) {
            throw new IllegalArgumentException("Cliente está inativo, não pode fazer pedidos.");
        }

        
        Restaurante restaurante = restauranteRepository.findById(pedidoDTO.restauranteId())
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + pedidoDTO.restauranteId()));
        
        if (!restaurante.getAtivo()) {
            throw new IllegalArgumentException("Restaurante está inativo, não pode receber pedidos.");
        }

        
        Pedido pedido = new Pedido();
        pedido.setValorTotal(pedidoDTO.valorTotal());
        pedido.setObservacoes(pedidoDTO.observacoes());
        
        String itensComoString = pedidoDTO.itens().stream()
                .map(item -> item.quantidade() + "x ProdutoID(" + item.produtoId() + ")")
                .collect(Collectors.joining(", "));
        pedido.setItens(itensComoString);

        
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);

        
        pedido.setNumeroPedido("PED-" + UUID.randomUUID().toString().substring(0, 8));
        pedido.setStatus("PENDENTE");
        
        return pedidoRepository.save(pedido);
    }

    
    public Pedido atualizarStatus(Long id, String novoStatus) {
        Pedido pedido = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + id));

        
        if (novoStatus == null || novoStatus.trim().isEmpty()) {
            throw new IllegalArgumentException("Novo status não pode ser vazio.");
        }

        pedido.setStatus(novoStatus.toUpperCase());
        return pedidoRepository.save(pedido);
    }

    
    @Transactional(readOnly = true)
    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarPorRestaurante(Long restauranteId) {
        return pedidoRepository.findByRestauranteId(restauranteId);
    }
}