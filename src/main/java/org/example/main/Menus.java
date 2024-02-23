package org.example.main;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.exception.ConsultaDataBaseException;
import org.example.exception.MedicoDataBaseException;
import org.example.exception.PacienteDataBaseException;
import org.example.main.dados.DadosMedico;
import org.example.main.dados.DadosPaciente;
import org.example.service.ConsultaSerivce;
import org.example.service.MedicoSerivce;
import org.example.service.PacienteSerivce;

import java.time.LocalDate;
import java.util.Scanner;

@RequiredArgsConstructor
public class Menus {

    private static final Scanner sc = new Scanner(System.in);
    @NonNull private final PacienteSerivce servicePaciente;
    @NonNull private final MedicoSerivce serviceMedico;
    @NonNull private final ConsultaSerivce serviceConsulta;

    public int menuPrincipal(){
        System.out.println("""
                1 - MENU PACIENTES
                2 - MENU MEDICOS
                3 - MENU CONSULTAS
                """);
        return sc.nextInt();
    }

    public int menuPaciente(){
        System.out.println("""
                1 - CADASTRAR PACIENTE
                2 - CONSULTAR TODOS PACIENTES
                3 - CONSULTAR PACIENTE PELO CPF
                4 - ALTERAR DADOS DO PACIENTE
                5 - DELETAR PACIENTE
                """);
        return sc.nextInt();
    }

    public void menuCadastroPaciente() throws PacienteDataBaseException {
        System.out.println("Digite o cpf do paciente: ");
        String cpf = sc.next();
        System.out.println("Digite o nome do paciente: ");
        String nome = sc.next();
        System.out.println("Digite a idade do paciente: ");
        Integer idade = sc.nextInt();
        System.out.println("Digite o email do paciente: ");
        String email = sc.next();

         servicePaciente.cadastrar(DadosPaciente.builder()
                 .cpf(cpf)
                 .nome(nome)
                 .idade(idade)
                 .email(email)
                 .build());

         mensagemSucesso();
    }

    public void menuConsultaTodosOsPacientes () throws PacienteDataBaseException {
        servicePaciente.buscarTodosPacientes().forEach(System.out::println);
    }

    public void menuConsultaPacientePorCpf () throws PacienteDataBaseException {
        System.out.println("Digiete o cpf do paciente: ");
        System.out.println("Paciente encotrado: " + servicePaciente.buscarPorCpf(sc.next()));
    }

    public void menuAlteraDadosPaciente() throws PacienteDataBaseException {
        System.out.println("""
                1 - ALTERAR APENAS CPF PACIENTE
                2 - ALTERAR APENAS O NOME DO PACIENTE
                3 - ALTERAR APENAS A IDADE DO PACIENTE
                4 - ALTERAR APENAS O EMAIL DO PACIENTE
                5 - ALTERAR TODAS AS INFORMAÇÕES
                """);

        switch (sc.nextInt()){
            case 1:{
                System.out.println("Digite o novo cpf do paciente: ");
                servicePaciente.alterar(DadosPaciente.builder().cpf(sc.next()).build());
                mensagemSucesso();
            }
            case 2:{
                System.out.println("Digite o novo nome do paciente: ");
                servicePaciente.alterar(DadosPaciente.builder().nome(sc.next()).build());
                mensagemSucesso();
            }
            case 3:{
                System.out.println("Digite a nova idade do paciente: ");
                servicePaciente.alterar(DadosPaciente.builder().idade(sc.nextInt()).build());
                mensagemSucesso();
            }
            case 4:{
                System.out.println("Digite o novo email do paciente: ");
                servicePaciente.alterar(DadosPaciente.builder().email(sc.next()).build());
                mensagemSucesso();
            }
            default:{
                System.out.println("Digite o novo cpf do paciente: ");
                String cpf = sc.next();
                System.out.println("Digite o novo nome do paciente: ");
                String nome = sc.next();
                System.out.println("Digite a nova idade do paciente: ");
                Integer idade = sc.nextInt();
                System.out.println("Digite o novo email do paciente: ");
                String email = sc.next();

                servicePaciente.alterar(DadosPaciente.builder().cpf(cpf).nome(nome).email(email).idade(idade).build());

                mensagemSucesso();
            }
        }
    }

    public void menuDeletarPaciente() throws PacienteDataBaseException {
        System.out.println("Digite o cpf do paciente que deseja deletar: ");
        servicePaciente.deletar(sc.next());
        mensagemSucesso();
    }

    public int menuMedicos(){
        System.out.println("""
                1 - CADASTRAR MÉDICO
                2 - CONSULTAR TODOS OS MÉDICOS
                3 - CONSULTAR MÉDICO POR CRM
                4 - ALTERAR DADOS DO MÉDICO
                5 - DELETAR MÉDICO
                """);
        return sc.nextInt();
    }

    public void menuCadastroMedicos() throws MedicoDataBaseException {
        System.out.println("Digite o nome do médico: ");
        String nome = sc.next();
        System.out.println("Digite a especialidade do médico: ");
        String especialidade = sc.next();

        serviceMedico.cadastrar(DadosMedico.builder().nome(nome).especialidade(especialidade).build());

        mensagemSucesso();
    }

    public void menuConsultarTodosOsMedicos () throws MedicoDataBaseException {
        serviceMedico.consultarTodos().forEach(System.out::println);
    }

    public void menuConsultarMedicoPorCrm (String crm) throws MedicoDataBaseException {
        System.out.println("Digite o crm do médico: ");
        System.out.println("Médico encontrado: " + serviceMedico.consultarPorCrm(crm));
    }

    public void menuAlterarDadosMedico () throws MedicoDataBaseException {
        System.out.println("""
                1 - ALTERAR APENAS O NOME DO MÉDICO
                2 - ALTERAR APENAS A ESPECIALIDADE DO MÉDICO
                3 - ALTERAR TODAS AS INFORMAÇÕES
                """);

        switch (sc.nextInt()){
            case 1:{
                System.out.println("Digite o novo nome do médico: ");
                serviceMedico.alterar(DadosMedico.builder().nome(sc.next()).build());
                mensagemSucesso();
            }
            case 2:{
                System.out.println("Digite a nova especialidade do médico: ");
                serviceMedico.alterar(DadosMedico.builder().especialidade(sc.next()).build()) ;
                mensagemSucesso();
            }
            default:{
                System.out.println("Digite o novo nome: ");
                String nome = sc.next();
                System.out.println("Digite a nova especialidade: ");
                String especialidade = sc.next();

                serviceMedico.alterar(DadosMedico.builder().nome(nome).especialidade(especialidade).build());
                mensagemSucesso();
            }
        }
    }

    public void menuDeletarMedicos () throws MedicoDataBaseException {
        System.out.println("Digite o id do médico que deseja deletar: ");
        serviceMedico.deletar(sc.nextLong());
        mensagemSucesso();
    }

    public int menConsultas(){
        System.out.println("""
                1 - MARCAR CONSULTA
                2 - ACESSAR CONSULTA
                3 - CANCELAR CONSULTA
                4 - REMARCAR CONSULTA
                """);
        return sc.nextInt();
    }

    public void menuCadastroConsulta () throws PacienteDataBaseException, MedicoDataBaseException, ConsultaDataBaseException {
        System.out.println("Digite o id do médico: ");
        String crm = sc.next();
        System.out.println("Digite o cpf do paciente: ");
        String cpf = sc.next();

        System.out.println(serviceConsulta.marcar(crm, cpf));
    }

    public void menuAcessarConsultaPorId (Long id) throws ConsultaDataBaseException {
        System.out.println("Digite o id da consulta: ");
        System.out.println("Consulta encontrada: " + serviceConsulta.buscarConsultaPorId(id));
    }

    public void menuCancelarConsulta() throws ConsultaDataBaseException {
        System.out.println("Digite o id da consulta que deseja cancelar: ");
        serviceConsulta.cancelar(sc.nextLong());
        mensagemSucesso();
    }

    public void menuRemarcarConsulta () throws MedicoDataBaseException, ConsultaDataBaseException {
        System.out.println("Digite o id da consulta: ");
        Long id = sc.nextLong();
        System.out.println("Digite o nova data da consulta: ");
        LocalDate data = LocalDate.parse(sc.next());

        System.out.println(serviceConsulta.remarcar(id, data));
    }

    private static void mensagemSucesso (){
        System.out.println("Operação realizada com sucesso");
    }
}
