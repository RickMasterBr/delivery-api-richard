package com.deliverytech.delivery.service;

import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    /**
     * REGRA DE NEGÓCIO: Cadastrar novo restaurante.
     */
    public Restaurante cadastrar(Restaurante restaurante) {
        // Regra: Taxa de entrega não pode ser negativa
        if (restaurante.getTaxaEntrega() != null &&
                restaurante.getTaxaEntrega().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Taxa de entrega não pode ser negativa.");
        }

        // Regra: Restaurante novo sempre começa ativo
        restaurante.setAtivo(true);
        return restauranteRepository.save(restaurante);
    }

    /**
     * REGRA DE NEGÓCIO: Atualizar dados de um restaurante.
     */
    public Restaurante atualizar(Long id, Restaurante restauranteAtualizado) {
        Restaurante restaurante = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + id));

        // Regra: Taxa de entrega não pode ser negativa
        if (restauranteAtualizado.getTaxaEntrega() != null &&
                restauranteAtualizado.getTaxaEntrega().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Taxa de entrega não pode ser negativa.");
        }

        restaurante.setNome(restauranteAtualizado.getNome());
        restaurante.setCategoria(restauranteAtualizado.getCategoria());
        restaurante.setEndereco(restauranteAtualizado.getEndereco());
        restaurante.setTelefone(restauranteAtualizado.getTelefone());
        restaurante.setTaxaEntrega(restauranteAtualizado.getTaxaEntrega());

        return restauranteRepository.save(restaurante);
    }

    /**
     * REGRA DE NEGÓCIO: Inativar um restaurante (Soft Delete).
     */
    public void inativar(Long id) {
        Restaurante restaurante = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + id));
        restaurante.inativar();
        restauranteRepository.save(restaurante);
    }

    /**
     * BUSCA: Lista todos os restaurantes ativos.
     */
    @Transactional(readOnly = true)
    public List<Restaurante> listarAtivos() {
        return restauranteRepository.findByAtivoTrue();
    }

    /**
     * BUSCA: Busca um restaurante pelo ID.
     */
    @Transactional(readOnly = true)
    public Optional<Restaurante> buscarPorId(Long id) {
        return restauranteRepository.findById(id);
    }

    /**
     * BUSCA: Busca restaurantes por categoria.
     */
    @Transactional(readOnly = true)
    public List<Restaurante> buscarPorCategoria(String categoria) {
        return restauranteRepository.findByCategoriaIgnoreCase(categoria);
    }
}