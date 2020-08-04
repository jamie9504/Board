package com.github.jamie9504.board.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("admin")
            .password(passwordEncoder().encode("admin"))
            .roles("ADMIN")
            .authorities("ACCESS1", "ACCESS2")
            .and()
            .withUser("manager")
            .password(passwordEncoder().encode("manager"))
            .roles("MANAGER")
            .authorities("ACCESS1")
            .and()
            .withUser("user")
            .password(passwordEncoder().encode("user"))
            .roles("USER");
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/script/**", "image/**", "/fonts/**", "lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/index.html").permitAll()
            .antMatchers("/profile/**").authenticated()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
            .antMatchers("/api/public/test1").hasAuthority("ACCESS1")
            .antMatchers("/api/public/test2").hasAuthority("ACCESS2")
            .and()
            .httpBasic();
    }
}
