package org.example.main.dados;

import lombok.Builder;

@Builder
public record DadosPaciente(String cpf, String nome, Integer idade, String email) {}
