package com.sergio.main.model.repository.database.dao;

import com.sergio.main.model.datasource.user.User;
import com.sergio.main.model.repository.database.DataBaseTransactions;

import javax.persistence.Query;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private final DataBaseTransactions DB_TRANSACTIONS;

    public UserDAOImpl(){

        DB_TRANSACTIONS = DataBaseTransactions.getInstance();

    }

    @Override
    public User getUserByUsername(String username) {

        String q = "SELECT * FROM users WHERE name = :username";
        Query query = DB_TRANSACTIONS.createQuery(q);
        query.setParameter(1,username);
        List<User> users = query.getResultList();
        User user = users.isEmpty() ? users.get(0) : null;

        return user;

    }

    @Override
    public boolean registerUser(User user) {

        boolean transactionCompleted = false;

        try {

            DB_TRANSACTIONS.persist(user);
            transactionCompleted = true;

        }catch (Exception e){

            DB_TRANSACTIONS.rollback();

        }

        return transactionCompleted;

    }

    @Override
    public boolean updateUser(User user) {
        return registerUser(user);
    }

    @Override
    public boolean checkUsernameRegistered(String username) {

        String q = "SELECT id FROM users WHERE name = :name";
        Query query = DB_TRANSACTIONS.createQuery(q);
        query.setParameter(1,username);

        return query.getResultList().isEmpty();

    }

    @Override
    public boolean checkEmailRegistered(String email) {

        String q = "SELECT id FROM users WHERE email = :email";
        Query query = DB_TRANSACTIONS.createQuery(q);
        query.setParameter(1, email);

        return query.getResultList().isEmpty();

    }

}
