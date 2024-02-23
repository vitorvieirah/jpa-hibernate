package org.example.main.dados;

import lombok.Builder;
import org.example.domain.Medico;
import org.example.domain.Paciente;

import java.time.LocalDate;

@Builder
public record DadosConsulta (Long id, Paciente paciente, Medico medico, LocalDate data) {}
