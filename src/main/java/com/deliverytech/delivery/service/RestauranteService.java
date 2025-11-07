
package com.deliverytech.delivery.service;

import com.deliverytech.delivery.dto.RestauranteDTO; 
import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    
    public Restaurante cadastrar(RestauranteDTO restauranteDTO) {
        
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(restauranteDTO.nome());
        restaurante.setCategoria(restauranteDTO.categoria());
        restaurante.setEndereco(restauranteDTO.endereco());
        restaurante.setTelefone(restauranteDTO.telefone());
        restaurante.setTaxaEntrega(restauranteDTO.taxaEntrega());

        
        restaurante.setAtivo(true);
        return restauranteRepository.save(restaurante);
    }

    public Restaurante atualizar(Long id, RestauranteDTO restauranteAtualizado) {
        Restaurante restaurante = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + id));

        
        restaurante.setNome(restauranteAtualizado.nome());
        restaurante.setCategoria(restauranteAtualizado.categoria());
        restaurante.setEndereco(restauranteAtualizado.endereco());
        restaurante.setTelefone(restauranteAtualizado.telefone());
        restaurante.setTaxaEntrega(restauranteAtualizado.taxaEntrega());

        return restauranteRepository.save(restaurante);
    }

   
    public void inativar(Long id) {
        Restaurante restaurante = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + id));
        restaurante.inativar();
        restauranteRepository.save(restaurante);
    }



    @Transactional(readOnly = true)
    public List<Restaurante> listarAtivos() {
        return restauranteRepository.findByAtivoTrue();
    }

    @Transactional(readOnly = true)
    public Optional<Restaurante> buscarPorId(Long id) {
        return restauranteRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Restaurante> buscarPorCategoria(String categoria) {
        return restauranteRepository.findByCategoriaIgnoreCase(categoria);
    }
}