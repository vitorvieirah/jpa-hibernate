package org.example.dao;

import lombok.AllArgsConstructor;
import org.example.domain.Consulta;
import org.example.entity.ConsultaEntity;
import org.example.exception.ConsultaDataBaseException;
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

    public List<Consulta> buscarConsultasPorMedico(String crm) throws ConsultaDataBaseException {
        String jpql = "SELECT c FROM ConsultaEntity c WHERE c.medico.crm = :crm";
        List<ConsultaEntity> consultas;

        try {
            em.getTransaction().begin();
            consultas = em.createQuery(jpql, ConsultaEntity.class)
                    .setParameter("crm", crm)
                    .getResultList();
            em.getTransaction().commit();
            em.close();
        }catch (Exception ex){
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
            throw new ConsultaDataBaseException(ex.getMessage());
        }
        return consultaOptional.map(mapper::paraDomain);
    }

    public List<Consulta> buscarPorConsultas() throws ConsultaDataBaseException {
        String jpql = "SELECT c FROM ConsultaEntity c";
        List<Consulta> consultas;

        try{
            em.getTransaction().begin();
            consultas = mapper.paraDomainsDeEntitys(em.createQuery(jpql, ConsultaEntity.class).getResultList());
            em.getTransaction().commit();
            em.close();
        }catch (Exception ex){
            throw new ConsultaDataBaseException(ex.getMessage());
        }

        return consultas;
    }
}
