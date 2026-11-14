package com.morgan.eventListenerDemo.listener;

import com.morgan.eventListenerDemo.event.VipUserRegisterEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * ClassName: VipListener
 * Package: com.morgan.eventListenerDemo.listener
 *
 * @author Morgan Wei
 * @create 2026-11-14-9:46
 * @Description: refer day2 contents of https://www.yuque.com/woyouxiaomianaola/bp5ofg/xbhagbpvfqxh76as#Fjxrs
 */
@Component
public class VipListener {

    @Order(1)
    @EventListener
    public void listenAll(VipUserRegisterEvent vipEvent){
        System.out.println("监听到 user "+vipEvent.getUserName()+", vip ind:"+vipEvent.isVip());
    }

    @Order(2)
    @EventListener(condition = "#vipEvent.vip")
    public void ListenVip(VipUserRegisterEvent vipEvent){
        System.out.println("[@EventListener 只监听VIP] " + vipEvent.getUserName());
    }
}
