package com.morgan.order.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


//name必须指定，否则会报错，因为调用的是外部的公共接口，不知道有没有注册信息，所以用绑定url，这样就会忽略name的值
@FeignClient(name = "xx", url = "https://wttr.in")
public interface WeatherFeignClient {

    @GetMapping("/{city}?format={rspFmt}")
    public String getWeatherByCity(@PathVariable(name = "city") String cityNm,@PathVariable(name = "rspFmt") int respFormat);
}
