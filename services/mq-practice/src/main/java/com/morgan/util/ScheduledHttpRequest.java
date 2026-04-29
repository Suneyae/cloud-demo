package com.morgan.util;

/**
 * ClassName: ScheduledHttpRequest
 * Package: com.morgan.util
 *
 * @author Morgan Wei
 * @create 2026-01-01-21:20
 * @Description:
 */
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
public class ScheduledHttpRequest {
    // 初始化 HttpClient（可复用，无需每次创建）
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(java.time.Duration.ofSeconds(3)) // 连接超时3秒
            .build();

    public static void main(String[] args) {
        triggerRequestEvery5s();
    }

    public static void triggerRequestEvery5s() {
        // 创建定时任务线程池（单线程即可，避免资源浪费）
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        // 配置定时任务：延迟0秒开始，每5秒执行一次
        scheduler.scheduleAtFixedRate(
                ScheduledHttpRequest::sendHttpRequest, // 要执行的任务（发送HTTP请求）
                0, // 首次执行延迟时间
                5, // 每次执行间隔
                TimeUnit.SECONDS // 时间单位
        );

        // 程序退出时关闭线程池（可选，防止资源泄漏）
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
            }
            System.out.println("定时任务线程池已关闭");
        }));
    }

    /**
     * 发送HTTP请求的核心方法（包含完整异常处理）
     */
    public static void sendHttpRequest() {
        try {
            Random random = new Random();
            String url = "http://localhost:8080/sendMsgLong/"+Math.abs(random.nextInt());
            System.out.println("url is:"+url);
            // 1. 构建HTTP请求（替换为你的目标URL）
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url)) // 你的请求地址
                    .GET() // 请求方法（POST需补充body，见下方备注）
                    .timeout(java.time.Duration.ofSeconds(5)) // 请求超时5秒
                    .build();

            // 2. 发送请求并获取响应
            HttpResponse<String> response = HTTP_CLIENT.send(
                    request,
                    HttpResponse.BodyHandlers.ofString() // 响应体以字符串接收
            );

            // 3. 处理响应结果
            System.out.println("[" + java.time.LocalDateTime.now() + "] " +
                    "请求成功 | 状态码：" + response.statusCode() +
                    " | 响应体：" + response.body().substring(0, Math.min(response.body().length(), 100)));

        } catch (Exception e) {
            // 捕获所有异常，避免单个请求失败导致定时任务终止
            System.out.println("[" + java.time.LocalDateTime.now() + "] " +
                    "请求失败：" + e.getMessage());
            e.printStackTrace(); // 可选，打印详细异常栈
        }
    }
}
