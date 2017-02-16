package com.epam.test.dao;

import com.epam.test.dao.User;

import java.util.List;

/**
 * Created by andrei on 16.2.17.
 */

public interface UserDao {

    List<User> getAllUsers();

    User getUserById(Integer userId);

    Integer addUser(User user);

    void updateUser(User user);

    void deleteUser(Integer userId);

}


