package com.morgan.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * ClassName: User
 * Package: com.morgan.entity
 *
 * @author Morgan Wei
 * @create 2026-10-31-23:30
 * @Description:
 */
@Entity
@Table(name = "t_user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer age;
    private String status; // NORMAL / LOCK
}
