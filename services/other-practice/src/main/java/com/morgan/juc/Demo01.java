package com.morgan.juc;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * ClassName: Demo01
 * Package: com.morgan.juc
 *
 * @author Morgan Wei
 * @create 2026-05-24-10:42
 * @Description:
 */
public class Demo01 {

    @Autowired


    public static void main(String[] args) {

    }


    private boolean getNetworkResults(){

        // to call network request by feign client
        CompletableFuture<Map<String,Object>> completableFuture = new CompletableFuture<>();



        return true;
    }
}
