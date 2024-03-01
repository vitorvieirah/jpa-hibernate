package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dao.PacienteDao;
import org.example.domain.Paciente;
import org.example.entity.PacienteEntity;
import org.example.exception.PacienteDataBaseException;
import org.example.main.dados.DadosPaciente;
import org.example.mapper.PacienteMapper;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

@AllArgsConstructor
public class PacienteSerivce {

    private final PacienteDao dao;
    private final PacienteMapper mapper;
    private final EntityManager em;

    public void cadastrar(DadosPaciente paciente) throws PacienteDataBaseException {

        em.getTransaction().begin();
        Optional<Paciente> oPaciente = dao.buscarPorCpf(paciente.cpf(), em);
        oPaciente.ifPresent(p -> {
            throw new RuntimeException("Paciente ja existe");
        });

        dao.salvar(mapper.paraDomainDeDados(paciente), em);
        em.getTransaction().commit();
        em.close();
    }

    public void alterar(DadosPaciente paciente) throws PacienteDataBaseException {
        em.getTransaction().begin();
        Optional<Paciente> oPaciente = dao.buscarPorCpf(paciente.cpf(), em);

        if(oPaciente.isPresent()){
            oPaciente.get().alterarDados(paciente);
            dao.salvar(oPaciente.get(), em);
        }
        else
            throw new RuntimeException("Paciente não encontrado");
        em.getTransaction().commit();
        em.close();
    }
    
    public List<Paciente> buscarTodosPacientes () throws PacienteDataBaseException {
        em.getTransaction().begin();
        List<Paciente> pacienteList = dao.buscarTodosPacientes(em);
        em.close();

        return pacienteList;
    }

    public void deletar(String cpf) throws PacienteDataBaseException {
        em.getTransaction().begin();
        dao.deletar(cpf, em);
        em.getTransaction().commit();
        em.close();
    }

    public Paciente buscarPorCpf(String cpf) throws PacienteDataBaseException {
        Optional<Paciente> paciente = dao.buscarPorCpf(cpf);

        if(paciente.isEmpty())
            throw new RuntimeException("Paciente não encontrado");
        else
            return paciente.get();
    }
}
