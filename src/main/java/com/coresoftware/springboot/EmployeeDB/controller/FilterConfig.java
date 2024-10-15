package com.coresoftware.springboot.EmployeeDB.controller;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<DebugEndpointFilter> loggingFilter(){
        FilterRegistrationBean<DebugEndpointFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new DebugEndpointFilter());
        registrationBean.addUrlPatterns("/debug");  // Filter applies only to /debug

        return registrationBean;
    }
}
