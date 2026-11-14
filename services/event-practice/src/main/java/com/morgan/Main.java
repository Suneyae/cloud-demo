package com.morgan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * ClassName: ${NAME}
 * Package: com.morgan
 *
 * @author Morgan Wei
 * @create 2026-11-13-16:42
 * @Description:
 */
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
//        SpringApplication.run(Main.class,args);
        SpringApplication app = new SpringApplication(Main.class);
        app.addListeners(new ApplicationListener<ApplicationEvent>() {
            @Override
            public void onApplicationEvent(ApplicationEvent event) {
                System.out.println("【收到Spring事件】" + event.getClass().getSimpleName());
            }
        });
        //打印bean的个数
        ConfigurableApplicationContext ctx = app.run(args);
        System.out.println("===getBeanDefinitionCount:"+ctx.getBeanDefinitionCount());
    }
}