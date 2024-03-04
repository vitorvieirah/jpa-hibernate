package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dao.MedicoDao;
import org.example.domain.Medico;
import org.example.exception.MedicoDataBaseException;
import org.example.main.dados.DadosMedico;
import org.example.main.dados.DadosPaciente;
import org.example.mapper.MedicoMapper;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class MedicoSerivce {

    private final MedicoDao dao;
    private final MedicoMapper mapper;
    private final EntityManager em;


    public void cadastrar(DadosMedico medico) throws MedicoDataBaseException {
        em.getTransaction().begin();
        Optional<Medico> oMedico = dao.consultarPorCrm(medico.crm(), em);

        oMedico.ifPresent(p -> {
            throw new RuntimeException("Medico já existe");
        });

        dao.salvar(mapper.paraDomainDeDados(medico), em);
        em.getTransaction().commit();
        em.close();
    }

    public List<Medico> consultarTodos() throws MedicoDataBaseException {
        em.getTransaction().begin();
        List<Medico> medicos =  dao.consultarTodos(em);
        em.close();
        return medicos;
    }

    public Medico consultarPorCrm(String crm) throws MedicoDataBaseException {
        em.getTransaction().begin();
        Optional<Medico> medico = dao.consultarPorCrm(crm, em);
        em.close();

        if(medico.isEmpty())
            throw new RuntimeException("Medico não encontrado");
        else
            return medico.get();
    }

    public void alterar(DadosMedico medico) throws MedicoDataBaseException {

        em.getTransaction().begin();
        Optional<Medico> oMedico = dao.consultarPorCrm(medico.crm(), em);

        if (oMedico.isPresent()){
            oMedico.get().alterarDados(medico);
            dao.salvar(oMedico.get(), em);
        }else
            throw new RuntimeException("Medico nao encontrado");

        em.getTransaction().commit();
        em.close();
    }

    public void deletar(String crm) throws MedicoDataBaseException {
        em.getTransaction().begin();
        dao.deletar(crm, em);
        em.getTransaction().commit();
        em.close();
    }
}
