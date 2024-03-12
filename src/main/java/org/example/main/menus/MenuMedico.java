package org.example.main.menus;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.exception.MedicoDataBaseException;
import org.example.main.dados.DadosMedico;
import org.example.service.MedicoSerivce;

import javax.persistence.EntityManager;
import java.util.Scanner;

import static org.example.main.menus.Menus.mensagemSucesso;

@RequiredArgsConstructor
public class MenuMedico {
    private static final Scanner sc = new Scanner(System.in);
    @NonNull private final MedicoSerivce serviceMedico;
    @NonNull private final EntityManager em;

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
        System.out.println("Digite a especialidade do médico: ");
        String especialidade = sc.next();
        System.out.println("Digite o nome do médico: ");
        String nome = sc.next();
        System.out.println("Digite seu crm: ");
        String crm = sc.next();

        serviceMedico.cadastrar(DadosMedico.builder().nome(nome).especialidade(especialidade).crm(crm).build(), em);

        mensagemSucesso();
    }

    public void menuConsultarTodosOsMedicos () throws MedicoDataBaseException {
        serviceMedico.consultarTodos(em).forEach(System.out::println);
    }

    public void menuConsultarMedicoPorCrm () throws MedicoDataBaseException {
        System.out.println("Digite o crm do médico: ");
        System.out.println("Médico encontrado: " + serviceMedico.consultarPorCrm(sc.next(), em));
    }

    public void menuAlterarDadosMedico () throws MedicoDataBaseException {
        System.out.println("""
                1 - ALTERAR APENAS O NOME DO MÉDICO
                2 - ALTERAR APENAS A ESPECIALIDADE DO MÉDICO
                3 - ALTERAR TODAS AS INFORMAÇÕES
                """);

        switch (sc.nextInt()) {
            case 1: {
                System.out.println("Digite o novo nome do médico: ");
                serviceMedico.alterar(DadosMedico.builder().nome(sc.next()).build(), em);
                mensagemSucesso();
            } break;
            case 2: {
                System.out.println("Digite a nova especialidade do médico: ");
                serviceMedico.alterar(DadosMedico.builder().especialidade(sc.next()).build(), em);
                mensagemSucesso();
            } break;
            default: {
                System.out.println("Digite o novo nome: ");
                String nome = sc.next();
                System.out.println("Digite a nova especialidade: ");
                String especialidade = sc.next();

                serviceMedico.alterar(DadosMedico.builder().nome(nome).especialidade(especialidade).build(), em);
                mensagemSucesso();
            }
        }
    }

    public void menuDeletarMedicos () throws MedicoDataBaseException {
        System.out.println("Digite o crm do médico que deseja deletar: ");
        serviceMedico.deletar(sc.next(), em);
        mensagemSucesso();
    }
}
