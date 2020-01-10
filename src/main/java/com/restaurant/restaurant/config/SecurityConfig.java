package com.restaurant.restaurant.config;

import com.restaurant.restaurant.security.jwt.JwtConfig;
import com.restaurant.restaurant.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Created by Антон on 09.01.2020.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtTokenProvider jwtTokenProvider;

    private static final String ADMIN_ENDPOINT = "/api/v1/admin/**";
    private static final String LOGIN_ENDPOINT = "/resto/V1/auth/login";
    private static final String REFRESH_ENDPOINT = "/resto/V1/auth/refresh";
    private static final String REGISTER_ENDPOINT = "/resto/V1/auth/register";

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT,REFRESH_ENDPOINT,REGISTER_ENDPOINT).permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasRole(com.restaurant.restaurant.entity.Role.ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfig(jwtTokenProvider));
    }
}
