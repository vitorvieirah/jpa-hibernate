package org.example.dao.log;

public abstract class ManegerLog {
    public static void printLogError(String mensage, Exception ex){
        System.out.println("Mensagem: " + mensage + "\n" + "Exception: " + ex.getMessage());
    }
}
