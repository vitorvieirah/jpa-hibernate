package org.example.dao;

import lombok.AllArgsConstructor;
import org.example.dao.log.ManegerLog;
import org.example.domain.Consulta;
import org.example.entity.ConsultaEntity;
import org.example.exception.ConsultaDataBaseException;
import org.example.mapper.ConsultaMapper;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ConsultaDao {

    private final ConsultaMapper mapper;

    public List<Consulta> buscarConsultasPorMedico(String crm, EntityManager em) throws ConsultaDataBaseException {
        String jpql = "SELECT c FROM Consulta c WHERE c.medico.crm = :crm";
        List<ConsultaEntity> consultas;

        try {
            consultas = em.createQuery(jpql, ConsultaEntity.class)
                    .setParameter("crm", crm)
                    .getResultList();
        }catch (Exception ex){
            ManegerLog.printLogError("Erro ao consultar consultas por médico", ex);
            throw new ConsultaDataBaseException(ex.getMessage());
        }
        return mapper.paraDomainsDeEntitys(consultas);
    }

    public void salvar(Consulta consulta, EntityManager em) throws ConsultaDataBaseException {
        try {
            em.persist(mapper.paraEntity(consulta));
        }catch (Exception ex){
            ManegerLog.printLogError("Erro ao salvar consulta", ex);
            throw new ConsultaDataBaseException(ex.getMessage());
        }
    }

    public void deletar (Long id, EntityManager em) throws ConsultaDataBaseException {
        try {
            ConsultaEntity consultaEntity = em.getReference(ConsultaEntity.class, id);
            em.remove(consultaEntity);
        }catch (EntityNotFoundException ex){
            ManegerLog.printLogError("Consulta não encontrado", ex);
        }catch (Exception ex){
            ManegerLog.printLogError("Erro ao deletar consulta", ex);
            throw new ConsultaDataBaseException(ex.getMessage());
        }
    }

    public Optional<Consulta> buscarPorId(Long idConsulta, EntityManager em) throws ConsultaDataBaseException {
        Optional<ConsultaEntity> consultaOptional;
        try {
            consultaOptional = Optional.of(em.find(ConsultaEntity.class, idConsulta));
        }catch (Exception ex){
            ManegerLog.printLogError("Erro ao buscar consulta por Id", ex);
            throw new ConsultaDataBaseException(ex.getMessage());
        }
        return consultaOptional.map(mapper::paraDomain);
    }

    public List<Consulta> buscarPorConsultas(EntityManager em) throws ConsultaDataBaseException {
        String jpql = "SELECT c FROM Consulta c";
        List<Consulta> consultas;

        try{
            consultas = mapper.paraDomainsDeEntitys(em.createQuery(jpql, ConsultaEntity.class).getResultList());
        }catch (Exception ex){
            ManegerLog.printLogError("Erro ao buscar por consultas", ex);
            throw new ConsultaDataBaseException(ex.getMessage());
        }

        return consultas;
    }

    public void alterar(Consulta consulta, EntityManager em) throws ConsultaDataBaseException {
        try{
            em.merge(mapper.paraEntity(consulta));
        }catch (Exception ex){
            ManegerLog.printLogError("Erro ao alterar consulta", ex);
            throw new ConsultaDataBaseException(ex.getMessage());
        }
    }
}
