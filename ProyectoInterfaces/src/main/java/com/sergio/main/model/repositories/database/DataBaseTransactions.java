package com.sergio.main.model.repositories.database;

import com.sergio.main.model.datasource.user.User;

import javax.persistence.*;

public class DataBaseTransactions {

    private static DataBaseTransactions instance;

    private final EntityManagerFactory EMF;
    private final EntityManager EM;

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

    public void makeTransactionVisualWork(Query query){

        EM.getTransaction().begin();
        query.executeUpdate();
        EM.getTransaction().commit();

    }

    public void rollback(){

        EM.getTransaction().rollback();

    }

    public boolean registerUser(User user){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoInterfaces");
        EntityManager em = emf.createEntityManager();
        boolean transactionCompleted = false;

        try {

            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            transactionCompleted = true;

        }catch (Exception ex){

            em.getTransaction().rollback();

        }finally {

            emf.close();
            em.close();

        }

        return transactionCompleted;

    }



}
