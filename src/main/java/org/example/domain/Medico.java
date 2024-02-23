package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.main.dados.DadosMedico;

@AllArgsConstructor
@Builder
@Getter
public class Medico {

    private Long id;
    private String nome;
    private String especialidade;

    public void alterarDados(DadosMedico medico) {
        if(medico.nome() != null)
            this.nome = medico.nome();
        if(medico.especialidade() != null)
            this.especialidade = medico.especialidade();
    }
}
