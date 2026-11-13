package com.morgan.controller;

import com.morgan.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: UserRegisterController
 * Package: com.morgan.controller
 *
 * @author Morgan Wei
 * @create 2026-11-13-17:38
 * @Description:
 */
@RestController
public class UserRegisterController {
    @Autowired
    private UserRegisterService registerService;

    @GetMapping("/register")
    public String register(@RequestParam String userName){
        registerService.register(userName);
        return "ok";

    }
}
