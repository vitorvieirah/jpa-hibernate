package org.example.dao;

import lombok.AllArgsConstructor;
import org.example.dao.log.ManegerLog;
import org.example.domain.Medico;
import org.example.entity.MedicoEntity;
import org.example.exception.MedicoDataBaseException;
import org.example.mapper.MedicoMapper;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class MedicoDao {

    private final MedicoMapper mapper;

    public Optional<Medico> consultarPorCrm(String crm, EntityManager em) throws MedicoDataBaseException {
        String jpql = "SELECT m FROM Medico m WHERE m.crm = :crm";
        try {
            MedicoEntity medicoEntity = em.createQuery(jpql, MedicoEntity.class)
                    .setParameter("crm", crm)
                    .getSingleResult();
            return Optional.ofNullable(mapper.paraDomain(medicoEntity));
        }catch (NoResultException ex){
            System.out.println("Nenhum médico encontrado para o crm: " + crm);
            return Optional.empty();
        }catch (Exception ex){
            ManegerLog.printLogError("Erro ao consultar médico por crm", ex);
            throw new MedicoDataBaseException(ex.getMessage());
        }
    }

    public List<Medico> consultarTodos(EntityManager em) throws MedicoDataBaseException {
        String jpql = " SELECT m FROM Medico m";
        List<MedicoEntity> resultMedicos;

        try {
            resultMedicos = em.createQuery(jpql, MedicoEntity.class).getResultList();
        }catch (Exception ex){
            ManegerLog.printLogError("Erro ao consultar todos os médicos", ex);
            throw new MedicoDataBaseException(ex.getMessage());
        }

        return mapper.paraDomainsDeEntitys(resultMedicos);
    }

    public void salvar(Medico medico, EntityManager em) throws MedicoDataBaseException {
        try {
            em.persist(mapper.paraEntity(medico));
        }catch (Exception ex){
            ManegerLog.printLogError("Erro ao salvar médico", ex);
            throw new MedicoDataBaseException(ex.getMessage());
        }
    }

    public void alterar (Medico medico, EntityManager em) throws MedicoDataBaseException{
        try{
            em.merge(mapper.paraEntity(medico));
        }catch (Exception ex){
            ManegerLog.printLogError("Erro ao allterar médico", ex);
            throw new MedicoDataBaseException(ex.getMessage());
        }
    }

    public void deletar(String crm, EntityManager em) throws MedicoDataBaseException {
        try {
            MedicoEntity medicoEntity = em.getReference(MedicoEntity.class, crm);
            em.remove(medicoEntity);
        }catch (EntityNotFoundException ex){
            ManegerLog.printLogError("Médico não encontrado", ex);
        }catch (Exception ex){
            ManegerLog.printLogError("Erro ao deletar médico", ex);
            throw new MedicoDataBaseException(ex.getMessage());
        }
    }
}
