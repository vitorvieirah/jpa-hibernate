package org.example.main.menus;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.exception.PacienteDataBaseException;
import org.example.main.dados.DadosPaciente;
import org.example.service.PacienteSerivce;

import javax.persistence.EntityManager;
import java.util.Scanner;

import static org.example.main.menus.Menus.mensagemSucesso;
import static org.example.main.menus.Menus.printPaciente;

@RequiredArgsConstructor
public class MenuPaciente {
    private static final Scanner sc = new Scanner(System.in);
    @NonNull private final PacienteSerivce servicePaciente;
    @NonNull private final EntityManager em;

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
        String nome = sc.nextLine();
        System.out.println("Digite a idade do paciente: ");
        Integer idade = sc.nextInt();
        System.out.println("Digite o email do paciente: ");
        String email = sc.next();

        servicePaciente.cadastrar(DadosPaciente.builder()
                .cpf(cpf)
                .nome(nome)
                .idade(idade)
                .email(email)
                .build(), em);
        mensagemSucesso();
    }

    public void menuConsultaTodosOsPacientes () throws PacienteDataBaseException {
        servicePaciente.buscarTodosPacientes(em).forEach(System.out::println);
    }

    public void menuConsultaPacientePorCpf () throws PacienteDataBaseException {
        System.out.println("Digiete o cpf do paciente: ");
        printPaciente(servicePaciente.buscarPorCpf(sc.next(), em));
    }

    public void menuAlteraDadosPaciente() throws PacienteDataBaseException {
        System.out.println("Digite seu o cpf do cliente: ");
        String cpfCliente = sc.next();
        printPaciente(servicePaciente.buscarPorCpf(cpfCliente, em));
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
                servicePaciente.alterar(cpfCliente, DadosPaciente.builder().cpf(sc.next()).build(), em);
                mensagemSucesso();
            } break;
            case 2:{
                System.out.println("Digite o novo nome do paciente: ");
                servicePaciente.alterar(cpfCliente, DadosPaciente.builder().nome(sc.next()).build(), em);
                mensagemSucesso();
            } break;
            case 3:{
                System.out.println("Digite a nova idade do paciente: ");
                servicePaciente.alterar(cpfCliente, DadosPaciente.builder().idade(sc.nextInt()).build(), em);
                mensagemSucesso();
            } break;
            case 4:{
                System.out.println("Digite o novo email do paciente: ");
                servicePaciente.alterar(cpfCliente, DadosPaciente.builder().email(sc.next()).build(), em);
                mensagemSucesso();
            }break;
            default:{
                System.out.println("Digite o novo cpf do paciente: ");
                String cpf = sc.next();
                System.out.println("Digite o novo nome do paciente: ");
                String nome = sc.next();
                System.out.println("Digite a nova idade do paciente: ");
                Integer idade = sc.nextInt();
                System.out.println("Digite o novo email do paciente: ");
                String email = sc.next();

                servicePaciente.alterar(cpf, DadosPaciente.builder().cpf(cpf).nome(nome).email(email).idade(idade).build(), em);

                mensagemSucesso();
            }
        }
    }

    public void menuDeletarPaciente() throws PacienteDataBaseException {
        System.out.println("Digite o cpf do paciente que deseja deletar: ");
        servicePaciente.deletar(sc.next(), em);
        mensagemSucesso();
    }
}
