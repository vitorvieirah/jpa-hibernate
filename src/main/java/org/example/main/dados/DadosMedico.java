package org.example.main.dados;

import lombok.Builder;

@Builder
public record DadosMedico (String crm, String nome, String especialidade) {}
