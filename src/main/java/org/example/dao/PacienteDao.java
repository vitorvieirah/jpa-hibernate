package org.example.dao;

import lombok.AllArgsConstructor;
import org.example.domain.Paciente;
import org.example.entity.PacienteEntity;
import org.example.exception.PacienteDataBaseException;
import org.example.mapper.PacienteMapper;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class PacienteDao {

    private final EntityManager em;
    private final PacienteMapper mapper;

    public Optional<Paciente> buscarPorCpf(String cpf) throws PacienteDataBaseException {
        Optional<PacienteEntity> oPaciente;

        try{
            em.getTransaction().begin();
            oPaciente =  Optional.of(em.find(PacienteEntity.class, cpf));
            em.close();
        }catch (Exception ex){
            throw new PacienteDataBaseException(ex.getMessage());
        }

        return oPaciente.map(mapper::paraDomain);
    }

    public void salvar(Paciente paciente) throws PacienteDataBaseException {
        try{
            em.getTransaction().begin();
            em.persist(mapper.paraEntity(paciente));
            em.getTransaction().commit();
            em.close();
        }catch (Exception ex){
            throw new PacienteDataBaseException(ex.getMessage());
        }
    }

    public void deletar(String cpf) throws PacienteDataBaseException {
        Optional<Paciente> paciente = buscarPorCpf(cpf);
        try {
            em.getTransaction().begin();
            paciente.ifPresent(p -> em.remove(mapper.paraEntity(paciente.get())));
            em.getTransaction().commit();
            em.close();
        }catch (Exception ex){
            throw new PacienteDataBaseException(ex.getMessage());
        }
    }

    public List<Paciente> buscarTodosPacientes() throws PacienteDataBaseException {
        String jpql = "SELECT p FROM PacienteEntity p";
        List<Paciente> pacientes;

        try {
            em.getTransaction().begin();
            pacientes = mapper.paraDomainsDeEntitys(em.createQuery(jpql, PacienteEntity.class).getResultList());
            em.close();
        }catch (Exception ex){
            throw new PacienteDataBaseException(ex.getMessage());
        }

        return pacientes;
    }
}
