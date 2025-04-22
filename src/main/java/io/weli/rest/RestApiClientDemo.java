package io.weli.rest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import io.weli.rest.model.Post;
import io.weli.rest.model.User;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RestApiClientDemo {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        // 配置代理（如果启用）
        configureProxy();

        // 设置基础URL
        RestAssured.baseURI = BASE_URL;

        try {
            // 示例1: 获取所有帖子并映射到Post对象列表
            System.out.println("获取所有帖子:");
            Response response = given()
                    .when()
                    .get("/posts")
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();

            List<Post> posts = objectMapper.readValue(response.asString(), new TypeReference<List<Post>>() {});
            System.out.println("帖子总数: " + posts.size());
            System.out.println("前5个帖子标题:");
            for (int i = 0; i < Math.min(5, posts.size()); i++) {
                Post post = posts.get(i);
                System.out.println((i + 1) + ". " + post.getTitle());
            }

            // 示例2: 获取特定帖子并映射到Post对象
            System.out.println("\n获取特定帖子:");
            Response postResponse = given()
                    .pathParam("id", 1)
                    .when()
                    .get("/posts/{id}")
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();

            Post post = objectMapper.readValue(postResponse.asString(), Post.class);
            System.out.println("帖子详情:");
            System.out.println("标题: " + post.getTitle());
            System.out.println("作者ID: " + post.getUserId());
            System.out.println("内容: " + post.getBody());

            // 示例3: 创建新帖子并映射返回的Post对象
            System.out.println("\n创建新帖子:");
            Post newPost = new Post(null, 1L, "测试标题", "测试内容");
            String requestBody = objectMapper.writeValueAsString(newPost);

            Response createResponse = given()
                    .header("Content-type", "application/json")
                    .body(requestBody)
                    .when()
                    .post("/posts")
                    .then()
                    .statusCode(201)
                    .extract()
                    .response();

            Post createdPost = objectMapper.readValue(createResponse.asString(), Post.class);
            System.out.println("新创建的帖子:");
            System.out.println(createdPost);

            // 示例4: 更新帖子并映射返回的Post对象
            System.out.println("\n更新帖子:");
            Post updatedPost = new Post(1L, 1L, "更新后的标题", "更新后的内容");
            String updateBody = objectMapper.writeValueAsString(updatedPost);

            Response updateResponse = given()
                    .header("Content-type", "application/json")
                    .body(updateBody)
                    .when()
                    .put("/posts/1")
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();

            Post resultPost = objectMapper.readValue(updateResponse.asString(), Post.class);
            System.out.println("更新后的帖子:");
            System.out.println(resultPost);

            // 示例5: 获取用户信息并映射到User对象
            System.out.println("\n获取用户信息:");
            Response userResponse = given()
                    .pathParam("id", 1)
                    .when()
                    .get("/users/{id}")
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();

            User user = objectMapper.readValue(userResponse.asString(), User.class);
            System.out.println("用户信息:");
            System.out.println(user);

            // 示例6: 删除帖子
            System.out.println("\n删除帖子:");
            given()
                    .when()
                    .delete("/posts/1")
                    .then()
                    .statusCode(200);
            System.out.println("帖子删除成功");

        } catch (Exception e) {
            System.err.println("处理数据时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void configureProxy() {
        String proxyEnabled = System.getProperty("proxy.enabled", "false");
        if ("true".equalsIgnoreCase(proxyEnabled)) {
            String proxyHost = System.getProperty("proxy.host", "localhost");
            String proxyPort = System.getProperty("proxy.port", "7890");
            
            System.out.println("使用代理配置:");
            System.out.println("代理主机: " + proxyHost);
            System.out.println("代理端口: " + proxyPort);
            
            System.setProperty("http.proxyHost", proxyHost);
            System.setProperty("http.proxyPort", proxyPort);
            System.setProperty("https.proxyHost", proxyHost);
            System.setProperty("https.proxyPort", proxyPort);
        }
    }
} 