package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Repository: Indica ao Spring que esta é uma interface de repositório.
 * JpaRepository<Cliente, Long>:
 * - Cliente: A entidade que este repositório vai gerenciar.
 * - Long: O tipo da chave primária (ID) da entidade Cliente.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // O Spring Data JPA cria a consulta SQL automaticamente
    // com base no nome do método.

    /**
     * Busca um cliente pelo seu email.
     * Equivalente a: SELECT c FROM Cliente c WHERE c.email = ?1
     */
    Optional<Cliente> findByEmail(String email); // [cite: 236]

    /**
     * Verifica se já existe um cliente com o email fornecido.
     */
    boolean existsByEmail(String email);

    /**
     * Busca todos os clientes que estão marcados como ativos.
     * Equivalente a: SELECT c FROM Cliente c WHERE c.ativo = true
     */
    List<Cliente> findByAtivoTrue(); // [cite: 236]
}