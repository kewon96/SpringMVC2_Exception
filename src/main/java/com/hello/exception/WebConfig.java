package com.hello.exception;

import com.hello.exception.filter.LogFilter;
import com.hello.exception.interceptor.LogInterceptor;
import com.hello.exception.resolver.MyHandlerExceptionResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.DispatcherType;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<LogFilter> logFilterRegistrationBean = new FilterRegistrationBean<>(new LogFilter());
        logFilterRegistrationBean.setOrder(1);
        logFilterRegistrationBean.addUrlPatterns("/*");
        logFilterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR);

        return logFilterRegistrationBean;
    }

    /**
     * Interceptor는 Filter와 다르게 DispatcherType을 가지고 제약을 걸 수 없다.
     * 대신에 그러한 화면의 Path를 이식해주는 방식을 채용한다.
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "*.ico", "/error", "/error-page/**") // 오류 페이지 경로
        ;
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(new MyHandlerExceptionResolver());
    }
}
