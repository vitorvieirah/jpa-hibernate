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

        ConsultaDao consultaDao = new ConsultaDao(consultaMapper);
        MedicoDao medicoDao = new MedicoDao(medicoMapper);
        PacienteDao pacienteDao = new PacienteDao(pacienteMapper);

        ConsultaSerivce consultaSerivce = new ConsultaSerivce(pacienteDao, medicoDao, consultaDao);
        MedicoSerivce medicoSerivce = new MedicoSerivce(medicoDao, medicoMapper);
        PacienteSerivce pacienteSerivce = new PacienteSerivce(pacienteDao, pacienteMapper);

        Menus menus = new Menus(pacienteSerivce, medicoSerivce, consultaSerivce, em);

        int op = 0;
        while (op < 4) {
            op = menus.menuPrincipal();
            switch (op) {
                case 1: {
                    op = menus.menuPaciente();
                    switch (op) {
                        case 1: menus.menuCadastroPaciente(); break;
                        case 2: menus.menuConsultaTodosOsPacientes(); break;
                        case 3: menus.menuConsultaPacientePorCpf(); break;
                        case 4: menus.menuAlteraDadosPaciente(); break;
                        case 5: menus.menuDeletarPaciente();
                    }
                } break;
                case 2: {
                    switch (menus.menuMedicos()) {
                        case 1:
                            menus.menuCadastroMedicos(); break;
                        case 2:
                            menus.menuConsultarTodosOsMedicos(); break;
                        case 3:
                            menus.menuConsultarMedicoPorCrm(); break;
                        case 4:
                            menus.menuAlterarDadosMedico(); break;
                        case 5:
                            menus.menuDeletarMedicos();
                    }
                } break;
                case 3: {
                    switch (menus.menuConsultas()) {
                        case 1:
                            menus.menuCadastroConsulta(); break;
                        case 2:
                            menus.menuAcessarConsultaPorId(); break;
                        case 3:
                            menus.menuAcessarTodasAsConsultas(); break;
                        case 4:
                            menus.menuCancelarConsulta(); break;
                        case 5:
                            menus.menuRemarcarConsulta(); break;
                    }
                }
            }
        }
    }

}