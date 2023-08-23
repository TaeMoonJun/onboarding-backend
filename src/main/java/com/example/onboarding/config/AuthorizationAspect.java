package com.example.onboarding.config;

import com.example.onboarding.exception.AuthorizationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.regex.Pattern;

@Aspect
@Component
public class AuthorizationAspect {

    @Value("${jwt.secret}") String secretKey;

    @Before("execution(public * com.example.onboarding.controllers..*Controller.*(..)) " +
            "&& !@annotation(com.example.onboarding.config.NoAuth)")
    public void insertAdminLog(JoinPoint joinPoint) throws AuthorizationException, UnsupportedEncodingException {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes("UTF-8"));

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String authorization = request.getHeader("Authorization");
        if(StringUtils.hasText(authorization)){
            throw new AuthorizationException("Authorization Header Not Found");
        }
        if(Pattern.matches("^Bearer .*", authorization)) {
            authorization = authorization.replaceAll("^Bearer( )*", "");
            Jws<Claims> jwsClaims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(authorization);

            if(jwsClaims.getBody() != null) {
                Claims claims = jwsClaims.getBody();
                long exp = claims.getExpiration().getTime();
                if(exp < new Date().getTime()) {
                    throw new AuthorizationException("Token Expired");
                }
            }
        } else {
            throw new  AuthorizationException("Invalid Token");
        }
    }
}