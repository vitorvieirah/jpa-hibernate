package org.example.main;

import org.example.dao.ConsultaDao;
import org.example.dao.MedicoDao;
import org.example.dao.PacienteDao;
import org.example.exception.ConsultaDataBaseException;
import org.example.exception.MedicoDataBaseException;
import org.example.exception.PacienteDataBaseException;
import org.example.main.menus.MenuConsulta;
import org.example.main.menus.MenuMedico;
import org.example.main.menus.MenuPaciente;
import org.example.main.menus.Menus;
import org.example.mapper.ConsultaMapper;
import org.example.mapper.MedicoMapper;
import org.example.mapper.PacienteMapper;
import org.example.service.ConsultaSerivce;
import org.example.service.MedicoSerivce;
import org.example.service.PacienteSerivce;
import org.example.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class Main {

    public static void main(String[] args) throws PacienteDataBaseException, MedicoDataBaseException, ConsultaDataBaseException {

        PacienteMapper pacienteMapper = new PacienteMapper();
        MedicoMapper medicoMapper = new MedicoMapper();
        ConsultaMapper consultaMapper = new ConsultaMapper(pacienteMapper, medicoMapper);

        int op = 0;
        while (op < 4) {
            op = Menus.menuPrincipal();
            switch (op) {
                case 1 -> {

                    EntityManager em = JPAUtil.getEntityManeger();
                    PacienteDao pacienteDao = new PacienteDao(pacienteMapper);
                    PacienteSerivce pacienteSerivce = new PacienteSerivce(pacienteDao, pacienteMapper);
                    MenuPaciente menus = new MenuPaciente(pacienteSerivce, em);

                    EntityTransaction transaction = em.getTransaction();

                    if (!transaction.isActive())
                        transaction.begin();

                    switch (menus.menuPaciente()) {
                        case 1 -> {
                            menus.menuCadastroPaciente();
                            transaction.commit();
                        }
                        case 2 -> {
                            menus.menuConsultaTodosOsPacientes();
                            transaction.commit();
                        }
                        case 3 -> {
                            menus.menuConsultaPacientePorCpf();
                            transaction.commit();
                        }
                        case 4 -> {
                            menus.menuAlteraDadosPaciente();
                            transaction.commit();
                        }
                        case 5 -> {
                            menus.menuDeletarPaciente();
                            transaction.commit();
                        }
                    }
                    em.close();
                }
                case 2 -> {
                    EntityManager em = JPAUtil.getEntityManeger();
                    MedicoDao medicoDao = new MedicoDao(medicoMapper);
                    MedicoSerivce medicoSerivce = new MedicoSerivce(medicoDao, medicoMapper);
                    MenuMedico menus = new MenuMedico(medicoSerivce, em);
                    EntityTransaction transaction = em.getTransaction();

                    if (!transaction.isActive())
                        transaction.begin();

                    switch (menus.menuMedicos()) {
                        case 1 -> {
                            menus.menuCadastroMedicos();
                            transaction.commit();
                        }
                        case 2 -> {
                            menus.menuConsultarTodosOsMedicos();
                            transaction.commit();
                        }
                        case 3 -> {
                            menus.menuConsultarMedicoPorCrm();
                            transaction.commit();
                        }
                        case 4 -> {
                            menus.menuAlterarDadosMedico();
                            transaction.commit();
                        }
                        case 5 -> {
                            menus.menuDeletarMedicos();
                            transaction.commit();
                        }
                    }
                    em.close();
                }
                case 3 -> {
                    EntityManager em = JPAUtil.getEntityManeger();
                    ConsultaDao consultaDao = new ConsultaDao(consultaMapper);
                    PacienteDao pacienteDao = new PacienteDao(pacienteMapper);
                    MedicoDao medicoDao = new MedicoDao(medicoMapper);
                    ConsultaSerivce consultaSerivce = new ConsultaSerivce(pacienteDao, medicoDao, consultaDao);
                    MenuConsulta menus = new MenuConsulta(consultaSerivce, em);
                    EntityTransaction transaction = em.getTransaction();

                    if (!transaction.isActive())
                        transaction.begin();

                    switch (menus.menuConsultas()) {
                        case 1 -> {
                            menus.menuCadastroConsulta();
                            transaction.commit();
                        }
                        case 2 -> {
                            menus.menuAcessarConsultaPorId();
                            transaction.commit();
                        }
                        case 3 -> {
                            menus.menuAcessarTodasAsConsultas();
                            transaction.commit();
                        }
                        case 4 -> {
                            menus.menuCancelarConsulta();
                            transaction.commit();
                        }
                        case 5 -> {
                            menus.menuRemarcarConsulta();
                            transaction.commit();
                        }
                    }
                    em.close();
                }
            }
        }
    }

}