package com.morgan.day01.lambda;

import com.morgan.day01.entity.User;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * ClassName: LambdaPratice
 * Package: com.morgan.day01.lambda
 *
 * @author Morgan Wei
 * @create 2026-11-03-23:40
 * @Description:
 */
public class LambdaPratice {
    public static void main(String[] args) {
        List<User> userList = Arrays.asList(
                new User(1L, "张三", 16, "ENABLE", 80),
                new User(2L, "李四", 22, "ENABLE", 90),
                new User(3L, "王五", 25, "DISABLE", 70),
                new User(4L, "赵六", 19, "ENABLE", 59),
                new User(5L, "田七", 30, "DISABLE", 95)
        );

        Predicate<User> predicate = new Predicate<User>() {
            @Override
            public boolean test(User user) {
                return "DISABLE".equals(user.getStatus());
            }
        };

        /*
        1. 定义一个 `Predicate<User>`，判断用户分数大于等于 60；再定义一个 Predicate，判断年龄小于 25。
        用 `.and()` 组合两个条件：**分数及格 并且 年龄小于 25**，最后用这个 Predicate 过滤 userList，打印结果。
        2. 沿用上面两个 Predicate，使用 `.or()` 组合：分数≥60 **或者** 年龄 < 25，过滤集合。
        3. 对 “分数≥60” 这个 Predicate 调用 `.negate()` 取反，理解取反逻辑，过滤出不及格的用户。
        4. 定义 `Consumer<User>`，实现打印用户姓名和年龄；分别用 Lambda 写法、方法引用写法实现。
        5. 定义 `Function<User, String>`，输入 User 对象，返回拼接字符串：`姓名：xxx，年龄：xxx`
        6. 定义一个通用过滤方法：
        调用这个方法，传入不同 Predicate 实现：①只查启用用户 ②只查 20 岁以上用户
        public static List<User> filterUser(List<User> list, Predicate<User> condition) {
            // 补充实现
        }
         */
        /*
        1. 定义一个 `Predicate<User>`，判断用户分数大于等于 60；再定义一个 Predicate，判断年龄小于 25。
        用 `.and()` 组合两个条件：**分数及格 并且 年龄小于 25**，最后用这个 Predicate 过滤 userList，打印结果。
         */

        Predicate<User> predicate1 = new Predicate<User>() {
            @Override
            public boolean test(User user) {
                return user.getScore()>=60;
            }
        }.and(new Predicate<User>() {
            @Override
            public boolean test(User user) {
                return user.getAge() < 25;
            }
        });

        userList.stream().filter(predicate1).forEach(user->System.out.println(user.getName()+" "+user.getAge()));

        /*
         2. 沿用上面两个 Predicate，使用 `.or()` 组合：分数≥60 **或者** 年龄 < 25，过滤集合。
         */
        List<String> list = userList.stream().filter(new Predicate<User>() {
            @Override
            public boolean test(User user) {
                return user.getScore()>=90;
            }
        }.or(new Predicate<User>() {
            @Override
            public boolean test(User user) {
                return user.getAge()<=15;
            }
        })).map(User::getName).toList();
        System.out.println(list);


        /*
                 3. 对 “分数≥60” 这个 Predicate 调用 `.negate()` 取反，理解取反逻辑，过滤出不及格的用户。

         */
        List<String> list2 = userList.stream().filter(new Predicate<User>() {
            @Override
            public boolean test(User user) {
                return user.getScore()>=60;
            }
        }.negate()).map(User::getName).toList();
        System.out.println("小于60:"+list2);

        /*

        4. 定义 `Consumer<User>`，实现打印用户姓名和年龄；分别用 Lambda 写法、方法引用写法实现。
        */
        System.out.println("=======lamba======");
        userList.stream().forEach(new Consumer<User>() {
            @Override
            public void accept(User user) {
                System.out.println("姓名："+user.getName()+",年龄："+user.getAge());
            }
        });
        Consumer<User> printWithLambda = u -> System.out.println("姓名："+u.getName()+",年龄："+u.getAge());
        userList.forEach(printWithLambda);



        // Lambda写法
        Consumer<User> printUserLambda = u -> System.out.printf("姓名：%s，年龄：%d%n", u.getName(), u.getAge());
        System.out.println("==== Lambda Consumer 打印 ====");

        userList.forEach(printUserLambda);

        // 方法引用写法（需要单独写一个静态方法）
        Consumer<User> printUserRef = LambdaPratice::printUserInfo;

        System.out.println("==== 方法引用 Consumer 打印 ====");
        userList.forEach(printUserRef);

        /*
        5. 定义 `Function<User, String>`，输入 User 对象，返回拼接字符串：`姓名：xxx，年龄：xxx`
        6. 定义一个通用过滤方法：
        调用这个方法，传入不同 Predicate 实现：①只查启用用户 ②只查 20 岁以上用户
         */


    }

    // 配套给方法引用用的静态方法
    public static void printUserInfo(User u) {
        System.out.printf("姓名：%s，年龄：%d%n", u.getName(), u.getAge());
    }



    @Test
    public void test01(){
        List<User> userList = Arrays.asList(
                new User("张三", 22),
                new User("李四", 18),
                new User("王五", 25),
                new User("赵六", 20),
                new User("钱七", 22)
        );

        // ===================== 题目1：Comparable =====================
        // 需求：修改User类，让User实现 Comparable<User>
        // 比较规则：按年龄降序；年龄相同，按姓名字典升序
        // 然后使用 Collections.sort 对userList的副本排序并打印结果
        // 提示：Integer.compare反过来就是降序
        System.out.println("===== 题目1 Comparable =====");
        Collections.sort(userList);


        // ===================== 题目2：Comparator =====================
        // 需求：使用Comparator，构造比较器
        // 规则：先姓名升序，姓名相同再年龄降序
        // 使用stream.sorted排序，收集为List打印
        System.out.println("===== 题目2 Comparator =====");

        // ===================== 题目3：Predicate =====================
        // 需求：定义Predicate<User>
        // 条件：年龄大于等于18，并且年龄小于25
        // 额外：再用negate取反，过滤出不满足上面条件的用户，分别打印两组结果
        System.out.println("===== 题目3 Predicate =====");

        // ===================== 题目4：Consumer =====================
        // 需求：定义Consumer<User>
        // 动作：打印【姓名：xxx，年龄：xx岁】
        // 用forEach遍历userList执行这个consumer
        System.out.println("===== 题目4 Consumer =====");

        // ===================== 题目5：Function<T,R> =====================
        // 需求：定义Function<User, String>
        // 功能：输入User，返回字符串 "用户-姓名"，例如 "用户-张三"
        // stream.map转换，收集成List打印
        System.out.println("===== 题目5 Function =====");

        // ===================== 题目6：Supplier =====================
        // 需求：定义Supplier<User>
        // 功能：每次get()返回一个固定用户：new User("匿名用户", 0)
        // 调用两次get，打印两个对象，观察是否是新对象
        System.out.println("===== 题目6 Supplier =====");

        // ===================== 题目7：UnaryOperator =====================
        // 需求：定义UnaryOperator<String>
        // 功能：接收字符串，前后加上【】，比如输入abc → 【abc】
        // 先取出所有用户名，再用这个UnaryOperator包装名字，打印结果
        System.out.println("===== 题目7 UnaryOperator =====");

        // ===================== 题目8：BiFunction<T,U,R> =====================
        // 需求：定义BiFunction<String, Integer, User>
        // 功能：传入name、age，返回User对象
        // 使用这个BiFunction构造 new User("孙八", 21)，打印对象
        System.out.println("===== 题目8 BiFunction =====");

        // ===================== 题目9：BiConsumer<T,U> =====================
        // 需求：准备一个Map<String,Integer>，key=姓名，value=年龄
        // 定义BiConsumer<String,Integer>
        // 动作：打印 "用户：xxx，年龄：xx"
        // map.forEach传入这个BiConsumer遍历打印
        System.out.println("===== 题目9 BiConsumer =====");

    }
}
