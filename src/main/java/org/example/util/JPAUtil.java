package org.example.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("jpa-hibernate");

    public static EntityManager getEntityManeger (){
        return FACTORY.createEntityManager();
    }
}
