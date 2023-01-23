package com.hello.exception;

import com.hello.exception.filter.LogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.DispatcherType;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<LogFilter> logFilterRegistrationBean = new FilterRegistrationBean<>(new LogFilter());
        logFilterRegistrationBean.setOrder(1);
        logFilterRegistrationBean.addUrlPatterns("/*");
        logFilterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR);

        return logFilterRegistrationBean;
    }

}
