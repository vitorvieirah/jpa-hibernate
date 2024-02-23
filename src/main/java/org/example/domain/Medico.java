package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.example.main.dados.DadosMedico;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class Medico {

    private String crm;
    private String nome;
    private String especialidade;

    public void alterarDados(DadosMedico medico) {
        if(medico.nome() != null)
            this.nome = medico.nome();
        if(medico.especialidade() != null)
            this.especialidade = medico.especialidade();
    }
}
