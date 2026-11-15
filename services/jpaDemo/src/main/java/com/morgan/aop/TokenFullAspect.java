package com.morgan.aop;

import com.morgan.annotation.RequireToken;
import com.morgan.global.TokenAuthException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

/**
 * ClassName: TokenFullAspect
 * Package: com.morgan.aop
 *
 * @author Morgan Wei
 * @create 2026-11-15-22:19
 * @Description:
 */
@Component
@Aspect
@Slf4j
public class TokenFullAspect {

    // 开关，和你原来代码保持一致
    private final boolean tokenCheck = true;
    private static final String VALID_TOKEN = "validToken-2026";

    /*
    ===================== 切点定义：复用 @annotation 拦截@RequireToken =====================
    @annotation 切点指示器，requireToken用来注入注解实例
    @Pointcut("@annotation(requireToken)")
    execution(* com.morgan.controller..*.*(..))
            - 第一个`*`：任意返回值
            - `com.morgan.controller..`：`..` 代表当前包 + 所有子包
            - 第二个`*`：任意类
            - 第三个`*`：任意方法名
            - `(..)`：任意参数
     */
    @Pointcut("execution(* com.morgan.controller..*.*(..)) && @annotation(requireToken)")
    public void tokenPointcut(RequireToken requireToken) {

    }

    // ===================== 1. @Around 环绕通知（核心，你原来的token校验逻辑） =====================
    //环绕通知引用切点
    @Around("tokenPointcut(requireToken)")
    public Object tokenAround(ProceedingJoinPoint joinPoint, RequireToken requireToken) throws Throwable {
        log.info("===== 【@Around 环绕通知开始】 =====");
        log.info("注解属性 needAdmin = {}", requireToken.needAdmin());
        log.info("目标方法名：{}", joinPoint.getSignature().getName());
        log.info("方法入参：{}", Arrays.toString(joinPoint.getArgs()));

        if (!tokenCheck) {
            log.info("token校验开关关闭，直接放行");
            Object result = joinPoint.proceed();
            log.info("===== 【@Around 环绕通知结束】 =====");
            return result;
        }

        // 获取请求头校验token（原逻辑）
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ") || !VALID_TOKEN.equals(authHeader.substring(7))) {
            log.error("token校验失败，header Authorization:{}", authHeader);
            throw new TokenAuthException("token校验失败");
        }

        log.info("token校验通过，准备执行目标方法");
        //执行被调用的方法
        Object result = joinPoint.proceed();
        log.info("@Around拿到目标方法返回值：{}", result);
        log.info("===== 【@Around 环绕通知结束】 =====");
        return result;
    }

    // ===================== 2. @Before 前置通知（目标方法执行之前） =====================
    @Before("tokenPointcut(requireToken)")
    public void before(JoinPoint joinPoint, RequireToken requireToken) {
        log.info("【@Before 前置通知】方法执行前，needAdmin：{}", requireToken.needAdmin());
    }

    // ===================== 3. @AfterReturning 正常返回通知（无异常才执行） =====================
    // returning = "res" 用来接收目标方法返回值
    @AfterReturning(value = "tokenPointcut(requireToken)", returning = "res")
    public void afterReturning(JoinPoint joinPoint, RequireToken requireToken, Object res) {
        log.info("【@AfterReturning 正常返回通知】方法正常结束，返回结果：{}", res);
    }

    // ===================== 4. @AfterThrowing 异常通知（抛出异常才执行） =====================
    // throwing = "ex" 用来捕获异常对象
    @AfterThrowing(value = "tokenPointcut(requireToken)", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, RequireToken requireToken, Throwable ex) {
        log.error("【@AfterThrowing 异常通知】方法抛出异常：{}", ex.getMessage(), ex);
    }

    // ===================== 5. @After 后置通知（无论成功/异常，一定会执行，类似finally） =====================
    @After("tokenPointcut(requireToken)")
    public void after(JoinPoint joinPoint, RequireToken requireToken) {
        log.info("【@After 后置通知】finally 阶段，一定执行");
    }
}
