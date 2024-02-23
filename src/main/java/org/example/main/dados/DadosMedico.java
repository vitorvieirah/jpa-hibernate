package org.example.main.dados;

import lombok.Builder;

@Builder
public record DadosMedico (Long id, String nome, String especialidade) {}
