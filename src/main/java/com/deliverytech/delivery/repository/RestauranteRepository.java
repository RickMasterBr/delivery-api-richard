package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    /**
     * Busca restaurantes cujo nome contém a string fornecida (ignorando maiúsculas/minúsculas).
     * Ex: "pizz" encontra "Pizzaria Bella"
     */
    List<Restaurante> findByNomeContainingIgnoreCase(String nome); // [cite: 238]

    /**
     * Busca todos os restaurantes ativos.
     */
    List<Restaurante> findByAtivoTrue(); // [cite: 238]

    /**
     * Busca todos os restaurantes de uma categoria específica.
     */
    List<Restaurante> findByCategoriaIgnoreCase(String categoria); // [cite: 238]

    /**
     * Busca restaurantes ativos com avaliação (rating) maior ou igual a um valor,
     * ordenados da maior avaliação para a menor.
     */
    List<Restaurante> findByAtivoTrueAndAvaliacaoGreaterThanEqualOrderByAvaliacaoDesc(BigDecimal avaliacao); // [cite: 238]

}