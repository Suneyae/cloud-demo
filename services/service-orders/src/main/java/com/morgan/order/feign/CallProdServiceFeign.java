package com.morgan.order.feign;

import com.morgan.order.feign.impl.CallProdServiceFeignImpl;
import com.morgan.product.bean.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//服务降级
@FeignClient(name = "service-product",fallback = CallProdServiceFeignImpl.class)
public interface CallProdServiceFeign {
    @GetMapping("/getProd/{id}")
    public Product getProductById(@PathVariable("id") Long prodId);
}
