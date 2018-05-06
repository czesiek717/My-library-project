package com.example.demo.service;

import com.example.demo.dao.UserDAO;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    UserDAO userDAO;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void saveUser (User user)
    {
        userDAO.saveUser(user);
    }

    @Override
    @Transactional
    public User getUser (int id)
    {
        return userDAO.getUser(id);
    }

    @Override
    @Transactional
    public User getUser (String username)
    {
        return userDAO.getUser(username);
    }

    @Override
    @Transactional
    public User getUserWithBookshelf (int id)
    {
        return userDAO.getUserWithBookshelf(id);
    }

    @Override
    @Transactional
    public User getUserWithReviews (int id)
    {
        return userDAO.getUserWithReviews(id);
    }

    @Override
    @Transactional
    public User getUserWithEverything (int id)
    {
        return userDAO.getUserWithEverything(id);
    }

    @Override
    @Transactional
    public User getUserWithEverything (String username)
    {
        return userDAO.getUserWithEverything(username);
    }

    @Override
    @Transactional
    public boolean registerUser (User user, String role)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDAO.registerUser(user, role);
    }

    @Override
    @Transactional
    public List<User> getUsersFrom ()
    {
        return userDAO.getAllUsers();
    }

    @Override
    @Transactional
    public void deleteUser (User user)
    {
        userDAO.deleteUser(user);
    }

    @Override
    @Transactional
    public void deleteUser (int id)
    {
        userDAO.deleteUser(id);
    }
}
