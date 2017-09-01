package com.ppteam.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                .usersByUsernameQuery("select a.username,b.password,true from user as a,user_security as b "+
                        " where b.user_id=(select id from user where username=? ) and a.id=b.user_id")
                .authoritiesByUsernameQuery("select username,role from user where username=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.PUT,"/api/user_info").authenticated()
                .antMatchers("/api/user_security").authenticated()
                .antMatchers("/space.html").authenticated()
                .antMatchers("/security.html").authenticated()
                .antMatchers(HttpMethod.POST,"/image/avatar").authenticated()
                .antMatchers("/uploadavatar.html").authenticated()
                .anyRequest().permitAll()
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
