package com.morgan.order.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


//name必须指定，否则会报错，因为调用的是外部的公共接口，不知道有没有注册信息，所以用绑定url，这样就会忽略name的值
@FeignClient(name = "yy", url = "http://shanhe.kim")
public interface PhoneFeignClient {

    @GetMapping("/api/za/phone.php?tel={phoneNum}")
    String getPhoneNumLoc(@PathVariable(name = "phoneNum") String phone);

}
