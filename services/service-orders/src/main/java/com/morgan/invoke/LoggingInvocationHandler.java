package com.morgan.invoke;

import lombok.val;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author morganwei
 * @date 05/06/2025 17:53
 * @description TODO
 */
public class LoggingInvocationHandler implements InvocationHandler {


    private final Object target;

    public LoggingInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodNm = method.getName();

        System.out.println(getCurrTime("")+ " begin to execute " + methodNm +"...");
        Object result = method.invoke(target, args);
        System.out.println(getCurrTime("")+ " end execute " + methodNm +"...");

        return result;
    }

    public static void main(String[] args) {
        HelloService realService = new HelloServiceImpl();

        HelloService proxyService = (HelloService) Proxy.newProxyInstance(
                HelloService.class.getClassLoader(),
                new Class[]{HelloService.class},
                new LoggingInvocationHandler(realService)
        );

        proxyService.sayHello("Morgan");
    }

    /**
     *
     * @return HH:mm:ss.SSS in default
     */
    public static String getCurrTime(String timeFormat){
        LocalTime now = LocalTime.now();
        if(StringUtils.hasLength(timeFormat)){
            return now.format(DateTimeFormatter.ofPattern(timeFormat));
        }else{
            return now.format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
        }
    }
}
