package com.morgan.service;

import com.morgan.event.UserRegisterEvent;
import com.morgan.eventListenerDemo.event.VipUserRegisterEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * ClassName: UserRegisterService
 * Package: com.morgan.service
 *
 * @author Morgan Wei
 * @create 2026-11-13-17:32
 * @Description:
 */
@Service

public class UserRegisterService {
    //引入ApplicationEventPublisher
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;


    public void register(String userName){
        System.out.println("UserRegisterService started, user is" + userName);
        applicationEventPublisher.publishEvent(new UserRegisterEvent(this,userName));
    }


    public void registerVip(String userName,boolean vip){
        System.out.println("保存用户simple：" + userName);
        applicationEventPublisher.publishEvent(new VipUserRegisterEvent(userName,vip));
    }

}
