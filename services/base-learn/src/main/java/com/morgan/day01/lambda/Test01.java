package com.morgan.day01.lambda;

import com.morgan.day01.entity.User;

import java.util.function.Predicate;

/**
 * ClassName: Test01
 * Package: com.morgan.day01.lambda
 *
 * @author Morgan Wei
 * @create 2026-11-03-23:29
 * @Description:
 */
public class Test01 {
    public static void main(String[] args) {
//        Predicate<User> predicate = new Predicate<User>() {
//            @Override
//            public boolean test(User user) {
//                return user.getAge() > 18;
//            }
//        };

        // 写法2：Lambda（简化，日常最常用）
        Predicate<User> predicate = user -> user.getAge() > 18;

        // 写法3：方法引用（逻辑抽成静态方法后进一步简化）
//        Predicate<User> predicate3 = Test::isAdult;

        // 写法4：直接作为参数传给Stream.filter
//        userList.stream().filter(user -> user.getAge()>18);
    }

}
