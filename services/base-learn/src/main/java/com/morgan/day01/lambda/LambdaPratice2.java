package com.morgan.day01.lambda;

import com.morgan.day01.entity.User;
import com.morgan.day01.entity.User;
import com.morgan.day01.entity.User2;
import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * ClassName: LambdaPratice
 * Package: com.morgan.day01.lambda
 *
 * @author Morgan Wei
 * @create 2026-11-03-23:40
 * @Description:
 */
public class LambdaPratice2 {
    public static void main(String[] args) {


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
        List<User> userList1 = new ArrayList<>(userList);
        Collections.sort(userList1);
        System.out.println("排序结果："+userList1);
        System.out.println("===== old list =====");
        userList.forEach(new Consumer<User>() {
            @Override
            public void accept(User user) {
                user.print();
            }
        });
        System.out.println("=====new List =====");

        userList1.forEach(new Consumer<User>() {
            @Override
            public void accept(User user) {
                user.print();
            }
        });

        // ===================== 题目2：Comparator =====================
        // 需求：使用Comparator，构造比较器
        // 规则：先姓名升序，姓名相同再年龄降序
        // 使用stream.sorted排序，收集为List打印
        System.out.println("===== 题目2 Comparator =====");
        List<User2> userList2 = Arrays.asList(
                new User2("张三", 22),
                new User2("李四", 18),
                new User2("王五", 25),
                new User2("赵六", 20),
                new User2("钱七", 22)
        );
        List<User2> user2Lit = new ArrayList<>(userList2);
        userList2.sort(new Comparator<User2>() {
            @Override
            public int compare(User2 o1, User2 o2) {
                int comparedName = o1.getName().compareTo(o2.getName());
                if(comparedName == 0){
                    return -Integer.compare(o1.getAge(),o2.getAge());
                }
                return comparedName;
            }
        });
        Comparator<User2> comparator = Comparator.comparing(User2::getName).thenComparing(Comparator.comparing(User2::getAge).reversed());
        user2Lit.sort(comparator);
        System.out.println(userList2);
        System.out.println(user2Lit);


        // ===================== 题目3：Predicate =====================
        // 需求：定义Predicate<User>
        // 条件：年龄大于等于18，并且年龄小于25
        // 额外：再用negate取反，过滤出不满足上面条件的用户，分别打印两组结果
        System.out.println("===== 题目3 Predicate =====");
        Predicate<User2> predicate = new Predicate<User2>() {
            @Override
            public boolean test(User2 user2) {
                return false;
            }
        };

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

        if(new LengthComparator().compare("abc88","xxxx")>0){
            System.out.println(">");
        }

    }
}

class LengthComparator implements Comparator<String>{

    @Override
    public int compare(String o1, String o2) {
        return o1.length()-o2.length();
    }
}