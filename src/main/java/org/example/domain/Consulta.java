package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;


@AllArgsConstructor
@Builder
@Getter
@ToString
public class Consulta {

    private Long id;
    private Paciente paciente;
    private Medico medico;
    private LocalDate data;

    public void remarcar(LocalDate novaData) {
        this.data = novaData;
    }
}
