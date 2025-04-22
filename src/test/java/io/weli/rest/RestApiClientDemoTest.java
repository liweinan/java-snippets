package io.weli.rest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RestApiClientDemoTest {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    @BeforeAll
    public static void setup() {
        // 可选代理设置
        String proxyEnabled = System.getProperty("proxy.enabled", "false");
        if ("true".equalsIgnoreCase(proxyEnabled)) {
            System.setProperty("http.proxyHost", System.getProperty("proxy.host", "localhost"));
            System.setProperty("http.proxyPort", System.getProperty("proxy.port", "7890"));
            System.setProperty("https.proxyHost", System.getProperty("proxy.host", "localhost"));
            System.setProperty("https.proxyPort", System.getProperty("proxy.port", "7890"));
        }
        
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void testGetAllPosts() {
        given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    public void testGetSpecificPost() {
        given()
                .pathParam("id", 1)
                .when()
                .get("/posts/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("title", not(emptyOrNullString()))
                .body("userId", notNullValue());
    }

    @Test
    public void testCreatePost() {
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
    }

    @Test
    public void testUpdatePost() {
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
    }

    @Test
    public void testDeletePost() {
        given()
                .when()
                .delete("/posts/1")
                .then()
                .statusCode(200);
    }
} 