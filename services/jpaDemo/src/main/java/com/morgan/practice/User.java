package com.morgan.practice;

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
public class User {

    private Long id;
    private String name;
    private Integer age;
    private String status;
    private Integer score;
}
