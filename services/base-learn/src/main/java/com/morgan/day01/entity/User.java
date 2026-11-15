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
public class User implements Comparable<User>{

    private Long id;
    private String name;
    private Integer age;
    private String status;
    private Integer score;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(User o) {
//        if (this.age != o.age){
//            //年龄降序
//            return -(this.age - o.age);
//        }
//        return this.name.compareTo(o.getName());

        // 先比较年龄：降序，所以 other.age 放前面
        int ageCompare = Integer.compare(o.age, this.age);
        // 年龄相等，则比较姓名，字典升序
        if (ageCompare == 0) {
            return this.name.compareTo(o.name);
        }
        return ageCompare;
    }

    public void print(){
        System.out.println("name:"+name+",age:"+age);
    }

}
