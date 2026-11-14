package com.morgan.exception;

/**
 * ClassName: BizException
 * Package: com.morgan.exception
 *
 * @author Morgan Wei
 * @create 2026-11-15-6:37
 * @Description: 自定义业务异常
 */
public class BizException extends RuntimeException{
    private String code;

    public String getCode() {
        return code;
    }
    public BizException(String code, String msg){
        super(msg);
        this.code = code;
    }
}
