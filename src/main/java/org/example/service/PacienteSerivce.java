package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dao.PacienteDao;
import org.example.domain.Paciente;
import org.example.exception.PacienteDataBaseException;
import org.example.main.dados.DadosPaciente;
import org.example.mapper.PacienteMapper;

import java.util.Optional;
import java.util.zip.DataFormatException;

@AllArgsConstructor
public class PacienteSerivce {

    private final PacienteDao dao;
    private final PacienteMapper mapper;

    public void cadastrar(DadosPaciente paciente) throws PacienteDataBaseException {

        Optional<Paciente> oPaciente = dao.buscarPorCpf(paciente.cpf());
        oPaciente.ifPresent(p -> {
            throw new RuntimeException("Paciente ja existe");
        });

        dao.salvar(mapper.paraDomainDeDados(paciente));
    }

    public void alterar(DadosPaciente paciente) throws PacienteDataBaseException {
        Optional<Paciente> oPaciente = dao.buscarPorCpf(paciente.cpf());

        if(oPaciente.isPresent()){
            oPaciente.get().alterarDados(paciente);
            dao.salvar(oPaciente.get());
        }
        else
            throw new RuntimeException("Paciente não encontrado");
    }

    public void deletar(String cpf) throws PacienteDataBaseException {
        dao.deletar(cpf);
    }
}
