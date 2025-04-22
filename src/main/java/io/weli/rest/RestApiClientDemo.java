package io.weli.rest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RestApiClientDemo {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public static void main(String[] args) {
        // 配置代理（如果启用）
        configureProxy();

        // 设置基础URL
        RestAssured.baseURI = BASE_URL;

        // 示例1: 获取所有帖子
        System.out.println("获取所有帖子:");
        Response response = given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .extract()
                .response();

        System.out.println("响应内容: " + response.asString());

        // 示例2: 获取特定帖子
        System.out.println("\n获取特定帖子:");
        given()
                .pathParam("id", 1)
                .when()
                .get("/posts/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("title", not(emptyOrNullString()))
                .body("userId", notNullValue());

        // 示例3: 创建新帖子
        System.out.println("\n创建新帖子:");
        String requestBody = """
                {
                    "title": "测试标题",
                    "body": "测试内容",
                    "userId": 1
                }
                """;

        given()
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("title", equalTo("测试标题"));

        // 示例4: 更新帖子
        System.out.println("\n更新帖子:");
        String updateBody = """
                {
                    "id": 1,
                    "title": "更新后的标题",
                    "body": "更新后的内容",
                    "userId": 1
                }
                """;

        given()
                .header("Content-type", "application/json")
                .body(updateBody)
                .when()
                .put("/posts/1")
                .then()
                .statusCode(200)
                .body("title", equalTo("更新后的标题"));

        // 示例5: 删除帖子
        System.out.println("\n删除帖子:");
        given()
                .when()
                .delete("/posts/1")
                .then()
                .statusCode(200);
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