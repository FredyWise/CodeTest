package com.fredywise.freshfruitapi;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<MyHaderCheckerFilter> headerCheckFilter() {
        FilterRegistrationBean<MyHaderCheckerFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MyHaderCheckerFilter());
        registrationBean.addUrlPatterns("/api/*"); 
        return registrationBean;
    }
}
