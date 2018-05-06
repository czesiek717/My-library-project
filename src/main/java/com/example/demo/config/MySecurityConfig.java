package com.example.demo.config;

import com.example.demo.service.MyDbAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter
{
    private MyDbAuthenticationService myDbAuthenticationService;
    /*private DataSource dataSource;*/

    @Autowired
    public MySecurityConfig(MyDbAuthenticationService myDbAuthenticationService/*, DataSource dataSource*/)
    {
        this.myDbAuthenticationService = myDbAuthenticationService;
        /*this.dataSource = dataSource;*/
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure (AuthenticationManagerBuilder auth) throws Exception
    {
        /*auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder());*/
        auth.userDetailsService(myDbAuthenticationService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure (HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/?").permitAll()
                .antMatchers("/search").permitAll()
                .antMatchers("/book").permitAll()
                .antMatchers("/author").permitAll()
                .antMatchers("/user/register").permitAll()
                .antMatchers("/user/admin").hasRole("ADMIN")
                .antMatchers("/user/admin/**").hasRole("ADMIN")
                .antMatchers("/css/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/resources/**").permitAll()
                .anyRequest().authenticated()
                .and()
                    .exceptionHandling()
                        .accessDeniedPage("/403")
                .and()
                    .formLogin().loginPage("/user/login")
                    .defaultSuccessUrl("/")
                    .loginProcessingUrl("/user/login")
                    .permitAll()
                .and()
                    .logout().logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                    .clearAuthentication(true)
                    .logoutSuccessUrl("/").deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true);
    }
}
