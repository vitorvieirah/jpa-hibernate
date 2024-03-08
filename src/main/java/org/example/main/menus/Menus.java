package org.example.main.menus;

import org.example.domain.Paciente;
import java.util.Scanner;
public abstract class Menus {

    private static final Scanner sc = new Scanner(System.in);

    public static int menuPrincipal(){
        System.out.println("""
                1 - MENU PACIENTES
                2 - MENU MEDICOS
                3 - MENU CONSULTAS
                4 - SAIR
                """);
        return sc.nextInt();
    }

    public static void mensagemSucesso (){
        System.out.println("Operação realizada com sucesso");
    }

    public static void printPaciente(Paciente paciente){
        System.out.println("Paciente encontrado: ");
        System.out.println("Nome: " + paciente.getNome() + "\n"
                + "Idade: " + paciente.getIdade() + "\n"
                + "Cpf: " + paciente.getCpf() + "\n"
                + "Email: " + paciente.getEmail());
    }
}
