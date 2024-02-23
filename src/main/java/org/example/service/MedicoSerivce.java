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
        Optional<Medico> oMedico = dao.consultarPorId(medico.id());

        oMedico.ifPresent(p -> {
            throw new RuntimeException("Medico já existe");
        });

        dao.salvar(mapper.paraDomainDeDados(medico));
    }

    public List<Medico> consultar() throws MedicoDataBaseException {
        return dao.consultarTodos();
    }

    public void alterar(DadosMedico medico) throws MedicoDataBaseException {

        Optional<Medico> oMedico = dao.consultarPorId(medico.id());

        if (oMedico.isPresent()){
            oMedico.get().alterarDados(medico);
            dao.salvar(oMedico.get());
        }else
            throw new RuntimeException("Medico nao encontrado");
    }

    public void deletar(Long id) throws MedicoDataBaseException {
        dao.deletar(id);
    }
}
