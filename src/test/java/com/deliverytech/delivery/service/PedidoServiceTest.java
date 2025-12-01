package com.deliverytech.delivery.service;

import com.deliverytech.delivery.dto.ItemPedidoDTO;
import com.deliverytech.delivery.dto.PedidoCreateDTO;
import com.deliverytech.delivery.entity.Cliente;
import com.deliverytech.delivery.entity.Pedido;
import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.repository.ClienteRepository;
import com.deliverytech.delivery.repository.PedidoRepository;
import com.deliverytech.delivery.repository.RestauranteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private RestauranteRepository restauranteRepository;

    @Test
    @DisplayName("Deve criar pedido com sucesso quando dados são válidos")
    void deveCriarPedidoComSucesso() {
        // Cenário (Arrange)
        Long clienteId = 1L;
        Long restauranteId = 2L;
        
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        cliente.setAtivo(true);

        Restaurante restaurante = new Restaurante();
        restaurante.setId(restauranteId);
        restaurante.setAtivo(true);

        PedidoCreateDTO dto = new PedidoCreateDTO(
            clienteId, 
            restauranteId, 
            new BigDecimal("50.00"), 
            "Sem cebola",
            List.of(new ItemPedidoDTO(10L, 2)),
            "Rua Teste, 123"
        );

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(restauranteRepository.findById(restauranteId)).thenReturn(Optional.of(restaurante));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Ação (Act)
        Pedido pedidoSalvo = pedidoService.criar(dto);

        // Verificação (Assert)
        assertNotNull(pedidoSalvo);
        assertEquals("PENDENTE", pedidoSalvo.getStatus());
        assertEquals(cliente, pedidoSalvo.getCliente());
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar pedido para cliente inativo")
    void naoDeveCriarPedidoParaClienteInativo() {
        Long clienteId = 1L;
        Cliente clienteInativo = new Cliente();
        clienteInativo.setId(clienteId);
        clienteInativo.setAtivo(false); // Inativo

        PedidoCreateDTO dto = new PedidoCreateDTO(
            clienteId, 1L, BigDecimal.TEN, null, List.of(), null
        );

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteInativo));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> pedidoService.criar(dto));
        verify(pedidoRepository, never()).save(any());
    }
}