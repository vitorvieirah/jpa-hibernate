package org.example.dao;

import lombok.AllArgsConstructor;
import org.example.domain.Consulta;
import org.example.entity.ConsultaEntity;
import org.example.exception.ConsultaDataBaseExceptions;
import org.example.exception.MedicoDataBaseException;
import org.example.mapper.ConsultaMapper;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ConsultaDao {
    
    private final EntityManager em;
    private final ConsultaMapper mapper;

    public List<Consulta> buscarConsultasPorMedico(Long idMedico) throws ConsultaDataBaseExceptions {
        String jpql = "SELECT c FROM ConsultaEntity c WHERE c.medico.id = :id";
        List<ConsultaEntity> consultas = new ArrayList<>();

        try {
            consultas = em.createQuery(jpql, ConsultaEntity.class)
                    .setParameter("id", idMedico)
                    .getResultList();
        }catch (Exception ex){
            throw new ConsultaDataBaseExceptions(ex.getMessage());
        }
        return mapper.paraDomainsDeEntitys(consultas);
    }

    public void salvar(Consulta consulta) throws MedicoDataBaseException {
        try {
            em.persist(mapper.paraEntity(consulta));
        }catch (Exception ex){
            throw new MedicoDataBaseException(ex.getMessage());
        }
    }

    public void deletar (Consulta consulta) throws MedicoDataBaseException {
        try{
            em.remove(mapper.paraEntity(consulta));
        }catch (Exception ex){
            throw new MedicoDataBaseException(ex.getMessage());
        }
    }

    public Optional<Consulta> buscarPorId(Long idConsulta) throws MedicoDataBaseException {
        Optional<ConsultaEntity> consultaOptional;
        try {
            consultaOptional = Optional.of(em.find(ConsultaEntity.class, idConsulta));
        }catch (Exception ex){
            throw new MedicoDataBaseException(ex.getMessage());
        }
        return consultaOptional.map(mapper::paraDomain);
    }
}
