package com.morgan.listener;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * ClassName: BeanDumpListener
 * Package: com.morgan.listener
 *
 * @author Morgan Wei
 * @create 2026-11-14-15:55
 * @Description:
 */
@Component
public class BeanDumpListener {

    @EventListener(ApplicationReadyEvent.class)
    public void dumpBeans(ApplicationReadyEvent event) {
        //拿到spring上下文
        var ctx = event.getApplicationContext();

        //1.打印所有bean名称
        String[] beanNames = ctx.getBeanDefinitionNames();
        System.out.println("\n===========所有Bean名称列表===========");
        for (int i = 0; i<beanNames.length; i++) {
            System.out.println("bean" + i+ " :"+beanNames[i]);
        }

        //2.直接按类型查找 PlatformTransactionManager
        System.out.println("\n========检查PlatformTransactionManager Bean========");
        boolean exists = ctx.containsBeanDefinition("platformTransactionManager");
        System.out.println("containsBeanDefinition(\"platformTransactionManager\") = " + exists);

        try {
            // 根据类型获取，如果不存在会抛NoSuchBeanDefinitionException
            PlatformTransactionManager txManager = ctx.getBean(PlatformTransactionManager.class);
            System.out.println("✅找到PlatformTransactionManager，实现类：" + txManager.getClass().getName());
        } catch (Exception e) {
            System.out.println("❌没有找到 PlatformTransactionManager Bean，@Transactional不会生效！");
        }
    }
}