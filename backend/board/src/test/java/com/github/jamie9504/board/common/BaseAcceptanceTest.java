package com.github.jamie9504.board.common;

import io.restassured.RestAssured;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public abstract class BaseAcceptanceTest {

    protected static final String AUTHORIZATION = "Authorization";
    @LocalServerPort
    public int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    protected String create(Map<String, String> params, String url) {
        return RestAssured.given()
            .log().all()
            .body(params)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post(url)
            .then()
            .log().all()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .header("location");
    }

    protected <T> T find(String location, Class<T> response) {
        return RestAssured.given()
            .log().all()
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get(location)
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .as(response);
    }

    protected <T> List<T> findAll(String location, Class<T> response) {
        return RestAssured.given()
            .log().all()
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get(location)
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .jsonPath()
            .getList(".", response);
    }

    protected void update(Map<String, String> params, String url) {
        RestAssured.given()
            .log().all()
            .body(params)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .put(url)
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value());
    }

    protected void delete(String location) {
        RestAssured.given()
            .log().all()
            .when()
            .delete(location)
            .then()
            .log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    protected String login(String email, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);

        return RestAssured.given()
            .log().all()
            .body(params)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/login")
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .header(AUTHORIZATION);
    }

    protected String createWithToken(String token, Map<String, String> params, String url) {
        return RestAssured.given()
            .log().all()
            .body(params)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header(AUTHORIZATION, token)
            .when()
            .post(url)
            .then()
            .log().all()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .header("location");
    }

    protected <T> T findWithToken(String token, String location, Class<T> response) {
        return RestAssured.given()
            .log().all()
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header(AUTHORIZATION, token)
            .when()
            .get(location)
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .as(response);
    }

    protected <T> List<T> findAllWithToken(String token, String location, Class<T> response) {
        return RestAssured.given()
            .log().all()
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header(AUTHORIZATION, token)
            .when()
            .get(location)
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .jsonPath()
            .getList(".", response);
    }

    protected void updateWithToken(String token, Map<String, String> params, String location) {
        RestAssured.given()
            .log().all()
            .body(params)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header(AUTHORIZATION, token)
            .when()
            .put(location)
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value());
    }

    protected void deleteWithToken(String token, String location) {
        RestAssured.given()
            .log().all()
            .header(AUTHORIZATION, token)
            .when()
            .delete(location)
            .then()
            .log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
