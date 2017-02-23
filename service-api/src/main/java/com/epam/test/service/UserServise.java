package com.epam.test.dao;

import java.util.List;
import com.epam.test.dao.User;

/**
 * Created by andrei on 20.2.17.
 */
public interface UserServise {
    List<User> getAllUsers();

    User getUserById(Integer userId);

    Integer addUser(User user);

    void updateUser(User user);

    void deleteUser(Integer userId);
}
