package com.nagarro.api;

import io.restassured.response.Response;

import static com.nagarro.urls.ApiEndpoints.BASE_URL;
import static com.nagarro.urls.ApiEndpoints.PING_ENDPOINT;
import static io.restassured.RestAssured.given;

public final class PingApi {
    private PingApi() {}

    public static Response healthCheck() {
        return given().when().get(BASE_URL + PING_ENDPOINT);
    }
}
