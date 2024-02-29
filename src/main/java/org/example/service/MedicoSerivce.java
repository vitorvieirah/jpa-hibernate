package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dao.MedicoDao;
import org.example.domain.Medico;
import org.example.exception.MedicoDataBaseException;
import org.example.main.dados.DadosMedico;
import org.example.main.dados.DadosPaciente;
import org.example.mapper.MedicoMapper;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class MedicoSerivce {

    private final MedicoDao dao;
    private final MedicoMapper mapper;


    public void cadastrar(DadosMedico medico) throws MedicoDataBaseException {
        Optional<Medico> oMedico = dao.consultarPorCrm(medico.crm());

        oMedico.ifPresent(p -> {
            throw new RuntimeException("Medico já existe");
        });

        dao.salvar(mapper.paraDomainDeDados(medico));
    }

    public List<Medico> consultarTodos() throws MedicoDataBaseException {
        return dao.consultarTodos();
    }

    public Medico consultarPorCrm(String crm) throws MedicoDataBaseException {
        Optional<Medico> medico = dao.consultarPorCrm(crm);

        if(medico.isEmpty())
            throw new RuntimeException("Medico não encontrado");
        else
            return medico.get();
    }

    public void alterar(DadosMedico medico) throws MedicoDataBaseException {

        Optional<Medico> oMedico = dao.consultarPorCrm(medico.crm());

        if (oMedico.isPresent()){
            oMedico.get().alterarDados(medico);
            dao.salvar(oMedico.get());
        }else
            throw new RuntimeException("Medico nao encontrado");
    }

    public void deletar(String crm) throws MedicoDataBaseException {
        dao.deletar(crm);
    }
}
