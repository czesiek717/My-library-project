package com.example.demo.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.example.demo")
public class AppConfig
{
    @Bean
    public SessionFactory sessionFactory(DataSource dataSource)
    {
        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource);
        builder.scanPackages("com.example.demo.entity");

        return builder.buildSessionFactory();
    }

    @Bean
    public HibernateTransactionManager txManager(SessionFactory sessionFactory)
    {
        return new HibernateTransactionManager(sessionFactory);
    }

    @Bean
    public SpringSecurityDialect springSecurityDialect()
    {
        return new SpringSecurityDialect();
    }
}
