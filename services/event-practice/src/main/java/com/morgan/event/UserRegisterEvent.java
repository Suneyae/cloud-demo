package com.morgan.event;

import org.springframework.context.ApplicationEvent;

/**
 * ClassName: UserRegisterEvent
 * Package: com.morgan.event
 *
 * @author Morgan Wei
 * @create 2026-11-13-16:44
 * @Description: refer https://www.yuque.com/woyouxiaomianaola/bp5ofg/xbhagbpvfqxh76as#JIZpg
 */
//传统方式，集成ApplicationEvent
public class UserRegisterEvent extends ApplicationEvent {
    private String userName;

//    public UserRegisterEvent(Object source) {
//        super(source);
//    }

    public UserRegisterEvent(Object source, String userName){
        super(source);
        this.userName = userName;
    }

    public String getUserName(){
        return userName;
    }
}
