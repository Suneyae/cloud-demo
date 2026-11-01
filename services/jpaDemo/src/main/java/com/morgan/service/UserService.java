package com.morgan.service;

import com.morgan.dto.UserQueryDTO;
import com.morgan.entity.User;
import com.morgan.repository.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 核心：动态拼接 Predicate
     */
    public Page<User> pageQuery(UserQueryDTO queryDTO) {
        Specification<User> spec2 = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();

                // 1. name 模糊 like
                if (StringUtils.hasText(queryDTO.getName())) {
                    predicateList.add(
                            cb.like(root.get("name"), cb.literal("%" + queryDTO.getName() + "%"))
                    );
                }
                // 2. minAge >=
                if (queryDTO.getMinAge() != null) {
                    predicateList.add(cb.greaterThanOrEqualTo(root.get("age"), queryDTO.getMinAge()));
                }
                // 3. maxAge <=
                if (queryDTO.getMaxAge() != null) {
                    predicateList.add(cb.lessThanOrEqualTo(root.get("age"), queryDTO.getMaxAge()));
                }
                // 4. status in (...)
                if (queryDTO.getStatusList() != null && !queryDTO.getStatusList().isEmpty()) {
                    predicateList.add(root.get("status").in(queryDTO.getStatusList()));
                }

                // ✅ 没有条件返回 cb.conjunction() = true，查全部
                if (predicateList.isEmpty()) {
                    return cb.conjunction();
                }
                // 所有条件 AND
                return cb.and(predicateList.toArray(new Predicate[0]));
            }
        };

        Specification<User> spec = (root, cq, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();

            // 1. name 模糊 like
            if (StringUtils.hasText(queryDTO.getName())) {
                predicateList.add(
                        cb.like(root.get("name"), cb.literal("%" + queryDTO.getName() + "%"))
                );
            }
            // 2. minAge >=
            if (queryDTO.getMinAge() != null) {
                predicateList.add(cb.greaterThanOrEqualTo(root.get("age"), queryDTO.getMinAge()));
            }
            // 3. maxAge <=
            if (queryDTO.getMaxAge() != null) {
                predicateList.add(cb.lessThanOrEqualTo(root.get("age"), queryDTO.getMaxAge()));
            }
            // 4. status in (...)
            if (queryDTO.getStatusList() != null && !queryDTO.getStatusList().isEmpty()) {
                predicateList.add(root.get("status").in(queryDTO.getStatusList()));
            }

            // ✅ 没有条件返回 cb.conjunction() = true，查全部
            if (predicateList.isEmpty()) {
                return cb.conjunction();
            }
            // 所有条件 AND
            return cb.and(predicateList.toArray(new Predicate[0]));
        };

        // 分页 第0页，每页10条
        Pageable pageable = PageRequest.of(0, 10);
//        return userRepository.findAll(spec, pageable);
        return userRepository.findAll(spec2,pageable);
    }

    // 插入测试数据
    public void initData() {
        User u1 = new User();
        u1.setName("张三");
        u1.setAge(20);
        u1.setStatus("NORMAL");

        User u2 = new User();
        u2.setName("李四");
        u2.setAge(28);
        u2.setStatus("LOCK");

        User u3 = new User();
        u3.setName("张三丰");
        u3.setAge(35);
        u3.setStatus("NORMAL");

        userRepository.save(u1);
        userRepository.save(u2);
        userRepository.save(u3);
    }
}