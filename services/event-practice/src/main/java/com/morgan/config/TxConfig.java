package com.morgan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * ClassName: TxConfig
 * Package: com.morgan.config
 *
 * @author Morgan Wei
 * @create 2026-11-14-16:01
 * @Description:
 */
@Configuration
public class TxConfig {

    @Bean
    public DataSource dataSource(){
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    /**
     * 创建 PlatformTransactionManager 这个bean，从而让事务生效，否则不会生效

     1. **`@Transactional`写上不代表事务就开启，必须要有`PlatformTransactionManager`Bean**；Spring‑Boot 只有检测到 DataSource 才会自动装配它。
     2. `@TransactionalEventListener`不是普通事件监听，**强绑定数据库事务同步机制，不能脱离事务环境做测试**。
     3. 调试事务两大工具：
     - `ctx.getBean(PlatformTransactionManager.class)`：检查容器有没有事务管理器 Bean；
     - `TransactionSynchronizationManager.isActualTransactionActive()`：检查**当前线程是否处于真实活跃事务内**。

     * @param dataSource
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);

    }
}
