package com.morgan.aop;

import com.morgan.annotation.RequireToken;


import com.morgan.global.TokenAuthException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * ClassName: TokenAspect
 * Package: com.morgan.aop
 *
 * @author Morgan Wei
 * @create 2026-11-01-20:23
 * @Description:
 */

@Component
@Aspect
public class TokenAspect {
    private static final String VALID_TOKEN = "validToken-2026";

    /*
     *  对标注了RequireToken注解的接口方法进行Token鉴权拦截
     */
    @Value("${app.token-check:true}")
    private boolean tokenCheck;
    @Around("@annotation(requireToken)")
    public Object checkToken(ProceedingJoinPoint joinPoint, RequireToken requireToken) throws Throwable {
        System.out.println("TokenAspect checkToken");
        if(!tokenCheck){
            return joinPoint.proceed();
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String auth = attributes.getRequest().getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ") || !VALID_TOKEN.equals(auth.substring(7))) {
            throw new TokenAuthException("token校验失败");
        }
        return joinPoint.proceed();

    }
}
