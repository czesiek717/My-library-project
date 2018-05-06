package com.example.demo.config;

import com.example.demo.DemoApplication;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MySpringMvcDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{

    @Override
    protected String[] getServletMappings()
    {
        return new String[] { "/" };
    }

    @Override
    protected Class<?>[] getServletConfigClasses()
    {
        return new Class[] { DemoApplication.class };
    }

    @Override
    protected Class<?>[] getRootConfigClasses()
    {
        return null;
    }
}
