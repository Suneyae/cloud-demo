package com.morgan.listener;

import com.morgan.event.UserRegisterEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * ClassName: UserRegisterListener
 * Package: com.morgan.listener
 *
 * @author Morgan Wei
 * @create 2026-11-13-17:29
 * @Description:
 */
@Component
public class UserRegisterListener implements ApplicationListener<UserRegisterEvent> {
    @Override
    public void onApplicationEvent(UserRegisterEvent event) {
        //
        System.out.println("监听到用户 " +event.getUserName() +" 注册 ");

    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
