package org.example.main.menus;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.exception.ConsultaDataBaseException;
import org.example.exception.MedicoDataBaseException;
import org.example.exception.PacienteDataBaseException;
import org.example.service.ConsultaSerivce;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Scanner;

import static org.example.main.menus.Menus.mensagemSucesso;

@RequiredArgsConstructor
public class MenuConsulta {

    private static final Scanner sc = new Scanner(System.in);
    @NonNull private final ConsultaSerivce serviceConsulta;
    @NonNull private final EntityManager em;

    public int menuConsultas(){
        System.out.println("""
                1 - MARCAR CONSULTA
                2 - ACESSAR CONSULTA
                3 - ACESSAR TODAS AS CONSULTAS
                4 - CANCELAR CONSULTA
                5 - REMARCAR CONSULTA
                """);
        return sc.nextInt();
    }

    public void menuCadastroConsulta () throws PacienteDataBaseException, MedicoDataBaseException, ConsultaDataBaseException {
        System.out.println("Digite o id do médico: ");
        String crm = sc.next();
        System.out.println("Digite o cpf do paciente: ");
        String cpf = sc.next();

        System.out.println(serviceConsulta.marcar(crm, cpf, em));
    }

    public void menuAcessarConsultaPorId () throws ConsultaDataBaseException {
        System.out.println("Digite o id da consulta: ");
        System.out.println("Consulta encontrada: " + serviceConsulta.buscarConsultaPorId(sc.nextLong(), em));
        em.close();
    }

    public void menuAcessarTodasAsConsultas () throws ConsultaDataBaseException {
        serviceConsulta.buscarTodasConsultas(em).forEach(System.out::println);
        em.close();
    }

    public void menuCancelarConsulta() throws ConsultaDataBaseException {
        System.out.println("Digite o id da consulta que deseja cancelar: ");
        serviceConsulta.cancelar(sc.nextLong(), em);
        mensagemSucesso();
        em.close();
    }

    public void menuRemarcarConsulta () throws MedicoDataBaseException, ConsultaDataBaseException {
        System.out.println("Digite o id da consulta: ");
        Long id = sc.nextLong();
        System.out.println("Digite o nova data da consulta: ");
        LocalDate data = LocalDate.parse(sc.next());

        System.out.println(serviceConsulta.remarcar(id, data, em));
        em.close();
    }
}
