package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    /**
     * Busca todos os produtos de um restaurante específico.
     * O Spring JPA entende "RestauranteId" e busca pelo ID da entidade aninhada.
     */
    List<Produto> findByRestauranteId(Long restauranteId); // [cite: 240]

    /**
     * Busca produtos de um restaurante que também estejam disponíveis.
     */
    List<Produto> findByRestauranteIdAndDisponivelTrue(Long restauranteId); // [cite: 240]

    /**
     * Busca produtos de um restaurante, por categoria.
     */
    List<Produto> findByRestauranteIdAndCategoriaIgnoreCase(Long restauranteId, String categoria); // [cite: 240]
}