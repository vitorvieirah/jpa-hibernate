package org.example.dao;

import lombok.AllArgsConstructor;
import org.example.dao.log.ManegerLog;
import org.example.domain.Paciente;
import org.example.entity.PacienteEntity;
import org.example.exception.PacienteDataBaseException;
import org.example.mapper.PacienteMapper;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class PacienteDao {

    private final PacienteMapper mapper;

    public Optional<Paciente> buscarPorCpf(String cpf, EntityManager em) throws PacienteDataBaseException {
        String jpql = "SELECT p FROM Paciente p WHERE p.cpf = :cpf";

        try {
            PacienteEntity pacienteEntity = em.createQuery(jpql, PacienteEntity.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();

            return Optional.ofNullable(mapper.paraDomain(pacienteEntity));
        } catch (NoResultException ex) {
            System.out.println("Nenhum paciente encontrado para o CPF: " + cpf);
            return Optional.empty();
        } catch (Exception ex) {
            ManegerLog.printLogError("Erro ao buscar paciente por CPF", ex);
            throw new PacienteDataBaseException(ex.getMessage());
        }
    }


    public void salvar(Paciente paciente, EntityManager em) throws PacienteDataBaseException {

        try{
            em.persist(mapper.paraEntity(paciente));
        }catch (Exception ex){
            ManegerLog.printLogError("Erro ao salvar paciente", ex);
            throw new PacienteDataBaseException(ex.getMessage());
        }
    }

    public void deletar(String cpf, EntityManager em) throws PacienteDataBaseException {
        Optional<Paciente> paciente = buscarPorCpf(cpf, em);
        try {
           if(paciente.isPresent()) {
               PacienteEntity pacienteEntity = mapper.paraEntity(paciente.get());
               em.merge(pacienteEntity);
               em.remove(pacienteEntity);
           }else
               throw new RuntimeException("Paciente não encontrado");
        }catch (Exception ex){
            ManegerLog.printLogError("Erro ao deletar paciente", ex);
            throw new PacienteDataBaseException(ex.getMessage());
        }
    }

    public List<Paciente> buscarTodosPacientes(EntityManager em) throws PacienteDataBaseException {
        String jpql = "SELECT p FROM Paciente p";
        List<Paciente> pacientes;

        try {
            pacientes = mapper.paraDomainsDeEntitys(em.createQuery(jpql, PacienteEntity.class).getResultList());
        }catch (Exception ex){
            ManegerLog.printLogError("Erro ao buscar todos os pacientes", ex);
            throw new PacienteDataBaseException(ex.getMessage());
        }

        return pacientes;
    }

    public void alterar(Paciente paciente, EntityManager em) throws PacienteDataBaseException {
        try{
            em.merge(mapper.paraEntity(paciente));
        }catch (Exception ex){
            ManegerLog.printLogError("Erro ao alterar paciente", ex);
            throw new PacienteDataBaseException(ex.getMessage());
        }
    }
}
