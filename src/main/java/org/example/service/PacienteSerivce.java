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

    public void cadastrar(DadosPaciente paciente, EntityManager em) throws PacienteDataBaseException {

        em.getTransaction().begin();
        Optional<Paciente> oPaciente = dao.buscarPorCpf(paciente.cpf(), em);
        oPaciente.ifPresent(p -> {
            throw new RuntimeException("Paciente ja existe");
        });

        dao.salvar(mapper.paraDomainDeDados(paciente), em);
        em.getTransaction().commit();
    }

    public void alterar(DadosPaciente paciente, EntityManager em) throws PacienteDataBaseException {
        em.getTransaction().begin();
        Optional<Paciente> oPaciente = dao.buscarPorCpf(paciente.cpf(), em);

        if(oPaciente.isPresent()){
            oPaciente.get().alterarDados(paciente);
            dao.salvar(oPaciente.get(), em);
        }
        else
            throw new RuntimeException("Paciente não encontrado");
        em.getTransaction().commit();
    }
    
    public List<Paciente> buscarTodosPacientes (EntityManager em) throws PacienteDataBaseException {
        em.getTransaction().begin();
        List<Paciente> pacienteList = dao.buscarTodosPacientes(em);
        em.getTransaction().commit();

        return pacienteList;
    }

    public void deletar(String cpf, EntityManager em) throws PacienteDataBaseException {
        em.getTransaction().begin();
        dao.deletar(cpf, em);
        em.getTransaction().commit();
    }

    public Paciente buscarPorCpf(String cpf, EntityManager em) throws PacienteDataBaseException {
        em.getTransaction().begin();
        Optional<Paciente> paciente = dao.buscarPorCpf(cpf, em);
        em.getTransaction().commit();

        if(paciente.isEmpty())
            throw new RuntimeException("Paciente não encontrado");
        else
            return paciente.get();
    }
}
