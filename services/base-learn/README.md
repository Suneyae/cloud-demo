# 核心思路先讲清楚
✅ 正确路线：**双线并行：补Java基础（按需补，不啃整本教材） + 微服务技术栈「Demo驱动」，以项目场景带动知识点，遇到哪个基础短板就补哪个，而不是先把Java全部学完再学微服务**

> 一句话：**基础不是一次性闭关补完，而是微服务练习过程中按需回填；优先掌握「近几年企业SpringCloud微服务的标配技术」，放弃冷门、过时组件**
> 现在主流版本：SpringCloud Alibaba（Nacos、Sentinel、Seata），远多于原生SpringCloud（Eureka、Hystrix已经淘汰）

下面给**可落地、有顺序、带检查点、有练习任务**的操作方案，分阶段，每个阶段有目标、做什么、练什么、补什么Java基础、验收标准。

## 前置准备
1. 环境固定：JDK17（你现在就在用）、Maven、IDEA；统一SpringBoot3 + SpringCloud Alibaba 2022/2023版本
2. 建立一个自己的学习仓库，仓库结构：
```
cloud-study
├── base-learn      // Java基础补漏小demo（单独小测试类）
├── cloud-demo      // 微服务聚合多module项目（就是你现在这种结构）
└── README.md       // 每个组件学习记录、踩坑笔记
```
3. 工具：Postman / ApiPost、Redis、MySQL，后面用到Seata再装Nacos

---
# 阶段0：先划定【近几年微服务必用清单】（只学企业高频，砍掉过时技术）
> 很多教程堆一堆组件，很多已经淘汰，不用浪费时间
✅ 必学高频（现在Java后端微服务面试+业务开发主力）
1. 注册&配置中心：Nacos（替代Eureka、Consul）
2. 远程调用：OpenFeign
3. 网关：Spring Cloud Gateway（替代Zuul，Zuul停止维护）
4. 限流熔断降级：Sentinel（替代Hystrix）
5. 分布式事务：Seata AT模式（最常用）
6. 消息队列：RocketMQ / RabbitMQ（异步解耦、削峰）
7. 分布式锁：Redis Redisson
8. 分布式ID：雪花算法、Redis自增
9. 统一鉴权：网关层统一JWT/Sa-Token（你已经在本地写过AOP Token，正好衔接）
10. 统一返回、全局异常、参数校验、日志、链路追踪：SkyWalking / Sleuth+Zipkin

❌ 过时，直接跳过：Eureka、Hystrix、Zuul、SpringCloud Config

> 学习顺序原则：**先学会服务之间怎么通信，再学容错、再学分布式问题（事务、锁）**

---
# 阶段1：筑基（1~2周，最重要！Java基础按需回填 + SpringBoot吃透）
> 所有SpringCloud建立在SpringBoot之上；SpringBoot底层就是Java基础 + Spring核心。
> 不要去买《Java编程思想》从头啃，**采用「问题驱动补基础」**

## 1）先锁定你必须补齐的Java基础清单（微服务开发高频用到，其他暂时放一放）
只补这些，别的基础等后面遇到再说：
- OOP：多态、接口、抽象类（重点：接口、默认方法、函数式接口）
- 集合：HashMap底层、ConcurrentHashMap、CopyOnWriteArrayList（微服务并发、缓存必踩坑）
- 多线程：Thread、线程池ThreadPoolExecutor、CompletableFuture（异步、Sentinel、MQ消费大量用到）
- Lambda、Stream（Spring很多API大量流式写法，你JPA Specification已经接触Predicate）
- 异常体系：受检异常、非受检异常（你刚写的GlobalExceptionHandler，正好结合理解）
- IO、NIO基础（Gateway、Netty底层用到，不用深挖，懂概念即可）
- 反射、注解（AOP、@FeignClient、各种Spring注解全部依赖反射注解，你刚学完AOP、@RestControllerAdvice，绝佳时机）
- 泛型（统一Result返回、Feign接口传参经常卡泛型报错）

## 2）操作方式（不是看书刷题！）
1. 每一次在微服务Demo里遇到报错，定位根源是Java基础 → 单独在 `base-learn` 模块写最小Demo验证
> 举例：
> 写OpenFeign的时候泛型报错 → 去补Java泛型
> 写异步调用CompletableFuture报错 → 补线程池
> 看AOP源码、注解解析看不懂 → 补注解+反射
2. 配套练习：
    - 每天30分钟，只练上面清单里的点，写极简测试代码
    - 拒绝刷海量算法题，现阶段只需要基础语法，算法后面再说
3. SpringBoot巩固（SpringCloud的地基）
   你现在的jpaDemo就是最好的载体，基于这个项目延伸练习：
    - Bean生命周期、Bean的作用域
    - Spring的IOC、DI
    - AOP（你已经写了Token切面，趁热打铁吃透）
    - SpringMVC完整请求链路：Filter → Interceptor → AOP → Controller → @RestControllerAdvice（你前面全部接触了，串起来！）
      ✅ 验收标准：
      能独立说清楚：一个HTTP请求进来，在SpringBoot里面完整执行链路，Filter、拦截器、AOP、全局异常的先后顺序；能独立写AOP切面、自定义注解。

---
# 阶段2：微服务基础通信层（2周，第一个多Module聚合项目，就是你现在的cloud-demo）
目标：搭建一套最小微服务集群，学会**服务怎么注册、怎么互相调用**
模块规划（多module聚合Maven，和你截图结构一致）
- service-product（商品服务）
- service-order（订单服务）
- cloud-gateway（网关）

## 按顺序逐个搭建，每一个组件单独做Demo，每做完一个写README
### 步骤1：Nacos（注册中心 + 配置中心二合一）
1. 本地单机部署Nacos
2. product、order服务启动注册到Nacos
3. 从Nacos读取配置，代替本地yml配置
> 配套基础补漏：理解配置文件加载优先级、Spring的@RefreshScope动态刷新

✅ 验收：两个服务启动，Nacos控制台能看到实例；修改Nacos配置，服务动态刷新。

### 步骤2：OpenFeign 远程调用
需求：下单服务order调用product服务扣减库存
1. 写Feign接口，远程调用
2. 学会超时配置、日志打印
> 容易踩坑：Feign传参、GET/POST传参区别，这里大量用到接口、泛型
✅ 验收：调用下单接口，自动远程调用商品服务，成功；服务下线后调用报错。

> 到这里，你已经有最小微服务骨架：Nacos + 两个业务服务 + Feign调用

### 步骤3：Spring Cloud Gateway 网关
1. 网关接入Nacos，统一入口
2. 路由转发、路径重写
3. 在网关层实现统一Token鉴权（线上标准方案：网关鉴权，下游服务不用校验token，替代你之前写在AOP里的token）
> 补基础：Gateway底层基于Netty、Reactor（WebFlux），这里只需要会用，Reactor基础简单了解即可，不用深挖
✅ 验收：所有请求统一走网关，网关校验token，非法token直接401，不转发到后端服务

> ⚠️ 这一阶段非常关键：把你之前学的Token校验，从AOP迁移到网关，体会线上真实架构分工

---
# 阶段3：容错 & 流量治理（1.5周，Sentinel为主）
组件：Sentinel（限流、熔断、降级、热点参数限流）
场景：订单服务调用商品服务失败，不能无限等待，需要熔断；接口流量太高要限流保护
练习任务：
1. Sentinel控制台接入微服务
2. 熔断规则：远程调用失败比例达到阈值，熔断
3. 限流规则：QPS限流
4. 自定义降级返回结果
> 基础关联：线程池、异步、异常处理
✅ 验收：压测模拟高并发，触发限流/熔断，返回友好降级信息，不会雪崩

> 到这里，你的项目已经具备生产微服务最核心的高可用能力

---
# 阶段4：分布式问题专项（2~3周，微服务最难的一块，逐个攻克）
> 这一块是区分CRUD开发和微服务开发的核心，**每一个技术都绑定业务场景练习，不要只看理论**
顺序：Redis分布式锁 → MQ异步 → Seata分布式事务
1. **Redisson分布式锁**
   场景：下单扣库存，并发超卖问题
   练习：对比原生Redis SETNX 和 Redisson，理解可重入锁、锁超时、看门狗
> 补基础：并发、线程安全，正好巩固ConcurrentHashMap
2. **MQ（RocketMQ优先）**
   场景：下单成功后，异步发送消息，通知短信、积分，解耦
   练习：生产者、消费者，消息确认ACK、重试、死信队列
> 补基础：异步编程，CompletableFuture
3. **Seata AT模式分布式事务（高频面试）**
   场景：下单（order入库）+扣库存（product），要么全部成功，要么全部回滚
> AT模式是企业90%场景在用，TCC模式先不学，太重
✅ 验收：模拟异常，一个服务成功一个失败，事务自动回滚

## 附加可选：分布式ID
雪花算法，自己手写一个雪花算法工具类，理解位运算（这里会补位运算基础）

---
# 阶段5：可观测 + 统一规范（1周，线上项目必备）
1. SkyWalking链路追踪：查看一次请求经过网关→order→product完整调用链路，看到哪里慢、哪里报错
2. 统一日志规范、MDC链路id
3. 参数校验：@Validated，全局统一参数异常返回
   ✅ 验收：一次调用，在SkyWalking看到完整链路耗时

---
# 阶段6：整合、复盘 + 模拟上线
把前面所有组件整合进一套完整链路：
前端请求 → Gateway网关（鉴权、路由）→ Order服务（Feign调用Product、Seata事务、Sentinel保护、MQ发送消息）
写完一套完整链路后：
1. 画架构图
2. 在README里整理整套流程、每个组件作用
3. 梳理面试高频问题（每一个组件的优缺点、踩坑点）

---
# 【最重要的配套学习规则，解决你Java基础薄弱的痛点】
## 规则1：遇到基础短板，「最小Demo验证」，不堆积
举例子：
> 现象：Feign泛型报错、看不懂函数式回调、看不懂CompletableFuture
> 动作：
> 1. 停下来，不要硬抄代码
> 2. 新建一个Java主类，只写这一个知识点的小demo跑通
> 3. 搞懂之后，回到微服务项目修复代码
> 4. 在笔记里记录这个知识点

## 规则2：拒绝“复制粘贴”，每一个组件，分两次写
第一次：跟着教程搭建跑通
第二次：删掉代码，**凭记忆从零重新搭建一遍模块**，这一步才是真正掌握

## 规则3：固定笔记模板（放在每个模块README）
每个技术点记录4块：
1. 这个组件是解决什么业务问题？（为什么需要它，没有它会怎么样）
2. 核心用法 + 最小可运行代码
3. 我踩了什么报错、怎么解决
4. 底层简单原理（一句话）

## 规则4：优先掌握「怎么用」，后期再补源码
基础薄弱阶段：**先用起来，先懂业务场景，不要一头扎进源码**
比如Sentinel、Nacos、Gateway，先会配置、会实现限流熔断；等整套链路跑通，再选择性看少量源码。

## 规则5：定期复盘，每周一次串联
每周花1小时，口头自问自答：
- 请求先走哪里？网关做什么？
- Nacos是干什么的？和配置中心区别？
- Feign调用失败怎么办？Sentinel熔断什么时候触发？
- Seata AT什么时候回滚？
  讲不出来，就是薄弱点，回头补。

---
# 避坑清单（很多基础薄弱的人踩）
1. ❌ 不要同时学一堆组件，一个吃透再下一个，不要并行开Nacos、Sentinel、Seata一起搭
2. ❌ 不要跳过SpringBoot直接上Cloud
3. ❌ 不要沉迷各种冷门组件、各种高级模式（TCC、SAGA事务、复杂负载均衡策略），先掌握主流AT、轮询负载均衡
4. ❌ 不要只看视频不动手，视频看完=0，必须自己搭建多模块项目
5. ❌ 不要去啃厚厚的Java教材，**场景化补基础效率远高于顺序看书**

---
# 学习资源选择（精简，不堆一大堆）
1. SpringCloud Alibaba官方文档（第一优先级，最权威）
2. B站：尚硅谷/黑马的 SpringCloud Alibaba 新版课程，只看里面上面清单里的组件，跳过过时内容
3. 你自己的代码仓库，你的demo + 笔记是最好的复习材料

---
# 可选：给你一个每日时间模板（业余学习版本）
- 工作日：1.5h
  30min：补对应Java基础小Demo
  60min：微服务组件搭建调试
- 周末：4~6h，完整搭建一个新模块、整合、复盘

如果你想要，我可以给你一份：**cloud-demo聚合项目的pom父工程模板 + 第一个Nacos+Feign最小模块的初始代码**，你直接导入就能开始阶段2。