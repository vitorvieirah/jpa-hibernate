package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.Consulta;
import org.example.entity.ConsultaEntity;
import org.example.exception.ConsultaDataBaseException;
import org.example.mapper.ConsultaMapper;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class ConsultaDao {
    
    private final EntityManager em;
    private final ConsultaMapper mapper;

    public List<Consulta> buscarConsultasPorMedico(String crm) throws ConsultaDataBaseException {
        String jpql = "SELECT c FROM Consulta c WHERE c.medico.crm = :crm";
        List<ConsultaEntity> consultas;

        try {
            em.getTransaction().begin();
            consultas = em.createQuery(jpql, ConsultaEntity.class)
                    .setParameter("crm", crm)
                    .getResultList();
            em.getTransaction().commit();
            em.close();
        }catch (Exception ex){
            log.error("Erro ao buscar consultas por médico", ex);
            throw new ConsultaDataBaseException(ex.getMessage());
        }
        return mapper.paraDomainsDeEntitys(consultas);
    }

    public void salvar(Consulta consulta) throws ConsultaDataBaseException {
        try {
            em.getTransaction().begin();
            em.persist(mapper.paraEntity(consulta));
            em.getTransaction().commit();
            em.close();
        }catch (Exception ex){
            log.error("Erro ao salvar consulta", ex);
            throw new ConsultaDataBaseException(ex.getMessage());
        }
    }

    public void deletar (Consulta consulta) throws ConsultaDataBaseException {
        try{
            em.getTransaction().begin();
            em.remove(mapper.paraEntity(consulta));
            em.getTransaction().commit();
            em.close();
        }catch (Exception ex){
            log.error("Erro ao deletar consulta", ex);
            throw new ConsultaDataBaseException(ex.getMessage());
        }
    }

    public Optional<Consulta> buscarPorId(Long idConsulta) throws ConsultaDataBaseException {
        Optional<ConsultaEntity> consultaOptional;
        try {
            em.getTransaction().commit();
            consultaOptional = Optional.of(em.find(ConsultaEntity.class, idConsulta));
            em.getTransaction().commit();
            em.close();
        }catch (Exception ex){
            log.error("Erro ao buscar consulta por Id", ex);
            throw new ConsultaDataBaseException(ex.getMessage());
        }
        return consultaOptional.map(mapper::paraDomain);
    }

    public List<Consulta> buscarPorConsultas() throws ConsultaDataBaseException {
        String jpql = "SELECT c FROM Consulta c";
        List<Consulta> consultas;

        try{
            em.getTransaction().begin();
            consultas = mapper.paraDomainsDeEntitys(em.createQuery(jpql, ConsultaEntity.class).getResultList());
            em.getTransaction().commit();
            em.close();
        }catch (Exception ex){
            log.error("Erro ao buscar por consultas", ex);
            throw new ConsultaDataBaseException(ex.getMessage());
        }

        return consultas;
    }
}
