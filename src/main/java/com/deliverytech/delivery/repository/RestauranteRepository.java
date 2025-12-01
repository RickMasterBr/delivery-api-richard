package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

     
    List<Restaurante> findByNomeContainingIgnoreCase(String nome);  

     
    List<Restaurante> findByAtivoTrue();  

     
    List<Restaurante> findByCategoriaIgnoreCase(String categoria);  

     
    List<Restaurante> findByAtivoTrueAndAvaliacaoGreaterThanEqualOrderByAvaliacaoDesc(BigDecimal avaliacao);  

}