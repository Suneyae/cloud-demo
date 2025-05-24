package com.morgan.product.controller;

import com.morgan.product.bean.Product;
import com.morgan.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;
    //查询商品
    @GetMapping("/getProd/{id}")
    public Product getProductById(@PathVariable("id") Long prodId){
        System.out.println("hello...");//用来看是哪个端口号上的方法在响应
        Product product = productService.getProductById(prodId);
        return product;
    }
}
