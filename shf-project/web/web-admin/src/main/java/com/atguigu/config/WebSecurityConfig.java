package com.atguigu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author lystart
 * @create 2023-05-10 14:13
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    protected PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin();
        http.authorizeRequests().antMatchers("/static/**","/login").permitAll().anyRequest().authenticated();
        // 登入成功
        http.formLogin().loginPage("/login").defaultSuccessUrl("/");
        // 登入失败
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login");
        // 关闭防火墙
        http.csrf().disable();
        //添加自定义异常入口
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeineHandler());
    }
}

