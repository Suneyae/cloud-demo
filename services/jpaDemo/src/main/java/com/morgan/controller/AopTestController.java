package com.morgan.controller;

import com.morgan.annotation.RequireToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: AopTestController
 * Package: com.morgan.controller
 *
 * @author Morgan Wei
 * @create 2026-11-15-22:41
 * @Description:
 */
@RestController
@RequestMapping("/aop")
public class AopTestController {

    // 普通校验接口，needAdmin=false
    @GetMapping("/hello")
    @RequireToken
    public String hello() {
        System.out.println(">>>>> 目标方法 hello() 执行了");
        return "hello success";
    }

    // 需要管理员权限
    @GetMapping("/admin")
    @RequireToken(needAdmin = true)
    public String admin() {
        System.out.println(">>>>> 目标方法 admin() 执行了");
        return "admin success";
    }

    // 用来测试异常分支
    @GetMapping("/error")
    @RequireToken
    public String error() {
        System.out.println(">>>>> 目标方法 error() 执行了");
        throw new RuntimeException("接口手动抛异常");
    }
}
