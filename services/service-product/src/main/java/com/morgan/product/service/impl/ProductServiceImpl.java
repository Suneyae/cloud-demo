package com.morgan.product.service.impl;

import com.morgan.product.bean.Product;
import com.morgan.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public Product getProductById(Long prodId) {
        Product product = new Product();//alt+enter , by GenerateAllSetter
        product.setId(prodId);
        product.setPrice(new BigDecimal(4));
        product.setProductName("Apples"+prodId);
        product.setNum(3);
        return product;
    }


}
