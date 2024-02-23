package org.example.mapper;

import lombok.AllArgsConstructor;
import org.example.domain.Medico;
import org.example.entity.MedicoEntity;
import org.example.main.dados.DadosMedico;

import java.util.List;

@AllArgsConstructor
public class MedicoMapper implements ManegerMapper<Medico, MedicoEntity, DadosMedico>{

    @Override
    public MedicoEntity paraEntity(Medico domain) {
        return MedicoEntity.builder()
                .crm(domain.getCrm())
                .nome(domain.getNome())
                .especialidade(domain.getEspecialidade())
                .build();
    }

    @Override
    public Medico paraDomain(MedicoEntity entity) {
        return Medico.builder()
                .crm(entity.getCrm())
                .especialidade(entity.getEspecialidade())
                .nome(entity.getNome())
                .build();
    }

    @Override
    public Medico paraDomainDeDados(DadosMedico medico) {
        return Medico.builder()
                .crm(medico.crm())
                .especialidade(medico.especialidade())
                .nome(medico.nome())
                .build();
    }

    public List<Medico> paraDomainsDeEntitys(List<MedicoEntity> resultMedicos) {
        return resultMedicos.stream().map(this::paraDomain).toList();
    }
}
