package com.morgan.juc.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morgan.juc.model.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class PublicApiClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String JSON_PLACEHOLDER_POST = "https://jsonplaceholder.typicode.com/posts/1";
    private static final String JSON_PLACEHOLDER_POSTS = "https://jsonplaceholder.typicode.com/posts";
    private static final String JSON_PLACEHOLDER_USERS = "https://jsonplaceholder.typicode.com/users";
    private static final String JSON_PLACEHOLDER_USER_TEMPLATE = "https://jsonplaceholder.typicode.com/users/%d";

    public PublicApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public Post getPostAsObject() {
        log.info("调用JSONPlaceholder Posts API并转换为Post对象");
        Post post = restTemplate.getForObject(JSON_PLACEHOLDER_POST, Post.class);
        log.info("Posts标题: {}, ID: {}", post.getTitle(), post.getId());
        return post;
    }

    public List<Post> getAllPosts() {
        log.info("调用JSONPlaceholder Posts列表API");
        Post[] postsArray = restTemplate.getForObject(JSON_PLACEHOLDER_POSTS, Post[].class);
        List<Post> posts = java.util.Arrays.asList(postsArray);
        log.info("获取到 {} 条Posts记录", posts.size());
        return posts;
    }

    public String getUsers() {
        log.info("调用JSONPlaceholder Users列表API");
        String result = restTemplate.getForObject(JSON_PLACEHOLDER_USERS, String.class);
        log.info("Users列表API调用完成");
        return result;
    }

    public String getUserById(int userId) {
        log.info("调用JSONPlaceholder User详情API, ID: {}", userId);
        String result = restTemplate.getForObject(JSON_PLACEHOLDER_USER_TEMPLATE, String.class, userId);
        log.info("User详情API调用完成, ID: {}", userId);
        return result;
    }
}
