package org.example.domain;

import lombok.*;
import org.example.main.dados.DadosPaciente;


@AllArgsConstructor
@Builder
@Getter
@ToString
public class Paciente {

    private String cpf;
    private String nome;
    private Integer idade;
    private String email;

    public void alterarDados(DadosPaciente paciente) {
        if(paciente.cpf() != null)
            this.cpf = paciente.cpf();
        if(paciente.nome() != null)
            this.nome = paciente.nome();
        if(paciente.email() != null)
            this.email = paciente.email();
        if(paciente.idade() != null)
            this.idade = paciente.idade();
    }
}
