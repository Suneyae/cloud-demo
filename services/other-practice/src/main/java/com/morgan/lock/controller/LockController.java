package com.morgan.lock.controller;

import com.morgan.lock.service.LockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author morganwei
 * @date 26/06/2025 10:27
 * @description TODO
 */
@RestController
@RequestMapping("/lock")
public class LockController {
    @Autowired
    private LockService lockService;

    @GetMapping("/test")
    public String testLock(){
        return lockService.doBusinessWithLock();
    }
}
