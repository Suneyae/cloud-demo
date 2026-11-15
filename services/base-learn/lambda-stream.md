# Lambda + Stream 练习题（纯练习版，无答案）

> 配套实体类与测试数据见文末「附录」，所有题目直接复制到 IDEA 的 main 方法或独立测试类中运行。
> 做完再对照之前的讲解自查，不要提前看答案。

---

## 阶段一：Lambda 基础（5 题）

**练习 1：匿名内部类 → Lambda 转换**
用原生匿名内部类写一个 `Predicate<User>`，判断用户年龄大于 18；再改写为 Lambda 一行版。

**练习 2：无参数 / 有参数 / 无返回的 Lambda**
用 `Consumer<User>` 打印一个用户对象；用方法引用（`类::方法`）简化。

**练习 3：Function 转换**
写一个 `Function<User, String>`，输入一个 `User`，返回它的 `name`。

**练习 4：Predicate 组合（and / or / negate）**
- 定义两个 Predicate：年龄大于 18、状态为 `ENABLE`
- 组合出「年龄>18 且 启用」的条件
- 组合出「年龄>18 或 启用」的条件
- 对「成年」条件取反

**练习 5：Runnable / Supplier**
- 用 Lambda 写一个 `Runnable`，执行时打印一句话
- 用 `Supplier<String>` 返回一个固定的字符串

---

## 阶段二：Stream 基础（6 题）

数据源使用附录中的 `userList`（5 个用户）。

**练习 1：filter 过滤（入参就是 Predicate！）**
过滤出：状态为 `ENABLE` 且年龄大于 18 的用户，收集成 `List<User>`。

**练习 2：map 映射**
过滤出年龄大于 18 的用户，再把每个用户映射成它的 `name`，得到 `List<String>`。

**练习 3：sorted 排序**
按年龄升序排序；若年龄相同，再按分数降序排序，输出结果。

**练习 4：distinct / skip / limit**
先过滤分数 ≥ 60 的用户，再跳过前 1 个，取 2 个，收集成 `List<User>`。

**练习 5：匹配 anyMatch / allMatch / noneMatch**
- 是否存在状态为 `DISABLE` 的用户（`anyMatch`）
- 是否所有用户年龄都大于 16（`allMatch`）
- 是否没有用户分数小于 60（`noneMatch`）

**练习 6：聚合 count / max / min**
- 统计启用用户的数量（`count`）
- 求所有用户的最大年龄、最小年龄（`mapToInt + max/min`）

---

## 进阶收集器 Collectors（4 题）

**练习 7：groupingBy 分组**
按 `status` 分组，得到 `Map<String, List<User>>`。

**练习 8：partitioningBy 分区**
按「年龄是否 ≥ 18」分区，得到 `Map<Boolean, List<User>>`。

**练习 9：joining 拼接**
把所有用户的 `name` 用逗号拼接成一个字符串。

**练习 10：toMap 转 Map**
按 `id` 作为 key，用户对象作为 value，转成 `Map<Long, User>`。
（提示：注意 key 重复的问题）

---

## 阶段三：Stream Predicate 与 JPA Specification 对照实战（重点）

**练习 11：内存版动态多条件查询（Stream + java.util.function.Predicate）**

写一个方法：
```java
public List<User> queryMem(List<User> list, String name, Integer minAge, String status)
```
逻辑：
- 收集所有满足条件的 `Predicate<User>` 到集合（name 非空拼包含；minAge 非空拼 ≥；status 非空拼等于）
- 用 Stream 的 `reduce(Predicate::and)` 把所有条件合并，无条件时返回「全部放行」
- 最后 `filter` 合并后的条件，收集结果

**练习 12：等价 JPA Specification 版（数据库条件）**

写一个方法，返回 `Specification<User>`：
```java
public Specification<User> queryDb(String name, Integer minAge, String status)
```
逻辑（对应你之前的分页代码）：
- 收集 `jakarta.persistence.criteria.Predicate` 到集合
- name 用 `cb.like` 模糊；minAge 用 `cb.greaterThanOrEqualTo`；status 用 `cb.equal`
- 条件为空返回 `cb.conjunction()`
- 最后 `cb.and(predicateList.toArray(new Predicate[0]))`

**练习 13：对照总结（思考题，不写代码）**
用自己的话，对比上面两个方法在「收集条件 → 判断加入 → 合并 and」三个环节的异同，指出两个 `Predicate` 各来自哪个包、各自用途。

---

## 阶段四：综合大题（独立完成）

基于 `userList`，用一条 Stream 链完成以下全部需求：

1. 过滤出状态为 `ENABLE` 的用户
2. 过滤出分数 ≥ 60 的用户
3. 过滤出年龄 ≥ 18 的用户
4. 按分数**降序**排序
5. 提取每个用户的 `name`
6. 用逗号拼接成一个字符串

**加分题：** 在上述结果的基础上，再把筛选出的用户按「年龄是否 ≥ 20」分成两组，输出 `Map<Boolean, List<User>>`。

---

## 附录

### 实体类 `User.java`

```java
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String status; // ENABLE / DISABLE
    private Integer score;
}
```

### 测试数据

```java
import java.util.Arrays;
import java.util.List;

List<User> userList = Arrays.asList(
        new User(1L, "张三", 16, "ENABLE", 80),
        new User(2L, "李四", 22, "ENABLE", 90),
        new User(3L, "王五", 25, "DISABLE", 70),
        new User(4L, "赵六", 19, "ENABLE", 59),
        new User(5L, "田七", 30, "DISABLE", 95)
);
```

### 需要的 import（按需添加）

```java
import java.util.function.Predicate;   // 阶段一、三内存版用（JDK原生）
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;    // 阶段二用
import java.util.Comparator;           // 排序用

// 阶段三 JPA 版需要（注意与上面区分！）
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;   // JPA 的 Predicate，不是函数式接口
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
```

> ⚠️ 提示：练习时特别留意阶段三里两个 `Predicate` 的区别——写 Stream 过滤用 `java.util.function.Predicate`，写 Specification 拼 SQL 条件用 `jakarta.persistence.criteria.Predicate`，二者不要导错包。

---
*完成全部题目后，如需核对答案，可对照讲解中的参考答案部分。*
