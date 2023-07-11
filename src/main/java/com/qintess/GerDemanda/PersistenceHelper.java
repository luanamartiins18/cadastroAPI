package com.qintess.GerDemanda;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceHelper {

    private static String PERSISTENCE_UNIT_NAME = "PU";
    private static EntityManagerFactory entityManagerFactory;

    static{

        try{
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }catch(Throwable excp){
            System.err.println("Não foi possivel iniciar a unidade de persistência ");
            throw new RuntimeException(excp);
        }

    }

    public static EntityManagerFactory getEntityManagerFactory(){
        return entityManagerFactory;
    }


}
