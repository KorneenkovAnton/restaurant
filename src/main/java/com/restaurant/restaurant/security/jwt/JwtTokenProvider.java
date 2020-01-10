package com.restaurant.restaurant.security.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

/**
 * Created by Антон on 09.01.2020.
 */
@Component
public class JwtTokenProvider {
    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.access.expired}")
    private long validityInMSAccess;

    @Value("${jwt.token.refresh.expired}")
    private long validityInMSRefresh;

    @Autowired
    private UserDetailsService jwtUserDetailService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    protected void init(){
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createAccessToken(String username, String password){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("Password",password);

        Date dateNow = new Date();
        Date validity = new Date(dateNow.getTime() + validityInMSAccess);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(dateNow)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    public String createRefreshToken(String username){
        Claims claims = Jwts.claims().setSubject(username);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMSRefresh);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    public Authentication getAuth(String token){
        UserDetails userDetails = this.jwtUserDetailService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    public String getUsername(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request,String headerName){
        String token = request.getHeader(headerName);
        if(token != null && token.startsWith("Token_")){
            return token.substring(6,token.length());
        }
        return  null;
    }

    public boolean validateToken(String token) throws JwtAuthenticationException {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

            if(claimsJws.getBody().getExpiration().before(new Date())){
                return false;
            }
            return true;
        }catch (JwtException | IllegalArgumentException e){
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }
}
