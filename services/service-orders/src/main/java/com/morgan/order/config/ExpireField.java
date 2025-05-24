package com.morgan.order.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@ConfigurationProperties(prefix="expire") //配置批量绑定，在nacos下，可以无需@RefreshScope实现自动刷新
@Data
@RefreshScope
public class ExpireField {
    /**
     * nacos里的Data id morgan_nacos.properties里的属性值如下
     * expire.time=800
     * expire.time_unit=mins
     */
    private int time;
    private String timeUnit;//会自动把time_unit转成驼峰命名的变量，所以这里写timeUnit可以绑定到
}
