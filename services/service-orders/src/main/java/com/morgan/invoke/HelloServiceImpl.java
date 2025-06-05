package com.morgan.invoke;

/**
 * @author morganwei
 * @date 05/06/2025 17:51
 * @description TODO
 */
public class HelloServiceImpl implements HelloService{
    @Override
    public void sayHello(String name) {
        System.out.println("Hello:"+name);
    }
}
