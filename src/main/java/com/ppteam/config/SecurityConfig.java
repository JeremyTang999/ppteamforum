package com.ppteam.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*auth.inMemoryAuthentication()
                .withUser("admin").password("abc").roles("role");*/
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username,password,true from user,user_security where username=?")
                .authoritiesByUsernameQuery("select username,role from user where username=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .and()
                    .formLogin()
                    .loginPage("/login.html")
                    .loginProcessingUrl("/p_login")
                    .defaultSuccessUrl("/")
                    .failureUrl("/failure.html")
                    .usernameParameter("p_username")
                    .passwordParameter("p_password")
                .and()
                    .logout()
                    .logoutUrl("/p_logout")
                    .logoutSuccessUrl("/")
                .and()
                .   csrf().disable();

    }
}
