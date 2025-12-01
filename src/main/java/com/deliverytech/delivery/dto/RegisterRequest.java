package com.deliverytech.delivery.dto;
import com.deliverytech.delivery.enums.Role;
public record RegisterRequest(String nome, String email, String senha, Role role, Long restauranteId) {}