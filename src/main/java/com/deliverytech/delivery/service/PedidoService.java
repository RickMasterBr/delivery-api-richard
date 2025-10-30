package com.deliverytech.delivery.service;

import com.deliverytech.delivery.entity.Cliente;
import com.deliverytech.delivery.entity.Pedido;
import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.repository.ClienteRepository;
import com.deliverytech.delivery.repository.PedidoRepository;
import com.deliverytech.delivery.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    /**
     * REGRA DE NEGÓCIO: Criar um novo pedido.
     * (Versão simplificada: o valor total e os itens vêm no objeto Pedido)
     */
    public Pedido criar(Pedido pedido, Long clienteId, Long restauranteId) {
        // 1. Regra: Buscar e validar o cliente
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + clienteId));
        if (!cliente.getAtivo()) {
            throw new IllegalArgumentException("Cliente está inativo, não pode fazer pedidos.");
        }

        // 2. Regra: Buscar e validar o restaurante
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + restauranteId));
        if (!restaurante.getAtivo()) {
            throw new IllegalArgumentException("Restaurante está inativo, não pode receber pedidos.");
        }

        // 3. Regra: Validar dados do pedido
        if (pedido.getValorTotal() == null || pedido.getValorTotal().signum() <= 0) {
            throw new IllegalArgumentException("Valor total do pedido deve ser positivo.");
        }

        // 4. Preencher os dados restantes do pedido
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);

        // 5. Regra: Gerar número do pedido e definir status inicial
        pedido.setNumeroPedido("PED-" + UUID.randomUUID().toString().substring(0, 8));
        pedido.setStatus("PENDENTE");
        pedido.setDataPedido(LocalDateTime.now()); // Garante a data atual

        return pedidoRepository.save(pedido);
    }

    /**
     * REGRA DE NEGÓCIO: Atualizar o status de um pedido.
     * Ex: PENDENTE -> CONFIRMADO -> EM_ENTREGA -> ENTREGUE
     */
    public Pedido atualizarStatus(Long id, String novoStatus) {
        Pedido pedido = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + id));

        // Aqui poderiam ter mais regras (ex: não pode voltar de ENTREGUE para PENDENTE)
        // Por enquanto, apenas atualizamos:
        pedido.setStatus(novoStatus.toUpperCase());

        return pedidoRepository.save(pedido);
    }

    /**
     * BUSCA: Busca um pedido por ID.
     */
    @Transactional(readOnly = true)
    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    /**
     * BUSCA: Lista todos os pedidos de um cliente específico.
     */
    @Transactional(readOnly = true)
    public List<Pedido> listarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    /**
     * BUSCA: Lista todos os pedidos de um restaurante específico.
     */
    @Transactional(readOnly = true)
    public List<Pedido> listarPorRestaurante(Long restauranteId) {
        return pedidoRepository.findByRestauranteId(restauranteId);
    }
}