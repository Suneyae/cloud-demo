package com.morgan.repository;

import com.morgan.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * ClassName: UserRepository
 * Package: com.morgan.repository
 *
 * @author Morgan Wei
 * @create 2026-10-31-23:30
 * @Description:
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
}
