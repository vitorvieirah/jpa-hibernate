package org.example.dao;

import lombok.AllArgsConstructor;
import org.example.domain.Paciente;
import org.example.entity.PacienteEntity;
import org.example.exception.PacienteDataBaseException;
import org.example.mapper.PacienteMapper;

import javax.persistence.EntityManager;
import java.util.Optional;

@AllArgsConstructor
public class PacienteDao {

    private final EntityManager em;
    private final PacienteMapper mapper;

    public Optional<Paciente> buscarPorCpf(String cpf) throws PacienteDataBaseException {
        Optional<PacienteEntity> oPaciente;

        try{
            oPaciente =  Optional.of(em.find(PacienteEntity.class, cpf));
        }catch (Exception ex){
            throw new PacienteDataBaseException(ex.getMessage());
        }

        return oPaciente.map(mapper::paraDomain);
    }

    public void salvar(Paciente paciente) throws PacienteDataBaseException {
        try{
            em.persist(mapper.paraEntity(paciente));
        }catch (Exception ex){
            throw new PacienteDataBaseException(ex.getMessage());
        }
    }

    public void deletar(String cpf) throws PacienteDataBaseException {
        try {
            Optional<Paciente> paciente = buscarPorCpf(cpf);
            paciente.ifPresent(p -> em.remove(mapper.paraEntity(paciente.get())));
        }catch (Exception ex){
            throw new PacienteDataBaseException(ex.getMessage());
        }
    }
}
