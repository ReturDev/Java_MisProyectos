package com.sergio.main.model.repository.database.dao;

import com.sergio.main.model.datasource.user.User;

public interface UserDAO {

    User getUserByUsername(String username);

    boolean registerUser(User user);

    boolean updateUser(User user);

    boolean checkUsernameRegistered(String username);

    boolean checkEmailRegistered(String email);

    boolean checkUserRegistered(String username, String password);

}
