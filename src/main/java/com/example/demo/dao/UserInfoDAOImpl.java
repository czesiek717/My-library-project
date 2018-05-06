package com.example.demo.dao;

import com.example.demo.entity.UserInfo;
import com.example.demo.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@Service
@Transactional
public class UserInfoDAOImpl extends JdbcDaoSupport implements UserInfoDAO
{
    @Autowired
    public UserInfoDAOImpl(@Qualifier("dataSource") DataSource dataSource)
    {
        this.setDataSource(dataSource);
    }

    @Override
    public UserInfo findUserInfo (String username)
    {
        String sql = "Select u.username, u.password from user u where u.username = ?";
        Object[] params = new Object[] {username};
        UserInfoMapper mapper = new UserInfoMapper();
        try
        {
            return this.getJdbcTemplate().queryForObject(sql, params, mapper);
        }
        catch(EmptyResultDataAccessException e)
        {
            return null;
        }
    }

    @Override
    public List<String> getUserRole (String username)
    {
        String sql = "Select u.role from user u where u.username = ?";
        Object[] params = new Object[] { username };
        return this.getJdbcTemplate().queryForList(sql, params, String.class);
    }
}
