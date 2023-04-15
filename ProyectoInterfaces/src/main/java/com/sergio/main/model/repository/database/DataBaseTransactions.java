package com.sergio.main.model.repository.database;


import javax.persistence.*;

/**
 * Clase que se encarga de las transacciones con la base de datos.
 */
public class DataBaseTransactions {

    private static DataBaseTransactions instance;

    private final EntityManagerFactory EMF;
    public final EntityManager EM;

    private DataBaseTransactions(){

        EMF = Persistence.createEntityManagerFactory("ProyectoInterfaces");
        EM= EMF.createEntityManager();

    }

    public static DataBaseTransactions getInstance(){

        if(instance == null){

            instance = new DataBaseTransactions();

        }

        return instance;

    }

    public Query createQuery(String q){

        return EM.createQuery(q);

    }

    public void rollback(){

        EM.getTransaction().rollback();

    }

    public void persist(Object object){

        EM.getTransaction().begin();
        EM.persist(object);
        EM.getTransaction().commit();

    }

    public void close(){

        EMF.close();
        EM.close();

    }



}
