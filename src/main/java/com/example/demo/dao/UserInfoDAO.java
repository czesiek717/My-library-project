package com.example.demo.dao;

import com.example.demo.entity.UserInfo;

import java.util.List;

public interface UserInfoDAO
{
    UserInfo findUserInfo(String username);

    List<String> getUserRole(String username);
}
