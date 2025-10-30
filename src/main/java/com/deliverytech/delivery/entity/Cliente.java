package com.deliverytech.delivery.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data // Anotação do Lombok: cria getters, setters, toString, etc.
@NoArgsConstructor // Anotação do Lombok: cria construtor vazio
@AllArgsConstructor // Anotação do Lombok: cria construtor com todos os campos
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone;
    private String endereco;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    private Boolean ativo;

    // Método para inativação (soft delete)
    public void inativar() {
        this.ativo = false;
    }

    @PrePersist // Antes de salvar um novo cliente, define a data de cadastro
    public void prePersist() {
        dataCadastro = LocalDateTime.now();
        if (ativo == null) {
            ativo = true;
        }
    }
}