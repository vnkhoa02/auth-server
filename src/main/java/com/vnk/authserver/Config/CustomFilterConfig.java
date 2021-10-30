package com.vnk.authserver.Config;

import com.vnk.authserver.Filter.CustomFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomFilterConfig{

    @Bean
    public FilterRegistrationBean<CustomFilter> registrationBean(){
        FilterRegistrationBean<CustomFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new CustomFilter());
        registrationBean.addUrlPatterns("/permission/**", "/roles/**");
        return registrationBean;
    }
}