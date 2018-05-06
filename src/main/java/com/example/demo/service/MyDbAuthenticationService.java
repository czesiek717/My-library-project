package com.example.demo.service;

import com.example.demo.dao.UserInfoDAO;
import com.example.demo.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyDbAuthenticationService implements UserDetailsService
{
    @Autowired
    private UserInfoDAO userInfoDAO;

    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException
    {
        UserInfo userInfo = userInfoDAO.findUserInfo(username);
        if(userInfo == null)
        {
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
        List<String> roles = userInfoDAO.getUserRole(username);
        List<GrantedAuthority> grantList = new ArrayList<>();
        if(roles != null)
        {
            for(String role : roles)
            {
                GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
                grantList.add(authority);
            }
        }
        return new User(userInfo.getUsername(), userInfo.getPassword(), grantList);
    }
}
