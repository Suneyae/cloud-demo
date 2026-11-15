package com.morgan.day01.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: User
 * Package: com.morgan.day01.lambda
 *
 * @author Morgan Wei
 * @create 2026-11-03-23:13
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User2 {

    private String name;
    private Integer age;





    public void print(){
        System.out.println("name:"+name+",age:"+age);
    }

}
