# Spring AOP 切点表达式（Pointcut）详细讲解
> 前置说明：Spring AOP 的切点表达式，语法叫 **AspectJ 切点表达式语言**，Spring只支持它的子集；你现在代码里用的 `@annotation(requireToken)` 就是其中一种最常用的注解切点。
> 先区分两个概念：
> - **切点（Pointcut）**：写表达式，用来匹配「哪些方法需要被AOP拦截」
> - **通知（Advice）**：`@Before / @After / @Around / @AfterReturning / @AfterThrowing`，匹配到之后执行的增强逻辑（就是你写的token校验代码）

## 一、切点的两种写法形式
### 形式1：直接写在通知注解上（你现在的写法）
```java
@Around("@annotation(requireToken)")
public Object checkToken(ProceedingJoinPoint joinPoint, RequireToken requireToken) throws Throwable {
}
```
切点表达式直接写在 `@Around` 里面，适合简单场景。

### 形式2：单独抽离 @Pointcut（推荐复杂项目，复用切点）
```java
// 单独定义切点，给切点起名字
@Pointcut("@annotation(com.morgan.jpademo.aop.RequireToken)")
public void requireTokenPointCut(){}

// 通知引用这个切点
@Around("requireTokenPointCut()")
public Object checkToken(ProceedingJoinPoint joinPoint, RequireToken requireToken) throws Throwable {
}
```
好处：同一个匹配规则可以给 `@Before`、`@Around` 等多个通知复用。

---
## 二、Spring AOP 支持的 6 大指示器（execution、@annotation 等）
指示器就是表达式开头关键字，最常用：
1. `execution()`：**匹配方法签名（最常用，万能匹配）**
2. `@annotation()`：**匹配方法上带有指定注解（就是你现在用的！重点）**
3. `within()`：匹配某个类/包下所有类里面的方法
4. `this()`：匹配代理对象的类型
5. `target()`：匹配目标原始对象的类型
6. `args()`：匹配方法参数类型

> ❗注意：`@within`、`@target` 是匹配【类上面的注解】，不是方法注解，容易混淆！

## 三、逐个详解，重点先讲你代码里的 @annotation
### ✅ 1. @annotation() 指示器（你的代码）
作用：**匹配【方法上面标注了目标注解】的方法**
```java
@Around("@annotation(requireToken)")
```
完整不带变量写法：
```java
@Pointcut("@annotation(com.morgan.jpademo.aop.RequireToken)")
```
- 含义：拦截**方法上加了 @RequireToken** 的方法
- 如果你想拿到注解对象（像你代码里 `RequireToken requireToken`）：
  表达式里面写变量名 `@annotation(requireToken)`，**方法入参变量名必须和这个名字一模一样**，Spring自动把注解实例注入进来。

> 区分易混：
> - `@annotation(xxx)` → **方法上的注解** ✅ 你当前场景
> - `@within(xxx)` → **类上面的注解**（类加了注解，类里面所有方法都拦截）
> - `@target(xxx)` → 目标对象类上有注解

示例：
```java
// 匹配方法上加 @RequireToken
@annotation(com.morgan.jpademo.aop.RequireToken)
// 匹配类上加 @RequireToken
@within(com.morgan.jpademo.aop.RequireToken)
```

---
### ✅ 2. execution() 指示器（最通用、面试最高频）
语法模板：
```
execution(【修饰符】 【返回值类型】 【包.类.方法名】(参数列表))
```
支持通配符：
- `*`：匹配任意一段（不包含`.`）
- `..`：匹配任意包、任意多个参数（只能用在包路径、参数位置）

模板拆解：
```java
execution(public * com.morgan.jpademo.controller.*.*(..))
```
逐段翻译：
- `public`：方法修饰符 public（可以省略，写*匹配任意修饰符）
- `*`：任意返回值类型
- `com.morgan.jpademo.controller.*`：controller包下所有类
- `.*`：类中任意方法名
- `(..)`：任意参数，0个、多个都行

#### 常用 execution 示例
```java
// 1. controller包下所有public方法
execution(public * com.morgan.jpademo.controller..*.*(..))

// 2. 只匹配UserController里面所有方法
execution(* com.morgan.jpademo.controller.UserController.*(..))

// 3. 匹配名字以page开头的方法
execution(* com.morgan.jpademo.controller..*.page*(..))

// 4. 匹配返回值是Page类型的方法
execution(org.springframework.data.domain.Page * com.morgan.jpademo.controller..*.*(..))

// 5. 匹配只有一个String参数的方法
execution(* com.morgan.jpademo..*.*(java.lang.String))
```

> 注意：`execution` 是**匹配方法签名**，不是注解；适合按包、类、方法名批量拦截。
> 和你 `@annotation` 的取舍：
> - 想灵活控制：哪个接口要校验，就加注解 → 用 `@annotation`（你现在的方案）
> - 想直接拦截整个controller包全部接口 → 用 `execution`

---
### ✅ 3. within()
匹配某个包/某个类内的所有方法
```java
// controller包以及子包下所有类的所有方法
within(com.morgan.jpademo.controller..*)
// 只匹配UserController这个类
within(com.morgan.jpademo.controller.UserController)
```
简单理解：`within` 锁定范围是【类】，类里面全部方法都匹配。

### ✅ 4. args() 根据参数匹配
```java
// 匹配只有一个UserQueryDTO类型参数的方法
args(com.morgan.jpademo.dto.UserQueryDTO)
```

### ✅ 5. this() / target()
- `target(类名)`：**原始目标对象**是这个类型，就匹配（推荐大部分场景）
- `this(类名)`：**AOP代理对象**是这个类型的时候匹配
> Spring AOP默认是JDK动态代理（接口代理），很多时候this和target不一样，新手尽量少用，容易踩坑。

---
## 四、表达式可以用逻辑运算符组合：&& || !
- `&&` 并且（也可以写 and）
- `||` 或者（也可以写 or）
- `!` 非（也可以写 not）

示例组合：
```java
// 匹配controller包的方法 并且 方法上有@RequireToken
@Around("execution(* com.morgan.jpademo.controller..*.*(..)) && @annotation(requireToken)")

// 排除某个类：controller下所有方法，但是排除H2相关的
execution(* com.morgan.jpademo.controller..*.*(..)) && !within(com.morgan.jpademo.controller.H2Controller)
```

> 注意：在注解字符串里，`&&` 直接写没问题，不要和xml里的转义混淆。

---
## 五、对比你两种鉴权方案对应的切点
1. 拦截器方案：是MVC层面，按url路径匹配，不属于AOP切点
2. AOP注解方案：切点 `@annotation(requireToken)`，**只拦截打了注解的方法，和url无关**

举个直观对比：
- execution：`execution(* com.morgan.jpademo.controller..*.*(..))` → 整个controller包所有接口全部拦截
- @annotation：`@annotation(requireToken)` → 只拦截**主动加注解**的接口，粒度更细

## 六、高频坑点（非常重要，你调试切面生效问题必看）
1. **AOP只能拦截Spring Bean里面的方法**
   只有交给Spring管理的bean（@Controller、@Service、@Component）里面的方法才会被拦截；自己new出来的对象里面的方法，AOP拦截不到！
2. **同一个类里面方法互相调用，AOP失效！**
   比如UserController里面A方法调用本类B方法，B上面有@RequireToken，切面不会拦截B！因为是this调用，不走代理。这是Spring AOP经典坑。
3. 区分：Spring AOP **只能拦截public方法**，private/protected方法无法增强！
4. `@annotation` 注解必须是运行时注解
   你的 `@RequireToken` 上面必须有 `@Retention(RetentionPolicy.RUNTIME)`，否则运行时JVM拿不到注解信息，切点匹配失败、切面不触发！
   你的注解应该是这样：
   ```java
   @Target(ElementType.METHOD)
   @Retention(RetentionPolicy.RUNTIME)
   public @interface RequireToken {
   }
   ```
5. 切点表达式写类全限定名！只写简单类名大概率匹配不到。

## 七、选型建议给你（结合你现在的demo）
- 后续继续做token校验：**保留 @annotation**，按需加注解，灵活，适合部分接口开放（h2、登录接口不用校验）
- 如果以后想统一拦截全部controller接口：换成 `execution(* com.morgan.jpademo.controller..*.*(..))`，不用到处加注解

---
## 补充：execution 和 @annotation 选择一句话总结
- `execution`：按【包、类、方法名字】批量拦截
- `@annotation`：按【方法上有没有标记注解】选择性拦截

要不要我写一个混合切点例子，同时演示 `execution + @annotation`，并且给你测试哪个接口会被拦截、哪个不会？


## ========分隔符========

# @RestControllerAdvice 完整讲解

>
> 一句话总结：**全局统一异常处理器注解**，作用是拦截所有 `@RestController` 抛出的异常，统一处理、统一返回JSON，不用每个接口单独try-catch。
> 它 = `@ControllerAdvice` + `@ResponseBody` 的组合。

## 1. 注解定义

```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ControllerAdvice
@ResponseBody
public @interface RestControllerAdvice {
}
```

拆解：

- `@ControllerAdvice`：SpringMVC提供的**控制器增强**，可以对Controller做全局增强（异常处理、数据预处理、绑定设置等）
- `@ResponseBody`：方法返回值直接序列化为JSON返回给前端，不是跳转页面
  👉 对比：
    - `@ControllerAdvice`：如果方法上不加`@ResponseBody`，默认走视图跳转（返回页面）
    - `@RestControllerAdvice`：**专门给前后端分离接口用，默认返回JSON**，就是你现在这个场景

## 2. 搭配的核心注解：@ExceptionHandler

`@RestControllerAdvice` 只是一个“容器标记”，真正定义捕获哪种异常，靠 `@ExceptionHandler`

```
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 只捕获 TokenAuthException 类型异常
    @ExceptionHandler(TokenAuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String,Object> handleTokenError(TokenAuthException e){
        return Map.of("code",401,"msg",e.getMessage());
    }

    // 兜底，捕获所有Exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,Object> handleAll(Exception e){
        return Map.of("code",500,"msg","服务异常");
    }
}
```

### 匹配规则（非常重要）

Spring 会**优先匹配最精确的异常类型**：
抛出 `TokenAuthException` → 优先进入上面专门的处理器，不会走到兜底的`Exception`

>
> 类似异常的继承树匹配，越具体优先级越高

`@ResponseStatus`：设置HTTP响应状态码（401、400、500），和业务code是两套东西：

- HTTP Status：协议层面状态（浏览器/Postman看到的状态码）
- 返回JSON里的code：**业务自定义状态码**（前端业务逻辑判断用）

## 3. @RestControllerAdvice 的属性（控制拦截范围）

它继承了 `@ControllerAdvice` 的属性，用来限定拦截哪些Controller，默认**拦截所有@RestController**

```
// 常用属性示例
@RestControllerAdvice(
    basePackages = "com.morgan.jpademo.controller", // 只扫描这个包下controller
    basePackageClasses = UserController.class,     // 只拦截UserController
    annotations = RestController.class              // 只拦截带@RestController注解的类（默认）
)
```

- `basePackages`：指定包路径（最常用，大项目推荐，避免拦截别的模块）
- `assignableTypes`：指定Controller类Class>
> 不写任何属性：默认项目内所有 `@RestController` 都会被增强

## 4. 它能做的三件事（不止捕获异常！很多人只知道异常）

@ControllerAdvice系列一共有3类增强能力：

1. ✅ **@ExceptionHandler：全局异常处理（你现在在用的，最常用）**
2. `@InitBinder`：全局注册属性编辑器、参数绑定，统一处理日期转换、字符串转数字
3. `@ModelAttribute`：给所有Controller的请求**预先注入公共参数**>
> 示例 @ModelAttribute：所有接口都自动带上一个公共信息

```
@ModelAttribute
public void addCommonAttr(Model model){
    model.addAttribute("projectName","jpa-demo");
}
```

>
> ⚠️ 但是！**AOP抛出的异常，只要异常最终抛到Controller层，就可以被它捕获**，就是你TokenAspect切面抛TokenAuthException这个场景。

## 5. 生效范围 & 常见坑（重点，调试必看）

### 坑1：只能捕获 Controller 调用链上抛出来的异常

执行顺序：
`请求 → 拦截器/AOP切面 → Controller → Service → Repository`

- AOP切面抛出异常 ✅ 可以被捕获（异常往外抛，最终抛到MVC的处理链）
- 拦截器 `preHandle` 里面抛出异常：**默认可以被 @RestControllerAdvice 捕获**（SpringBoot2.3+版本支持，老版本有坑）
- ✅ Service、Repository抛出异常，向上抛到Controller，都能捕获
- ❌ 异步线程（@Async新开线程）里面抛出的异常：**捕获不到！** 不在主线程MVC调用栈

### 坑2：不会捕获已经被 try-catch 吃掉的异常

如果代码里写了 try{} catch()，并且catch里面没有throw新异常，@RestControllerAdvice看不到这个异常。

### 坑3：@RestControllerAdvice 必须交给Spring管理

类上不加注解、没有被扫描到，直接失效。一般类上加 `@RestControllerAdvice` 就会被自动扫描，不用额外加`@Component`（注解内部已经包含）。

### 坑4：和全局异常冲突

项目里不要写多个@RestControllerAdvice同时捕获同一个异常，Spring不知道优先用哪个，会报错。

## 6. 和AOP切面做异常处理的区别（你刚好两个都在用）

- `@RestControllerAdvice`：**MVC层的能力**，只处理Web请求链路的异常，专门统一接口返回格式；是SpringMVC原生能力
- AOP @Around切面：在方法执行前后做环绕逻辑，适合鉴权、日志、耗时统计；切面里抛异常，交给RestControllerAdvice兜底格式化返回

分工（你当前demo的标准分工）：

1. AOP切面：校验token，校验失败抛自定义TokenAuthException
2. RestControllerAdvice：接住这个异常，包装成统一json返回401

## 7. 拓展对比

| 注解 | 返回形式 | 使用场景 |
| --- | --- | --- |
| @RestControllerAdvice | JSON | 前后端分离接口，统一异常返回（你这个） |
| @ControllerAdvice | 视图页面（除非方法加@ResponseBody） | 传统服务端渲染Thymeleaf/JSP |

## 8. 标准工业级写法模板（你可以直接替换）

```
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.morgan.jpademo.controller")
public class GlobalExceptionHandler {

    // token认证异常 401
    @ExceptionHandler(TokenAuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<Void> handleTokenAuthException(TokenAuthException e) {
        return Result.fail(401, e.getMessage());
    }

    // 参数校验异常 400
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<Void> handleParamException(IllegalArgumentException e) {
        return Result.fail(400, e.getMessage());
    }

    // 兜底未知异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<Void> handleException(Exception e) {
        e.printStackTrace();
        return Result.fail(500, "系统繁忙，请稍后重试");
    }
}
```

如果你想，我顺便给你区分：**@RestControllerAdvice 和 Filter、Interceptor、AOP 四者执行顺序**，理清整个请求链路。
