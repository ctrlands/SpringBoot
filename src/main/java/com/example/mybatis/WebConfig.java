package com.example.mybatis;

import com.example.mybatis.controller.SessionInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Created by LJ on 2018/7/26.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {
   /* public WebConfig(){
        super();
    }*/
   private ApplicationContext applicationContext;
    //注册登陆拦截器
    /*@Autowired
    private SessionInterceptor sessionInterceptor;*/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("拦截开始");
        //拦截规则：除了login，其他都拦截判断
        //registry.addInterceptor( sessionInterceptor()).addPathPatterns("/**").excludePathPatterns("/","/register","/index");
        registry.addInterceptor( sessionInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    //关键，将拦截器作为bean写入配置中
    @Bean
    public SessionInterceptor sessionInterceptor(){
        return new SessionInterceptor();
    }
}
