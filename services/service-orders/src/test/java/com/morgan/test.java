package com.morgan;

import com.morgan.order.OrderMainApplication;
import com.morgan.order.feign.WeatherFeignClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = OrderMainApplication.class)
public class test {

    @MockBean
    WeatherFeignClient weatherFeignClient;
    @Test
    void test(){
        System.out.println("天气:"+weatherFeignClient.getWeatherByCity("Nanjing",4));
    }
}
