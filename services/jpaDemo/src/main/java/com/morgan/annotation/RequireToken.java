package com.morgan.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName: RequireToken
 * Package: com.morgan.annotation
 *
 * @author Morgan Wei
 * @create 2026-11-01-20:22
 * @Description:
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireToken {

    //增加属性，演示切面读取注解属性
    boolean needAdmin() default false;
}
