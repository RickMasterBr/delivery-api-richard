package com.deliverytech.delivery.service;

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

    // Precisamos do RestauranteRepository para associar o produto
    @Autowired
    private RestauranteRepository restauranteRepository;

    /**
     * REGRA DE NEGÓCIO: Cadastrar um produto VINCULADO a um restaurante.
     */
    public Produto cadastrar(Produto produto, Long restauranteId) {
        // 1. Regra: Valida o preço
        validarPreco(produto.getPreco());

        // 2. Regra: Busca o restaurante
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + restauranteId));

        // 3. Associa o produto ao restaurante
        produto.setRestaurante(restaurante);

        // 4. Regra: Produto novo começa disponível
        if (produto.getDisponivel() == null) {
            produto.setDisponivel(true);
        }

        return produtoRepository.save(produto);
    }

    /**
     * REGRA DE NEGÓCIO: Atualizar um produto.
     */
    public Produto atualizar(Long id, Produto produtoAtualizado) {
        Produto produto = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));

        validarPreco(produtoAtualizado.getPreco());

        produto.setNome(produtoAtualizado.getNome());
        produto.setDescricao(produtoAtualizado.getDescricao());
        produto.setPreco(produtoAtualizado.getPreco());
        produto.setCategoria(produtoAtualizado.getCategoria());

        return produtoRepository.save(produto);
    }

    /**
     * REGRA DE NEGÓCIO: Atualizar disponibilidade (disponível/indisponível).
     */
    public void atualizarDisponibilidade(Long id, boolean disponivel) {
        Produto produto = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));
        produto.setDisponivel(disponivel);
        produtoRepository.save(produto);
    }

    /**
     * BUSCA: Busca um produto por ID.
     */
    @Transactional(readOnly = true)
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    /**
     * BUSCA: Lista todos os produtos de um restaurante específico.
     */
    @Transactional(readOnly = true)
    public List<Produto> listarPorRestaurante(Long restauranteId) {
        return produtoRepository.findByRestauranteId(restauranteId);
    }

    /**
     * BUSCA: Lista apenas produtos disponíveis de um restaurante.
     */
    @Transactional(readOnly = true)
    public List<Produto> listarDisponiveisPorRestaurante(Long restauranteId) {
        return produtoRepository.findByRestauranteIdAndDisponivelTrue(restauranteId);
    }

    private void validarPreco(BigDecimal preco) {
        if (preco == null || preco.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Preço do produto não pode ser nulo ou negativo.");
        }
    }
}