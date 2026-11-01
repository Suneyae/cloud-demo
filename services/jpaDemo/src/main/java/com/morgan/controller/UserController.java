package com.morgan.controller;

import com.morgan.annotation.RequireToken;
import com.morgan.dto.UserQueryDTO;
import com.morgan.entity.User;
import com.morgan.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: UserController
 * Package: com.morgan.controller
 *
 * @author Morgan Wei
 * @create 2026-10-31-23:29
 * @Description:
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/page")
    @RequireToken
    public Page<User> queryPage(@RequestBody UserQueryDTO dto) {
        return userService.pageQuery(dto);
    }

    @GetMapping("/init")
    public String init() {
        userService.initData();
        return "初始化成功！";
    }
}