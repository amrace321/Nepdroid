package com.nepdroid.pro1.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomLoginSuccessHandler customLoginSuccessHandler;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
   /*     auth.inMemoryAuthentication().withUser("admin")
                .password("{noop}admin").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("freddy")
                .password("{noop}freddy").roles("SELLER");
        auth.inMemoryAuthentication().withUser("bob")
                .password("{noop}bob").roles("USER");*/


//enabled from admin if users shows false bihaviour then amin can disbale the user
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select email,password,enabled from user where email=?")
                .authoritiesByUsernameQuery("select role as role1,role as role2 from user where email=?")
                .dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/seller/**").access("hasRole('ROLE_SELLER')")
                .antMatchers("/register/**","/api/mobile/**").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/vendor/**").permitAll()
                .antMatchers("/css/**").permitAll()

                .antMatchers("/user/**").access("hasRole('ROLE_USER')")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll()
                .successHandler(customLoginSuccessHandler);

    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //for restful api configuration
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/mobile**");
    }
    }



