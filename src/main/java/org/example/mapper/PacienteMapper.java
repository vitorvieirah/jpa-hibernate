package org.example.mapper;

import org.example.domain.Paciente;
import org.example.entity.PacienteEntity;
import org.example.main.dados.DadosPaciente;

public class PacienteMapper implements ManegerMapper<Paciente, PacienteEntity, DadosPaciente>{
    @Override
    public PacienteEntity paraEntity(Paciente domain) {
        return PacienteEntity.builder()
                .cpf(domain.getCpf())
                .nome(domain.getNome())
                .email(domain.getEmail())
                .idade(domain.getIdade())
                .build();
    }

    @Override
    public Paciente paraDomain(PacienteEntity entity) {
        return Paciente.builder()
                .cpf(entity.getCpf())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .idade(entity.getIdade())
                .build();
    }

    @Override
    public Paciente paraDomainDeDados (DadosPaciente dadosPaciente){
        return Paciente.builder()
                .cpf(dadosPaciente.cpf())
                .nome(dadosPaciente.nome())
                .email(dadosPaciente.email())
                .idade(dadosPaciente.idade())
                .build();
    }
}
