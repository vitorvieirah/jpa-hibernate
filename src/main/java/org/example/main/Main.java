package org.example.main;


import org.example.dao.ConsultaDao;
import org.example.dao.MedicoDao;
import org.example.dao.PacienteDao;
import org.example.exception.ConsultaDataBaseException;
import org.example.exception.MedicoDataBaseException;
import org.example.exception.PacienteDataBaseException;
import org.example.mapper.ConsultaMapper;
import org.example.mapper.MedicoMapper;
import org.example.mapper.PacienteMapper;
import org.example.service.ConsultaSerivce;
import org.example.service.MedicoSerivce;
import org.example.service.PacienteSerivce;
import org.example.util.JPAUtil;

import javax.persistence.EntityManager;

public class Main {

    public static void main(String[] args) throws PacienteDataBaseException, MedicoDataBaseException, ConsultaDataBaseException {

        EntityManager em = JPAUtil.getEntityManeger();

        PacienteMapper pacienteMapper = new PacienteMapper();
        MedicoMapper medicoMapper = new MedicoMapper();
        ConsultaMapper consultaMapper = new ConsultaMapper(pacienteMapper, medicoMapper);

        ConsultaDao consultaDao = new ConsultaDao(em, consultaMapper);
        MedicoDao medicoDao = new MedicoDao(em, medicoMapper);
        PacienteDao pacienteDao = new PacienteDao(em, pacienteMapper);

        ConsultaSerivce consultaSerivce = new ConsultaSerivce(pacienteDao, medicoDao, consultaDao);
        MedicoSerivce medicoSerivce = new MedicoSerivce(medicoDao, medicoMapper);
        PacienteSerivce pacienteSerivce = new PacienteSerivce(pacienteDao, pacienteMapper);

        Menus menus = new Menus(pacienteSerivce, medicoSerivce, consultaSerivce);

        switch (menus.menuPrincipal()){
            case 1 :{
                switch (menus.menuPaciente()){
                    case 1: menus.menuCadastroPaciente();
                    case 2: menus.menuConsultaTodosOsPacientes();
                    case 3: menus.menuConsultaPacientePorCpf();
                    case 4: menus.menuAlteraDadosPaciente();
                    case 5: menus.menuDeletarPaciente();
                }
            }
            case 2: {
                switch (menus.menuMedicos()){
                    case 1: menus.menuCadastroMedicos();
                    case 2: menus.menuConsultarMedico();
                }
            }


        }
    }

}