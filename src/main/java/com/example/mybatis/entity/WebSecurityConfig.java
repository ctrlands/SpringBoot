package com.example.mybatis.entity;

import com.example.mybatis.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * Created by LJ on 2018/8/22.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    /*@Autowired
    private DataSource dataSource;*/

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.

    }*/
    /*@Bean
    public PersistentTokenRepository persistentTokenRepository() {
        jdbcTokenRepositoryImpl tokenRepository = new jdbcTokenRepositoryImpl();

    }*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
           .authorizeRequests()
                // 任何尚未匹配的url只需要验证用户即可访问
                .anyRequest()
                .authenticated()
                .and()
           // 设置登录页
           .formLogin()
                // 设置登录页
                .loginPage("/")
                // 设置登陆成功页
                .defaultSuccessUrl("/register")
                .permitAll()
                // 自定义登陆用户名和密码参数
                // .usernameParameter("")
                // .passwordParameter("")
                .and()
           .logout()
                .permitAll()
                .and()
           // 自动登录
           .rememberMe()
                    // token有效时间(单位：s)
                    .tokenValiditySeconds(60);
        // 关闭csrf跨域
        http.csrf().disable();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置拦截忽略文件，可以对静态资源放行
        web.ignoring().antMatchers("/css/**","/js/**","/style/**","/fonts/**");

    }
}
