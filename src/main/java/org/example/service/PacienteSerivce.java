package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dao.PacienteDao;
import org.example.domain.Paciente;
import org.example.exception.PacienteDataBaseException;
import org.example.main.dados.DadosPaciente;
import org.example.mapper.PacienteMapper;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class PacienteSerivce {

    private final PacienteDao dao;
    private final PacienteMapper mapper;


    public void cadastrar(DadosPaciente paciente, EntityManager em) throws PacienteDataBaseException {
        Optional<Paciente> oPaciente = dao.buscarPorCpf(paciente.cpf(), em);
        oPaciente.ifPresent(p -> {
            throw new RuntimeException("Paciente ja existe");
        });

        dao.salvar(mapper.paraDomainDeDados(paciente), em);
    }

    public void alterar(String cpf, DadosPaciente paciente, EntityManager em) throws PacienteDataBaseException {
        Optional<Paciente> oPaciente = dao.buscarPorCpf(cpf, em);

        if(oPaciente.isPresent()){
            oPaciente.get().alterarDados(paciente);
            dao.alterar(oPaciente.get(), em);
        }
        else
            throw new RuntimeException("Paciente não encontrado");
    }
    
    public List<Paciente> buscarTodosPacientes (EntityManager em) throws PacienteDataBaseException {
        List<Paciente> pacienteList = dao.buscarTodosPacientes(em);

        return pacienteList;
    }

    public void deletar(String cpf, EntityManager em) throws PacienteDataBaseException {
        dao.deletar(cpf, em);
    }

    public Paciente buscarPorCpf(String cpf, EntityManager em) throws PacienteDataBaseException {
        Optional<Paciente> paciente = dao.buscarPorCpf(cpf, em);

        if(paciente.isEmpty())
            throw new RuntimeException("Paciente não encontrado");
        else
            return paciente.get();
    }
}
