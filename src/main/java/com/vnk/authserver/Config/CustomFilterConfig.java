package com.vnk.authserver.Config;

import com.vnk.authserver.Filter.RolesPermissionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomFilterConfig {

    @Bean
    public FilterRegistrationBean<RolesPermissionFilter> rolesFilterRegistrationBean(){
        FilterRegistrationBean<RolesPermissionFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new RolesPermissionFilter());
        registrationBean.addUrlPatterns("/permission/**", "/roles/**");
        return registrationBean;
    }

}