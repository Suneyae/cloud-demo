package com.morgan.juc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: Post
 * Package: com.morgan
 *
 * @author Morgan Wei
 * @create 2026-05-24-11:47
 * @Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private Integer id;
    private String title;
    private String body;
    private Long userId;
}
