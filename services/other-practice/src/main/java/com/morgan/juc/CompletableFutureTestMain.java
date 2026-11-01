package com.morgan.juc;

import com.fasterxml.jackson.databind.JsonNode;
import com.morgan.juc.client.PublicApiClient;
import com.morgan.juc.model.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ClassName: CompletableFutureTestMain
 * Package: com.morgan.juc
 *
 * @author Morgan Wei
 * @create 2026-05-24
 * @Description: CompletableFuture网络请求测试主程序（独立运行，无需Spring）
 */
@Slf4j
public class CompletableFutureTestMain {

    private static PublicApiClient apiClient;
    private static final ExecutorService executor = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws Exception {
        log.info("========== 初始化测试环境 ==========");
        
        // 手动初始化RestTemplate和ApiClient
        RestTemplate restTemplate = new RestTemplate();
        apiClient = new PublicApiClient(restTemplate);
        
        log.info("初始化完成，开始运行CompletableFuture测试...\n");

        // 创建测试实例并运行
        CompletableFutureTestMain tester = new CompletableFutureTestMain();
        
        // 运行测试
        tester.testAsyncJsonProcessing();

        log.info("\n========== 所有测试完成！ ==========");
        
        // 保持程序运行
        Thread.sleep(5000);
        executor.shutdown();
    }

    /**
     * 测试1: 简单的异步JSON处理
     */
    public void testAsyncJsonProcessing() throws Exception {
        log.info("\n========== CompletableFuture + JSON处理测试 ==========");
        long start = System.currentTimeMillis();

        CompletableFuture<Post> postFuture = CompletableFuture.supplyAsync(() -> {
            log.info("[线程{}] 开始获取Posts数据", Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            Post post = apiClient.getPostAsObject();
            log.info("[线程{}] Posts数据获取完成: {}", Thread.currentThread().getName(), post.getTitle());
            return post;
        }, executor);

        CompletableFuture<List<JsonNode>> usersFuture = CompletableFuture.supplyAsync(() -> {
            log.info("[线程{}] 开始获取Users数据", Thread.currentThread().getName());
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            String jsonStr = apiClient.getUsers();
            try {
                JsonNode arrayNode = apiClient.getObjectMapper().readTree(jsonStr);
                // 获取数组中的用户列表
                if (arrayNode.isArray()) {
                    List<JsonNode> users = new java.util.ArrayList<>();
                    for (JsonNode node : arrayNode) {
                        users.add(node);
                    }
                    log.info("[线程{}] 获取到 {} 个用户", Thread.currentThread().getName(), users.size());
                    return users;
                } else {
                    log.warn("[线程{}] JSON数据格式异常", Thread.currentThread().getName());
                    return List.of();
                }
            } catch (Exception e) {
                log.error("解析失败", e);
                return List.of();
            }
        }, executor);

        CompletableFuture<Void> combined = CompletableFuture.allOf(postFuture, usersFuture);

        combined.thenRun(() -> {
            long duration = System.currentTimeMillis() - start;
            log.info("\n[完成] 所有异步请求完成，总耗时: {} ms", duration);

            Post post = safeJoin(postFuture, null);
            if (post != null) {
                log.info("[Posts结果] ID: {}, Title: {}", post.getId(), post.getTitle());
            }

            List<JsonNode> users = safeJoin(usersFuture, List.of());
            users.forEach(user -> {
                String name = user.get("name").asText();
                String email = user.get("email").asText();
                log.info("[Users结果] {} - {}", name, email);
            });
        });

        waitForCompletion(combined, 10000);
    }

    /**
     * 测试2: 多个独立的API请求
     */
    public void testMultipleApiRequests() throws Exception {
        log.info("\n========== 测试2: 多个独立API请求 ==========");
        long start = System.currentTimeMillis();

        CompletableFuture<String> postFuture = CompletableFuture.supplyAsync(() -> {
            log.info("[线程{}] 请求Posts API", Thread.currentThread().getName());
            return apiClient.getPostAsObject().toString();
        }, executor);

        CompletableFuture<String> userFuture = CompletableFuture.supplyAsync(() -> {
            log.info("[线程{}] 请求Users API", Thread.currentThread().getName());
            return apiClient.getUsers();
        }, executor);

        CompletableFuture<String> dogFuture = CompletableFuture.supplyAsync(() -> {
            log.info("[线程{}] 请求随机User API", Thread.currentThread().getName());
            return "这个功能待实现";
        }, executor);

        CompletableFuture.allOf(postFuture, userFuture, dogFuture)
            .thenRun(() -> {
                long duration = System.currentTimeMillis() - start;
                log.info("\n[完成] 所有请求完成，总耗时: {} ms", duration);
                log.info("Posts结果: {}", safeJoin(postFuture, ""));
            })
            .exceptionally(ex -> {
                log.error("测试失败", ex);
                return null;
            });

        waitForCompletion(CompletableFuture.allOf(postFuture, userFuture, dogFuture), 10000);
    }

    private <T> T safeJoin(CompletableFuture<T> future, T defaultValue) {
        try {
            return future.get(1, java.util.concurrent.TimeUnit.SECONDS);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private void waitForCompletion(CompletableFuture<?> future, long timeoutMs) {
        try {
            future.orTimeout(timeoutMs, java.util.concurrent.TimeUnit.MILLISECONDS).join();
        } catch (Exception e) {
            log.error("等待超时", e);
        }
    }
}
