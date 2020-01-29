package com.restaurant.restaurant.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Антон on 09.01.2020.
 */
public class JwtTokenFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        System.out.println(((HttpServletRequest)servletRequest).getRequestURI());
        String token = jwtTokenProvider.resolveToken((HttpServletRequest)servletRequest,"Authorization");
        try {
            if(token != null && jwtTokenProvider.validateToken(token)){
                Authentication authentication = jwtTokenProvider.getAuth(token);

                if(authentication != null){
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            }
        } catch (JwtAuthenticationException e) {
            e.printStackTrace();
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
