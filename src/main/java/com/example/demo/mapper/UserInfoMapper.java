package com.example.demo.mapper;

import com.example.demo.entity.UserInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserInfoMapper implements RowMapper<UserInfo>
{
    @Override
    public UserInfo mapRow (ResultSet resultSet, int i) throws SQLException
    {
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");
        return new UserInfo(username, password);
    }
}
