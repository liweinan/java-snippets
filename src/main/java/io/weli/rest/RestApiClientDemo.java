package io.weli.rest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
            // 示例1: 获取所有帖子并处理数据
            System.out.println("获取所有帖子:");
            Response response = given()
                    .when()
                    .get("/posts")
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();

            JsonNode posts = objectMapper.readTree(response.asString());
            System.out.println("帖子总数: " + posts.size());
            System.out.println("前5个帖子标题:");
            for (int i = 0; i < Math.min(5, posts.size()); i++) {
                System.out.println((i + 1) + ". " + posts.get(i).get("title").asText());
            }

            // 示例2: 获取特定帖子并处理数据
            System.out.println("\n获取特定帖子:");
            Response postResponse = given()
                    .pathParam("id", 1)
                    .when()
                    .get("/posts/{id}")
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();

            JsonNode post = objectMapper.readTree(postResponse.asString());
            System.out.println("帖子详情:");
            System.out.println("标题: " + post.get("title").asText());
            System.out.println("作者ID: " + post.get("userId").asInt());
            System.out.println("内容: " + post.get("body").asText());

            // 示例3: 创建新帖子并处理返回数据
            System.out.println("\n创建新帖子:");
            String requestBody = """
                    {
                        "title": "测试标题",
                        "body": "测试内容",
                        "userId": 1
                    }
                    """;

            Response createResponse = given()
                    .header("Content-type", "application/json")
                    .body(requestBody)
                    .when()
                    .post("/posts")
                    .then()
                    .statusCode(201)
                    .extract()
                    .response();

            JsonNode createdPost = objectMapper.readTree(createResponse.asString());
            System.out.println("新创建的帖子ID: " + createdPost.get("id").asInt());
            System.out.println("标题: " + createdPost.get("title").asText());

            // 示例4: 更新帖子并处理返回数据
            System.out.println("\n更新帖子:");
            String updateBody = """
                    {
                        "id": 1,
                        "title": "更新后的标题",
                        "body": "更新后的内容",
                        "userId": 1
                    }
                    """;

            Response updateResponse = given()
                    .header("Content-type", "application/json")
                    .body(updateBody)
                    .when()
                    .put("/posts/1")
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();

            JsonNode updatedPost = objectMapper.readTree(updateResponse.asString());
            System.out.println("更新后的帖子详情:");
            System.out.println("标题: " + updatedPost.get("title").asText());
            System.out.println("内容: " + updatedPost.get("body").asText());

            // 示例5: 删除帖子并验证
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