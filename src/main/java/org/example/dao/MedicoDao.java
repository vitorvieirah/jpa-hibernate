package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.Medico;
import org.example.entity.MedicoEntity;
import org.example.exception.MedicoDataBaseException;
import org.example.mapper.MedicoMapper;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
public class MedicoDao {

    private final EntityManager em;
    private final MedicoMapper mapper;

    public Optional<Medico> consultarPorCrm(String crm) throws MedicoDataBaseException {
        Optional<MedicoEntity> oMedico;
        try {
            em.getTransaction().begin();
            oMedico = Optional.of(em.find(MedicoEntity.class, crm));
            em.close();
        }catch (Exception ex){
            log.error("Erro ao consultar médico por crm", ex);
            throw new MedicoDataBaseException(ex.getMessage());
        }

        return oMedico.map(mapper::paraDomain);
    }

    public List<Medico> consultarTodos() throws MedicoDataBaseException {
        String jpql = " SELECT m FROOM Medico m";
        List<MedicoEntity> resultMedicos;

        try {
            em.getTransaction().begin();
            resultMedicos = em.createQuery(jpql, MedicoEntity.class).getResultList();
            em.close();
        }catch (Exception ex){
            log.error("Erro ao consultar todos os medicos", ex);
            throw new MedicoDataBaseException(ex.getMessage());
        }

        return mapper.paraDomainsDeEntitys(resultMedicos);
    }

    public void salvar(Medico medico) throws MedicoDataBaseException {
        try {
            em.getTransaction().begin();
            em.persist(mapper.paraEntity(medico));
            em.getTransaction().commit();
            em.close();
        }catch (Exception ex){
            log.error("Erro ao salvar médico", ex);
            throw new MedicoDataBaseException(ex.getMessage());
        }
    }

    public void deletar(String crm) throws MedicoDataBaseException {
        try{
            em.getTransaction().begin();
            em.remove(consultarPorCrm(crm));
            em.getTransaction().commit();
            em.close();
        }catch (Exception ex){
            log.error("Erro ao deletar médico", ex);
            throw new MedicoDataBaseException(ex.getMessage());
        }
    }
}
