package org.example.mapper;

import lombok.AllArgsConstructor;
import org.example.domain.Consulta;
import org.example.entity.ConsultaEntity;
import org.example.main.dados.DadosConsulta;

import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
public class ConsultaMapper implements ManegerMapper<Consulta, ConsultaEntity, DadosConsulta>{

    private final PacienteMapper pacienteMapper;
    private final MedicoMapper medicoMapper;


    @Override
    public ConsultaEntity paraEntity(Consulta domain) {
        return ConsultaEntity.builder()
                .id(domain.getId())
                .paciente(pacienteMapper.paraEntity(domain.getPaciente()))
                .medico(medicoMapper.paraEntity(domain.getMedico()))
                .data(domain.getData())
                .build();
    }

    @Override
    public Consulta paraDomain(ConsultaEntity entity) {
        return Consulta.builder()
                .id(entity.getId())
                .medico(medicoMapper.paraDomain(entity.getMedico()))
                .paciente(pacienteMapper.paraDomain(entity.getPaciente()))
                .data(entity.getData())
                .build();
    }

    @Override
    public Consulta paraDomainDeDados(DadosConsulta dados) {
        return Consulta.builder()
                .id(dados.id())
                .paciente(dados.paciente())
                .medico(dados.medico())
                .data(dados.data())
                .build();
    }

    public List<Consulta> paraDomainsDeEntitys (List<ConsultaEntity> entities){
        return entities.stream().map(this::paraDomain).toList();
    }


}
