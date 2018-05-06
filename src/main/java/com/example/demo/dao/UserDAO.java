package com.example.demo.dao;

import com.example.demo.entity.User;

import java.util.List;

public interface UserDAO
{
    void saveUser(User user);

    User getUser(int id);

    User getUser(String username);

    User getUserWithBookshelf(int id);

    User getUserWithReviews(int id);

    User getUserWithEverything(int id);

    User getUserWithEverything(String username);

    boolean registerUser(User user, String role);

    List<User> getAllUsers();

    void deleteUser(User user);

    void deleteUser(int id);
}
