
package com.deliverytech.delivery.service;

import com.deliverytech.delivery.dto.ProdutoDTO;
import com.deliverytech.delivery.entity.Produto;
import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.repository.ProdutoRepository;
import com.deliverytech.delivery.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    
    public Produto cadastrar(ProdutoDTO produtoDTO, Long restauranteId) {
        
        

        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + restauranteId));

        Produto produto = new Produto();
        produto.setNome(produtoDTO.nome());
        produto.setDescricao(produtoDTO.descricao());
        produto.setPreco(produtoDTO.preco());
        produto.setCategoria(produtoDTO.categoria());
        
       
        produto.setRestaurante(restaurante);

       
        produto.setDisponivel(true); 

        return produtoRepository.save(produto);
    }

    
    public Produto atualizar(Long id, ProdutoDTO produtoAtualizado) {
        Produto produto = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));

        
        produto.setNome(produtoAtualizado.nome());
        produto.setDescricao(produtoAtualizado.descricao());
        produto.setPreco(produtoAtualizado.preco());
        produto.setCategoria(produtoAtualizado.categoria());

        return produtoRepository.save(produto);
    }

    
    public void atualizarDisponibilidade(Long id, boolean disponivel) {
        Produto produto = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));
        produto.setDisponivel(disponivel);
        produtoRepository.save(produto);
    }

    
    @Transactional(readOnly = true)
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Produto> listarPorRestaurante(Long restauranteId) {
        return produtoRepository.findByRestauranteId(restauranteId);
    }

    @Transactional(readOnly = true)
    public List<Produto> listarDisponiveisPorRestaurante(Long restauranteId) {
        return produtoRepository.findByRestauranteIdAndDisponivelTrue(restauranteId);
    }

}