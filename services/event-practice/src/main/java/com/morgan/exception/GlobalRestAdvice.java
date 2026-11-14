package com.morgan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: GlobalRestAdvice
 * Package: com.morgan.exception
 *
 * @author Morgan Wei
 * @create 2026-11-14-16:34
 * @Description:
 */
@RestControllerAdvice(basePackages="com.morgan.controller")
public class GlobalRestAdvice {
    /**
     * 捕获 RuntimeException（你的demo抛出模拟异常就是RuntimeException）
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String,Object>> handleRuntimeException(RuntimeException e){
        Map<String,Object> body = new HashMap<>();
        body.put("code",500);
        body.put("msg","业务异常:"+e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 捕获参数非法异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,Object>> handleIllegalArgumentException(IllegalArgumentException e){

        Map<String,Object> body = new HashMap<>();
        body.put("code", 400);
        body.put("msg", "参数错误"+e.getMessage());
        return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST);
    }

    /**
     * 兜底，捕获所有Exception
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleException(Exception e){
        Map<String,Object> body = new HashMap<>();
        body.put("code", 500);
        body.put("msg", "系统未知异常，请联系管理员");
        // 生产环境打印完整异常堆栈日志 log.error("未知异常",e);
        System.out.println("全局异常:"+e);
        return new ResponseEntity<>(body,HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(BizException.class)
    public ResponseEntity<Map<String,Object>> handleBizException(String code,BizException e){
        Map<String, Object> body = new HashMap<>();
        body.put("code",code);
        body.put("msg",e.getMessage());
        return new ResponseEntity<>(body,HttpStatus.OK);
    }
















}
