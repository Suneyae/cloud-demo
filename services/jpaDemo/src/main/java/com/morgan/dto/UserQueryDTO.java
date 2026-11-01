package com.morgan.dto;

import lombok.Data;

import java.util.List;

/**
 * ClassName: UserQueryDTO
 * Package: com.morgan.dto
 *
 * @author Morgan Wei
 * @create 2026-10-31-23:29
 * @Description:
 */
@Data
public class UserQueryDTO {
    private String name;       // 模糊查询姓名
    private Integer minAge;    // 最小年龄 >=
    private Integer maxAge;    // 最大年龄 <=
    private List<String> statusList; // in查询状态
}
