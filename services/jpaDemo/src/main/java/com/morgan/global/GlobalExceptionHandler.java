package com.morgan.global;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: GlobalExceptionHandler
 * Package: com.morgan.global
 *
 * @author Morgan Wei
 * @create 2026-11-01-21:00
 * @Description: 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
     * 处理Token授权异常
     */
    @ExceptionHandler(TokenAuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String,Object> handleTokenException(TokenAuthException e){

        Map<String,Object> result = new HashMap<>();
        result.put("code",401);
        result.put("msg",e.getMessage());
        return result;
    }

    /**
     * 全局异常处理，用来兜底鉴权失败以外的异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,Object> handleException(Exception e){
        Map<String,Object> result = new HashMap<>();
        result.put("code",500);
        result.put("msg",e.getMessage());
        return result;
    }

}
